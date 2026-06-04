"""
sniper.py
---------
Compares every .png in logos/<subfolder>/ against a reference image
using OpenCV ORB feature matching + histogram similarity.

Usage:
    python sniper.py

Reference image path  : referenceImg/referenceImg.png
Logos root            : logos/
"""

import os
import sys
import time
import cv2
import numpy as np
from datetime import datetime
from pathlib import Path

# ── Rich TUI ──────────────────────────────────────────────────────────────────
try:
    from rich.console import Console
    from rich.table import Table
    from rich.panel import Panel
    from rich.progress import (
        Progress, BarColumn, TextColumn,
        TaskProgressColumn, TimeElapsedColumn, SpinnerColumn
    )
    from rich.text import Text
    from rich.live import Live
    from rich.layout import Layout
    from rich import box
    from rich.style import Style
    from rich.rule import Rule
    RICH_OK = True
except ImportError:
    RICH_OK = False
    print("[ERROR] 'rich' not installed.  Run:  pip install rich opencv-python")
    sys.exit(1)

console = Console()

# ── Paths ─────────────────────────────────────────────────────────────────────
PROJECT_ROOT   = Path(__file__).parent.resolve()
REFERENCE_PATH = PROJECT_ROOT / "referenceImg" / "referenceImg.png"
LOGOS_DIR      = PROJECT_ROOT / "logos"
LOGS_DIR       = PROJECT_ROOT / "logs"

# ── Matching config ───────────────────────────────────────────────────────────
# ORB: minimum good-match ratio (good matches / total keypoints) to call it a match
ORB_MATCH_RATIO      = 0.18   # ≥18 % of keypoints match → ORB says yes
# Histogram: Bhattacharyya distance — lower = more similar (0.0 perfect, 1.0 none)
HIST_DISTANCE_THRESH = 0.55   # ≤0.55 → histogram says yes
# Final decision: EITHER ORB OR histogram must agree
# (robust: catches colour-heavy logos where ORB fails and vice-versa)

# ── Logging ───────────────────────────────────────────────────────────────────
_log_lines:     list[str] = []
_log_file_path: str       = ""

def init_log():
    global _log_file_path
    LOGS_DIR.mkdir(parents=True, exist_ok=True)
    ts = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    _log_file_path = str(LOGS_DIR / f"sniper_{ts}.txt")

def _flush():
    if not _log_file_path:
        return
    text = "\n".join(_log_lines)
    with open(_log_file_path, "w", encoding="utf-8") as f:
        f.write(text)
    with open(LOGS_DIR / "sniper_latest.txt", "w", encoding="utf-8") as f:
        f.write(text)

def log(msg: str):
    ts   = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    _log_lines.append(line)
    _flush()


# ── Load reference ─────────────────────────────────────────────────────────────
def load_reference() -> tuple[np.ndarray, np.ndarray]:
    """
    Returns (ref_gray, ref_bgr).
    Exits if the file is missing or unreadable.
    """
    if not REFERENCE_PATH.exists():
        console.print(f"\n[bold red]✘  Reference image not found:[/bold red] {REFERENCE_PATH}")
        sys.exit(1)

    img = cv2.imread(str(REFERENCE_PATH))
    if img is None:
        console.print(f"\n[bold red]✘  Cannot read reference image:[/bold red] {REFERENCE_PATH}")
        sys.exit(1)

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    return gray, img


# ── ORB feature matching ───────────────────────────────────────────────────────
_orb = cv2.ORB_create(nfeatures=500)
_bf  = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)

def orb_similarity(ref_gray: np.ndarray, tgt_gray: np.ndarray) -> float:
    """
    Returns ratio of good matches to the smaller keypoint set (0.0 – 1.0).
    """
    kp1, des1 = _orb.detectAndCompute(ref_gray, None)
    kp2, des2 = _orb.detectAndCompute(tgt_gray, None)

    if des1 is None or des2 is None or len(kp1) == 0 or len(kp2) == 0:
        return 0.0

    matches   = _bf.match(des1, des2)
    good      = [m for m in matches if m.distance < 60]
    base      = min(len(kp1), len(kp2))
    return len(good) / base if base else 0.0


# ── Histogram similarity ───────────────────────────────────────────────────────
def hist_distance(ref_bgr: np.ndarray, tgt_bgr: np.ndarray) -> float:
    """
    Returns Bhattacharyya distance (0 = identical, 1 = totally different).
    """
    def _hist(img):
        h = cv2.calcHist([img], [0, 1, 2], None, [32, 32, 32],
                         [0, 256, 0, 256, 0, 256])
        cv2.normalize(h, h)
        return h

    ref_h = _hist(ref_bgr)
    tgt_h = _hist(tgt_bgr)
    return cv2.compareHist(ref_h, tgt_h, cv2.HISTCMP_BHATTACHARYYA)


# ── Per-image comparison ───────────────────────────────────────────────────────
def compare_image(
    path: Path,
    ref_gray: np.ndarray,
    ref_bgr: np.ndarray,
) -> dict:
    """
    Returns a dict with keys: path, orb_score, hist_dist, matched (bool), error (str|None).
    """
    result = {
        "path":      path,
        "orb_score": 0.0,
        "hist_dist": 1.0,
        "matched":   False,
        "error":     None,
    }

    img = cv2.imread(str(path))
    if img is None:
        result["error"] = "unreadable"
        return result

    try:
        gray        = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        orb_score   = orb_similarity(ref_gray, gray)
        hist_d      = hist_distance(ref_bgr, img)

        result["orb_score"] = orb_score
        result["hist_dist"] = hist_d
        result["matched"]   = (orb_score >= ORB_MATCH_RATIO) or (hist_d <= HIST_DISTANCE_THRESH)

    except Exception as e:
        result["error"] = str(e)

    return result


# ── Discover all logos ─────────────────────────────────────────────────────────
def discover_folders() -> list[tuple[str, list[Path]]]:
    """
    Returns [(folder_name, [png_path, ...]), ...] sorted by folder name.
    """
    if not LOGOS_DIR.exists():
        console.print(f"\n[bold red]✘  Logos directory not found:[/bold red] {LOGOS_DIR}")
        sys.exit(1)

    folders = []
    for entry in sorted(LOGOS_DIR.iterdir()):
        if entry.is_dir():
            pngs = sorted(entry.glob("*.png"))
            if pngs:
                folders.append((entry.name, pngs))
    return folders


# ── Pretty summary table ───────────────────────────────────────────────────────
def render_summary(all_results: list[dict], elapsed: float):
    """
    Prints the final per-folder + per-image breakdown table.
    """

    # ── Header ──
    console.print()
    console.rule("[bold cyan]  S N I P E R   R E S U L T S  [/bold cyan]", style="cyan")
    console.print()

    # ── Per-folder table ──
    folder_table = Table(
        box=box.SIMPLE_HEAVY,
        show_header=True,
        header_style="bold cyan",
        border_style="dim cyan",
        expand=False,
        title="[bold]Folder Match Summary[/bold]",
        title_style="bold white",
    )
    folder_table.add_column("Folder",          style="white",      no_wrap=True, min_width=30)
    folder_table.add_column("Images Scanned",  style="dim white",  justify="right")
    folder_table.add_column("Matches",         style="bold green", justify="right")
    folder_table.add_column("Match Rate",      style="yellow",     justify="right")
    folder_table.add_column("Errors",          style="red dim",    justify="right")

    total_images  = 0
    total_matches = 0
    total_errors  = 0

    for folder_data in all_results:
        fname    = folder_data["folder"]
        imgs     = folder_data["images"]
        n        = len(imgs)
        matched  = sum(1 for i in imgs if i["matched"])
        errors   = sum(1 for i in imgs if i["error"])
        rate     = f"{matched/n*100:.0f}%" if n else "—"

        total_images  += n
        total_matches += matched
        total_errors  += errors

        match_style = "bold green" if matched > 0 else "dim"
        folder_table.add_row(
            fname,
            str(n),
            f"[{match_style}]{matched}[/{match_style}]",
            rate,
            str(errors) if errors else "[dim]0[/dim]",
        )

    console.print(folder_table)

    # ── Per-image detail table (only matches + errors, to keep it readable) ──
    detail_rows = []
    for folder_data in all_results:
        for img in folder_data["images"]:
            if img["matched"] or img["error"]:
                detail_rows.append((folder_data["folder"], img))

    if detail_rows:
        console.print()
        detail_table = Table(
            box=box.MINIMAL_DOUBLE_HEAD,
            show_header=True,
            header_style="bold magenta",
            border_style="dim magenta",
            expand=False,
            title="[bold]Per-Image Detail  (matches & errors only)[/bold]",
            title_style="bold white",
        )
        detail_table.add_column("Folder",     style="white",   no_wrap=True, min_width=25)
        detail_table.add_column("File",       style="cyan",    no_wrap=True, min_width=30)
        detail_table.add_column("ORB Score",  style="yellow",  justify="right")
        detail_table.add_column("Hist Dist",  style="yellow",  justify="right")
        detail_table.add_column("Result",     justify="center")

        for folder_name, img in detail_rows:
            fname = img["path"].name
            if img["error"]:
                result_cell = f"[red]ERR: {img['error'][:30]}[/red]"
                orb_cell    = "—"
                hist_cell   = "—"
            elif img["matched"]:
                result_cell = "[bold green]✔  MATCH[/bold green]"
                orb_cell    = f"{img['orb_score']:.3f}"
                hist_cell   = f"{img['hist_dist']:.3f}"
            else:
                continue  # shouldn't reach here

            detail_table.add_row(folder_name, fname, orb_cell, hist_cell, result_cell)

        console.print(detail_table)

    # ── Totals ──
    console.print()
    console.print(
        Panel(
            f"  [bold white]Folders scanned  :[/bold white] [cyan]{len(all_results)}[/cyan]\n"
            f"  [bold white]Images scanned   :[/bold white] [cyan]{total_images}[/cyan]\n"
            f"  [bold white]Total matches    :[/bold white] [bold green]{total_matches}[/bold green]\n"
            f"  [bold white]Total errors     :[/bold white] [red]{total_errors}[/red]\n"
            f"  [bold white]Elapsed          :[/bold white] [dim]{elapsed:.1f}s[/dim]\n"
            f"  [bold white]Log saved to     :[/bold white] [dim]{_log_file_path}[/dim]",
            title="[bold cyan]  TOTALS  [/bold cyan]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print()


# ── Main ───────────────────────────────────────────────────────────────────────
def main():
    init_log()

    # Banner
    console.print()
    console.print(
        Panel(
            "[bold cyan]  S N I P E R[/bold cyan]\n"
            "[dim]  OpenCV visual-similarity logo scanner[/dim]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print(f"  [dim]Reference :[/dim] [white]{REFERENCE_PATH}[/white]")
    console.print(f"  [dim]Logos root:[/dim] [white]{LOGOS_DIR}[/white]")
    console.print()

    log(f"Reference : {REFERENCE_PATH}")
    log(f"Logos root: {LOGOS_DIR}")

    # Load reference
    console.print("  [dim]Loading reference image…[/dim]")
    ref_gray, ref_bgr = load_reference()
    console.print(f"  [green]✔[/green]  Reference loaded  [{ref_bgr.shape[1]}×{ref_bgr.shape[0]}px]\n")
    log(f"Reference loaded: {ref_bgr.shape[1]}x{ref_bgr.shape[0]}")

    # Discover folders
    folders = discover_folders()
    if not folders:
        console.print("[yellow]  No PNG images found inside logos/ subfolders. Nothing to do.[/yellow]")
        sys.exit(0)

    total_images = sum(len(p) for _, p in folders)
    console.print(
        f"  [cyan]Folders[/cyan] : [white]{len(folders)}[/white]   "
        f"[cyan]Images[/cyan] : [white]{total_images}[/white]\n"
    )
    log(f"Folders: {len(folders)}  Images: {total_images}")

    # ── Progress ──
    all_results: list[dict] = []
    t_start = time.time()

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

        overall_task = progress.add_task(
            "Overall",
            total=total_images,
            status="",
        )

        for folder_name, png_paths in folders:
            folder_task = progress.add_task(
                f"[dim]{folder_name[:35]}[/dim]",
                total=len(png_paths),
                status="",
            )

            folder_data = {"folder": folder_name, "images": []}

            for img_path in png_paths:
                progress.update(
                    folder_task,
                    status=f"[dim]{img_path.name[:40]}[/dim]",
                )
                progress.update(
                    overall_task,
                    status=f"[dim]{folder_name[:20]}/{img_path.name[:25]}[/dim]",
                )

                result = compare_image(img_path, ref_gray, ref_bgr)
                folder_data["images"].append(result)

                log(
                    f"{folder_name}/{img_path.name}  "
                    f"orb={result['orb_score']:.3f}  "
                    f"hist={result['hist_dist']:.3f}  "
                    f"match={result['matched']}  "
                    f"err={result['error']}"
                )

                progress.advance(folder_task)
                progress.advance(overall_task)

            all_results.append(folder_data)

            matches = sum(1 for i in folder_data["images"] if i["matched"])
            progress.update(
                folder_task,
                status=f"[green]{matches} match{'es' if matches != 1 else ''}[/green]",
            )

    elapsed = time.time() - t_start

    # ── Summary ──
    render_summary(all_results, elapsed)
    log("Done.")
    _flush()


if __name__ == "__main__":
    main()