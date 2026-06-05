"""
verifier.py
-----------
1. Reads every subfolder name from colleges/
2. Cross-references against logos/
   - Missing entirely  → check Wikipedia: no article? no images?
   - Present but ≤ 2 PNGs → flagged as sparse
3. Prints a rich TUI summary with per-college detail

Usage:
    python verifier.py
"""

import os
import sys
import time
import re
import requests
from pathlib import Path
from datetime import datetime

try:
    from rich.console import Console
    from rich.table import Table
    from rich.panel import Panel
    from rich.progress import (
        Progress, BarColumn, TextColumn,
        TaskProgressColumn, TimeElapsedColumn, SpinnerColumn,
    )
    from rich import box
    from rich.rule import Rule
except ImportError:
    print("[ERROR] 'rich' not installed.  Run:  pip install rich requests")
    sys.exit(1)

console = Console()

# ── Paths ─────────────────────────────────────────────────────────────────────
PROJECT_ROOT  = Path(__file__).parent.resolve()
COLLEGES_DIR  = PROJECT_ROOT / "colleges"
LOGOS_DIR     = PROJECT_ROOT / "logos"
LOGS_DIR      = PROJECT_ROOT / "logs"

# ── Wikipedia API ─────────────────────────────────────────────────────────────
WIKIPEDIA_API = "https://en.wikipedia.org/w/api.php"
HEADERS = {
    "User-Agent": (
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 (KHTML, like Gecko) "
        "Chrome/124.0.0.0 Safari/537.36"
    ),
    "Referer": "https://en.wikipedia.org/",
}

SKIP_KEYWORDS = [
    "commons-logo", "edit-ltr", "edit-rtl", "red_pog", "blue_pog",
    "question_mark", "wikimedia-logo", "powered_by", "wiki_letter",
    "increase2", "decrease2", "steady2", "disambig", "folder",
    "portal-puzzle", "icon", "button", "star", "lock", "sound",
    "audio", "video", "speaker", "arrow", "sig_", "signatur",
    "placeholder", "noimage", "missing",
]

SPARSE_THRESHOLD = 2   # ≤ this many PNGs → flagged sparse

# ── Status labels ─────────────────────────────────────────────────────────────
ST_OK         = "OK"
ST_SPARSE     = "Sparse"
ST_MISSING    = "Missing"
ST_NO_WIKI    = "No Wikipedia"
ST_NO_IMAGES  = "No Wiki Images"
ST_WIKI_OK    = "Wiki Has Images"
ST_ERROR      = "Check Error"

# ── Logging ───────────────────────────────────────────────────────────────────
_log_lines:     list[str] = []
_log_file_path: str       = ""

def init_log():
    global _log_file_path
    LOGS_DIR.mkdir(parents=True, exist_ok=True)
    ts = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    _log_file_path = str(LOGS_DIR / f"verifier_{ts}.txt")

def _flush():
    if not _log_file_path:
        return
    text = "\n".join(_log_lines)
    with open(_log_file_path, "w", encoding="utf-8") as f:
        f.write(text)
    with open(LOGS_DIR / "verifier_latest.txt", "w", encoding="utf-8") as f:
        f.write(text)

def log(msg: str):
    ts   = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    _log_lines.append(line)
    _flush()


# ── Helpers ───────────────────────────────────────────────────────────────────
def safe_name(name: str) -> str:
    return re.sub(r'[<>:"/\\|?*]', "_", name)

def is_skip_file(filename: str) -> bool:
    low = filename.lower()
    return any(k in low for k in SKIP_KEYWORDS)


# ── Wikipedia checks ──────────────────────────────────────────────────────────
def wiki_resolve(college_name: str) -> str | None:
    """Return Wikipedia page title or None."""
    try:
        r = requests.get(
            WIKIPEDIA_API,
            headers=HEADERS,
            timeout=15,
            params={
                "action":    "opensearch",
                "search":    college_name,
                "limit":     1,
                "redirects": "resolve",
                "format":    "json",
            },
        )
        data   = r.json()
        titles = data[1]
        if titles:
            return titles[0]
    except Exception as e:
        log(f"  wiki_resolve error [{college_name}]: {e}")
    return None


def wiki_has_images(page_title: str) -> bool:
    """Return True if the Wikipedia page has at least one usable image."""
    params = {
        "action":  "query",
        "titles":  page_title,
        "prop":    "images",
        "imlimit": "max",
        "format":  "json",
    }
    try:
        while True:
            r    = requests.get(WIKIPEDIA_API, headers=HEADERS, timeout=15, params=params)
            data = r.json()
            pages = data.get("query", {}).get("pages", {})
            for page in pages.values():
                for img in page.get("images", []):
                    fname = img.get("title", "").replace("File:", "").strip()
                    if fname and not is_skip_file(fname):
                        return True
            if "continue" in data:
                params["imcontinue"] = data["continue"]["imcontinue"]
            else:
                break
    except Exception as e:
        log(f"  wiki_has_images error [{page_title}]: {e}")
    return False


# ── Discovery ─────────────────────────────────────────────────────────────────
def get_college_names() -> list[str]:
    if not COLLEGES_DIR.exists():
        console.print(f"\n[bold red]✘  colleges/ directory not found:[/bold red] {COLLEGES_DIR}")
        sys.exit(1)
    names = sorted(
        d.name for d in COLLEGES_DIR.iterdir() if d.is_dir()
    )
    if not names:
        console.print("[yellow]  No subfolders found in colleges/. Nothing to do.[/yellow]")
        sys.exit(0)
    return names


def count_logos(college_name: str) -> int | None:
    """Return PNG count in logos/<safe_name>/, or None if folder missing."""
    folder = LOGOS_DIR / safe_name(college_name)
    if not folder.exists():
        return None
    pngs = list(folder.glob("*.png"))
    return len(pngs)


# ── Result rendering ──────────────────────────────────────────────────────────
STATUS_STYLE = {
    ST_OK:         "bold green",
    ST_SPARSE:     "yellow",
    ST_MISSING:    "red",
    ST_NO_WIKI:    "bold red",
    ST_NO_IMAGES:  "orange3",
    ST_WIKI_OK:    "cyan",
    ST_ERROR:      "magenta",
}

def style_status(status: str) -> str:
    s = STATUS_STYLE.get(status, "white")
    return f"[{s}]{status}[/{s}]"


def render_results(results: list[dict], elapsed: float):
    console.print()
    console.rule("[bold cyan]  V E R I F I E R   R E S U L T S  [/bold cyan]", style="cyan")
    console.print()

    # ── Main table ──
    tbl = Table(
        box=box.SIMPLE_HEAVY,
        show_header=True,
        header_style="bold cyan",
        border_style="dim cyan",
        expand=False,
        title="[bold]College Verification Report[/bold]",
        title_style="bold white",
    )
    tbl.add_column("College",         style="white",     no_wrap=True, min_width=35)
    tbl.add_column("Logo PNGs",       justify="right",   min_width=10)
    tbl.add_column("Status",          justify="center",  min_width=16)
    tbl.add_column("Wikipedia Title", style="dim white", no_wrap=True, min_width=35)

    for r in results:
        png_cell  = str(r["png_count"]) if r["png_count"] is not None else "[dim]—[/dim]"
        wiki_cell = r["wiki_title"] or "[dim]—[/dim]"
        tbl.add_row(r["name"], png_cell, style_status(r["status"]), wiki_cell)

    console.print(tbl)

    # ── Flagged sections ──
    sparse   = [r for r in results if r["status"] == ST_SPARSE]
    no_wiki  = [r for r in results if r["status"] == ST_NO_WIKI]
    no_imgs  = [r for r in results if r["status"] == ST_NO_IMAGES]
    wiki_ok  = [r for r in results if r["status"] == ST_WIKI_OK]
    errors   = [r for r in results if r["status"] == ST_ERROR]

    def flag_section(title: str, items: list[dict], style: str, note: str = ""):
        if not items:
            return
        console.print()
        console.rule(f"[{style}]  {title}  ({len(items)})[/{style}]", style=style)
        if note:
            console.print(f"  [dim]{note}[/dim]")
        for r in items:
            extra = f"  [dim]({r['png_count']} PNGs)[/dim]" if r["png_count"] is not None else ""
            wiki  = f"  → [dim]{r['wiki_title']}[/dim]" if r.get("wiki_title") else ""
            console.print(f"  [bold {style}]▸[/bold {style}]  {r['name']}{extra}{wiki}")

    flag_section(
        "SPARSE  — ≤2 logos saved",
        sparse, "yellow",
        "These folders exist but have very few images. Re-run the scraper for them.",
    )
    flag_section(
        "MISSING + NO WIKIPEDIA ARTICLE",
        no_wiki, "bold red",
        "No logos folder AND no Wikipedia page found. Check spelling or scrape manually.",
    )
    flag_section(
        "MISSING + WIKIPEDIA EXISTS BUT NO IMAGES",
        no_imgs, "orange3",
        "Wikipedia article found but has no usable images. Logo may not exist on Wikipedia.",
    )
    flag_section(
        "MISSING + WIKIPEDIA HAS IMAGES  (scraper may have failed)",
        wiki_ok, "cyan",
        "Wikipedia article has images but logos folder is absent. Re-run the scraper.",
    )
    flag_section("CHECK ERRORS", errors, "magenta")

    # ── Counts panel ──
    counts = {}
    for r in results:
        counts[r["status"]] = counts.get(r["status"], 0) + 1

    console.print()
    console.print(
        Panel(
            f"  [bold white]Total colleges         :[/bold white] [cyan]{len(results)}[/cyan]\n"
            f"  [bold white][{ST_OK}]                 :[/bold white] [green]{counts.get(ST_OK, 0)}[/green]\n"
            f"  [bold white][{ST_SPARSE}]             :[/bold white] [yellow]{counts.get(ST_SPARSE, 0)}[/yellow]\n"
            f"  [bold white][{ST_NO_WIKI}]      :[/bold white] [bold red]{counts.get(ST_NO_WIKI, 0)}[/bold red]\n"
            f"  [bold white][{ST_NO_IMAGES}] :[/bold white] [orange3]{counts.get(ST_NO_IMAGES, 0)}[/orange3]\n"
            f"  [bold white][{ST_WIKI_OK}]     :[/bold white] [cyan]{counts.get(ST_WIKI_OK, 0)}[/cyan]\n"
            f"  [bold white][{ST_ERROR}]          :[/bold white] [magenta]{counts.get(ST_ERROR, 0)}[/magenta]\n"
            f"  [bold white]Elapsed                :[/bold white] [dim]{elapsed:.1f}s[/dim]\n"
            f"  [bold white]Log saved to           :[/bold white] [dim]{_log_file_path}[/dim]",
            title="[bold cyan]  TOTALS  [/bold cyan]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print()


# ── Main ───────────────────────────────────────────────────────────────────────
def main():
    init_log()

    console.print()
    console.print(
        Panel(
            "[bold cyan]  V E R I F I E R[/bold cyan]\n"
            "[dim]  College → Logos cross-checker + Wikipedia probe[/dim]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print(f"  [dim]Colleges dir :[/dim] [white]{COLLEGES_DIR}[/white]")
    console.print(f"  [dim]Logos dir    :[/dim] [white]{LOGOS_DIR}[/white]")
    console.print()

    log(f"Colleges dir : {COLLEGES_DIR}")
    log(f"Logos dir    : {LOGOS_DIR}")

    colleges = get_college_names()
    console.print(f"  [cyan]Colleges found:[/cyan] [white]{len(colleges)}[/white]\n")
    log(f"Colleges found: {len(colleges)}")

    # Separate into: has logos (just count), missing logos (needs wiki check)
    present  = [(n, c) for n in colleges if (c := count_logos(n)) is not None]
    missing  = [n for n in colleges if count_logos(n) is None]

    console.print(
        f"  [green]Have logos folder[/green] : {len(present)}   "
        f"[red]Missing logos folder[/red] : {len(missing)}\n"
    )

    results: list[dict] = []
    t_start = time.time()

    # ── Phase 1: logo folder checks (fast, no network) ──
    console.rule("[dim cyan]Phase 1 — Logo folder audit[/dim cyan]", style="dim cyan")
    console.print()

    with Progress(
        SpinnerColumn(spinner_name="dots", style="cyan"),
        TextColumn("  [bold cyan]{task.description}[/bold cyan]"),
        BarColumn(
            bar_width=38,
            style="cyan",
            complete_style="bold cyan",
            finished_style="bold green",
        ),
        TaskProgressColumn(style="white"),
        TextColumn("[dim]{task.fields[status]}[/dim]"),
        console=console,
        transient=False,
    ) as progress:

        task = progress.add_task("Auditing logo folders", total=len(present), status="")

        for name, png_count in present:
            progress.update(task, status=f"[dim]{name[:40]}[/dim]")
            status = ST_OK if png_count > SPARSE_THRESHOLD else ST_SPARSE
            results.append({
                "name":       name,
                "png_count":  png_count,
                "status":     status,
                "wiki_title": None,
            })
            log(f"{name}  pngs={png_count}  status={status}")
            progress.advance(task)

    # ── Phase 2: Wikipedia checks for missing folders ──
    if missing:
        console.print()
        console.rule("[dim cyan]Phase 2 — Wikipedia probe for missing colleges[/dim cyan]", style="dim cyan")
        console.print()

        with Progress(
            SpinnerColumn(spinner_name="dots", style="cyan"),
            TextColumn("  [bold cyan]{task.description}[/bold cyan]"),
            BarColumn(
                bar_width=38,
                style="cyan",
                complete_style="bold cyan",
                finished_style="bold green",
            ),
            TaskProgressColumn(style="white"),
            TextColumn("[dim]{task.fields[status]}[/dim]"),
            TimeElapsedColumn(),
            console=console,
            transient=False,
        ) as progress:

            task = progress.add_task("Probing Wikipedia", total=len(missing), status="")

            for name in missing:
                progress.update(task, status=f"[dim]{name[:40]}[/dim]")
                log(f"Wiki probe: {name}")

                try:
                    wiki_title = wiki_resolve(name)

                    if not wiki_title:
                        status     = ST_NO_WIKI
                        wiki_title = None
                    else:
                        has_imgs = wiki_has_images(wiki_title)
                        status   = ST_WIKI_OK if has_imgs else ST_NO_IMAGES

                except Exception as e:
                    log(f"  error: {e}")
                    wiki_title = None
                    status     = ST_ERROR

                results.append({
                    "name":       name,
                    "png_count":  None,
                    "status":     status,
                    "wiki_title": wiki_title,
                })
                log(f"{name}  wiki={wiki_title}  status={status}")
                progress.advance(task)

                # polite delay so Wikipedia doesn't rate-limit us
                time.sleep(1.2)

    elapsed = time.time() - t_start

    # Sort: OK first, then by status priority, then alpha
    status_order = {
        ST_OK: 0, ST_SPARSE: 1, ST_WIKI_OK: 2,
        ST_NO_IMAGES: 3, ST_NO_WIKI: 4, ST_ERROR: 5,
    }
    results.sort(key=lambda r: (status_order.get(r["status"], 9), r["name"].lower()))

    render_results(results, elapsed)
    log("Done.")
    _flush()


if __name__ == "__main__":
    main()