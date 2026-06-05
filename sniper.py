"""
sniper.py
---------
Compares every .png in logos/<subfolder>/ against a reference image
using OpenCV ORB feature matching + histogram similarity.

Non-matched images are copied (preserving subfolder structure) to:
    <project_root>/sniped/<subfolder>/<filename>.png
  → copying happens immediately after each folder is processed.

Usage:
    python sniper.py

Reference image path  : referenceImg/referenceImg.png
Logos root            : logos/
Sniped output         : sniped/
"""

import sys
import time
import shutil
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
        TaskProgressColumn, TimeElapsedColumn, SpinnerColumn,
    )
    from rich import box
    RICH_OK = True
except ImportError:
    print("[ERROR] 'rich' not installed.  Run:  pip install rich opencv-python")
    sys.exit(1)

console = Console()

# ── Paths ─────────────────────────────────────────────────────────────────────
PROJECT_ROOT   = Path(__file__).parent.resolve()
REFERENCE_PATH = PROJECT_ROOT / "referenceImg" / "referenceImg.png"
LOGOS_DIR      = PROJECT_ROOT / "logos"
LOGS_DIR       = PROJECT_ROOT / "logs"
SNIPED_DIR     = PROJECT_ROOT / "sniped"

# ── Matching config ───────────────────────────────────────────────────────────
ORB_MATCH_RATIO      = 0.18   # ≥18 % of keypoints match → ORB says yes
HIST_DISTANCE_THRESH = 0.55   # ≤0.55 Bhattacharyya → histogram says yes

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
    ts = datetime.now().strftime("%H:%M:%S")
    _log_lines.append(f"[{ts}] {msg}")
    _flush()


# ── Load reference ─────────────────────────────────────────────────────────────
def load_reference() -> tuple[np.ndarray, np.ndarray]:
    if not REFERENCE_PATH.exists():
        console.print(f"\n[bold red]✘  Reference image not found:[/bold red] {REFERENCE_PATH}")
        sys.exit(1)
    img = cv2.imread(str(REFERENCE_PATH))
    if img is None:
        console.print(f"\n[bold red]✘  Cannot read reference image:[/bold red] {REFERENCE_PATH}")
        sys.exit(1)
    return cv2.cvtColor(img, cv2.COLOR_BGR2GRAY), img


# ── ORB feature matching ───────────────────────────────────────────────────────
_orb = cv2.ORB_create(nfeatures=500)
_bf  = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)

def orb_similarity(ref_gray: np.ndarray, tgt_gray: np.ndarray) -> float:
    kp1, des1 = _orb.detectAndCompute(ref_gray, None)
    kp2, des2 = _orb.detectAndCompute(tgt_gray, None)
    if des1 is None or des2 is None or len(kp1) == 0 or len(kp2) == 0:
        return 0.0
    matches = _bf.match(des1, des2)
    good    = [m for m in matches if m.distance < 60]
    base    = min(len(kp1), len(kp2))
    return len(good) / base if base else 0.0


# ── Histogram similarity ───────────────────────────────────────────────────────
def hist_distance(ref_bgr: np.ndarray, tgt_bgr: np.ndarray) -> float:
    def _hist(img):
        h = cv2.calcHist([img], [0, 1, 2], None, [32, 32, 32],
                         [0, 256, 0, 256, 0, 256])
        cv2.normalize(h, h)
        return h
    return cv2.compareHist(_hist(ref_bgr), _hist(tgt_bgr), cv2.HISTCMP_BHATTACHARYYA)


# ── Per-image comparison ───────────────────────────────────────────────────────
def compare_image(path: Path, ref_gray: np.ndarray, ref_bgr: np.ndarray) -> dict:
    result = {"path": path, "orb_score": 0.0, "hist_dist": 1.0, "matched": False, "error": None}
    img = cv2.imread(str(path))
    if img is None:
        result["error"] = "unreadable"
        return result
    try:
        gray             = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        orb_score        = orb_similarity(ref_gray, gray)
        hist_d           = hist_distance(ref_bgr, img)
        result["orb_score"] = orb_score
        result["hist_dist"] = hist_d
        result["matched"]   = (orb_score >= ORB_MATCH_RATIO) or (hist_d <= HIST_DISTANCE_THRESH)
    except Exception as e:
        result["error"] = str(e)
    return result


# ── Discover all logos ─────────────────────────────────────────────────────────
def discover_folders() -> list[tuple[str, list[Path]]]:
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


# ── Copy non-matched images for ONE folder → sniped/<folder>/ ─────────────────
def snipe_folder(folder_data: dict) -> tuple[int, int]:
    """
    Called immediately after a folder is processed.
    Returns (copied, skipped).
    """
    folder_name = folder_data["folder"]
    dest_folder = SNIPED_DIR / folder_name
    copied = skipped = 0

    for img in folder_data["images"]:
        if img["matched"] or img["error"]:
            if img["error"]:
                skipped += 1
            continue
        src: Path = img["path"]
        dst: Path = dest_folder / src.name
        try:
            dest_folder.mkdir(parents=True, exist_ok=True)
            shutil.copy2(src, dst)
            log(f"SNIPED  {folder_name}/{src.name}  →  {dst}")
            copied += 1
        except Exception as e:
            log(f"COPY-ERROR  {src}  →  {dst}  : {e}")
            skipped += 1

    return copied, skipped


# ── Pretty summary table ───────────────────────────────────────────────────────
def render_summary(all_results: list[dict], elapsed: float,
                   total_sniped: int, total_snipe_err: int):

    console.print()
    console.rule("[bold cyan]  S N I P E R   R E S U L T S  [/bold cyan]", style="cyan")
    console.print()

    # ── Folder summary ──
    folder_table = Table(
        box=box.SIMPLE_HEAVY,
        show_header=True,
        header_style="bold cyan",
        border_style="dim cyan",
        expand=False,
        title="[bold]Folder Match Summary[/bold]",
        title_style="bold white",
    )
    folder_table.add_column("Folder",         style="white",       no_wrap=True, min_width=30)
    folder_table.add_column("Scanned",        style="dim white",   justify="right")
    folder_table.add_column("Matches",        style="bold green",  justify="right")
    folder_table.add_column("Not Matched",    style="bold yellow", justify="right")
    folder_table.add_column("Match Rate",     style="yellow",      justify="right")
    folder_table.add_column("Errors",         style="red dim",     justify="right")

    total_images = total_matches = total_not_matched = total_errors = 0

    for fd in all_results:
        imgs        = fd["images"]
        n           = len(imgs)
        matched     = sum(1 for i in imgs if i["matched"])
        errors      = sum(1 for i in imgs if i["error"])
        not_matched = n - matched - errors
        rate        = f"{matched/n*100:.0f}%" if n else "—"

        total_images      += n
        total_matches     += matched
        total_not_matched += not_matched
        total_errors      += errors

        folder_table.add_row(
            fd["folder"],
            str(n),
            f"[bold green]{matched}[/bold green]" if matched else "[dim]0[/dim]",
            f"[bold yellow]{not_matched}[/bold yellow]" if not_matched else "[dim]0[/dim]",
            rate,
            str(errors) if errors else "[dim]0[/dim]",
        )

    console.print(folder_table)

    # ── Matches / errors detail ──
    match_rows = [
        (fd["folder"], img)
        for fd in all_results
        for img in fd["images"]
        if img["matched"] or img["error"]
    ]
    if match_rows:
        console.print()
        dt = Table(
            box=box.MINIMAL_DOUBLE_HEAD,
            show_header=True,
            header_style="bold magenta",
            border_style="dim magenta",
            expand=False,
            title="[bold]Matches & Errors[/bold]",
            title_style="bold white",
        )
        dt.add_column("Folder",    style="white",  no_wrap=True, min_width=25)
        dt.add_column("File",      style="cyan",   no_wrap=True, min_width=30)
        dt.add_column("ORB",       style="yellow", justify="right")
        dt.add_column("Hist",      style="yellow", justify="right")
        dt.add_column("Result",    justify="center")
        for folder_name, img in match_rows:
            if img["error"]:
                dt.add_row(folder_name, img["path"].name, "—", "—",
                           f"[red]ERR: {img['error'][:30]}[/red]")
            else:
                dt.add_row(folder_name, img["path"].name,
                           f"{img['orb_score']:.3f}", f"{img['hist_dist']:.3f}",
                           "[bold green]✔  MATCH[/bold green]")
        console.print(dt)

    # ── Sniped (non-matched) detail ──
    nomatch_rows = [
        (fd["folder"], img)
        for fd in all_results
        for img in fd["images"]
        if not img["matched"] and not img["error"]
    ]
    if nomatch_rows:
        console.print()
        st = Table(
            box=box.MINIMAL_DOUBLE_HEAD,
            show_header=True,
            header_style="bold yellow",
            border_style="dim yellow",
            expand=False,
            title="[bold]Sniped  (non-matched → sniped/)[/bold]",
            title_style="bold white",
        )
        st.add_column("Folder",      style="white",  no_wrap=True, min_width=25)
        st.add_column("File",        style="cyan",   no_wrap=True, min_width=30)
        st.add_column("ORB",         style="dim",    justify="right")
        st.add_column("Hist",        style="dim",    justify="right")
        st.add_column("Dest",        style="yellow", no_wrap=True)
        for folder_name, img in nomatch_rows:
            st.add_row(
                folder_name,
                img["path"].name,
                f"{img['orb_score']:.3f}",
                f"{img['hist_dist']:.3f}",
                str(Path("sniped") / folder_name / img["path"].name),
            )
        console.print(st)

    # ── Totals panel ──
    console.print()
    console.print(
        Panel(
            f"  [bold white]Folders scanned  :[/bold white] [cyan]{len(all_results)}[/cyan]\n"
            f"  [bold white]Images scanned   :[/bold white] [cyan]{total_images}[/cyan]\n"
            f"  [bold white]Total matches    :[/bold white] [bold green]{total_matches}[/bold green]\n"
            f"  [bold white]Not matched      :[/bold white] [bold yellow]{total_not_matched}[/bold yellow]\n"
            f"  [bold white]Total errors     :[/bold white] [red]{total_errors}[/red]\n"
            f"  [bold white]Sniped (copied)  :[/bold white] [yellow]{total_sniped}[/yellow]"
            + (f"  [dim]({total_snipe_err} copy errors)[/dim]" if total_snipe_err else "") + "\n"
            f"  [bold white]Sniped folder    :[/bold white] [dim]{SNIPED_DIR}[/dim]\n"
            f"  [bold white]Elapsed          :[/bold white] [dim]{elapsed:.1f}s[/dim]\n"
            f"  [bold white]Log              :[/bold white] [dim]{_log_file_path}[/dim]",
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
            "[bold cyan]  S N I P E R[/bold cyan]\n"
            "[dim]  OpenCV visual-similarity logo scanner[/dim]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print(f"  [dim]Reference :[/dim] [white]{REFERENCE_PATH}[/white]")
    console.print(f"  [dim]Logos root:[/dim] [white]{LOGOS_DIR}[/white]")
    console.print(f"  [dim]Sniped to :[/dim] [white]{SNIPED_DIR}[/white]")
    console.print()

    log(f"Reference : {REFERENCE_PATH}")
    log(f"Logos root: {LOGOS_DIR}")
    log(f"Sniped to : {SNIPED_DIR}")

    console.print("  [dim]Loading reference image…[/dim]")
    ref_gray, ref_bgr = load_reference()
    console.print(f"  [green]✔[/green]  Reference loaded  [{ref_bgr.shape[1]}×{ref_bgr.shape[0]}px]\n")
    log(f"Reference loaded: {ref_bgr.shape[1]}x{ref_bgr.shape[0]}")

    folders = discover_folders()
    if not folders:
        console.print("[yellow]  No PNG images found inside logos/ subfolders. Nothing to do.[/yellow]")
        sys.exit(0)

    total_images = sum(len(p) for _, p in folders)
    console.print(
        f"  [cyan]Folders[/cyan] : [white]{len(folders)}[/white]   "
        f"[cyan]Images[/cyan]  : [white]{total_images}[/white]\n"
    )
    log(f"Folders: {len(folders)}  Images: {total_images}")

    all_results:    list[dict] = []
    total_sniped    = 0
    total_snipe_err = 0
    t_start         = time.time()

    # ── One progress bar per folder, transient=True so completed bars scroll away ──
    for folder_name, png_paths in folders:
        folder_data = {"folder": folder_name, "images": []}

        with Progress(
            SpinnerColumn(spinner_name="dots", style="cyan"),
            TextColumn("  [bold cyan]{task.description}[/bold cyan]"),
            BarColumn(bar_width=36, style="cyan",
                      complete_style="bold cyan", finished_style="bold green"),
            TaskProgressColumn(style="white"),
            TextColumn("[dim]{task.fields[status]}[/dim]"),
            TimeElapsedColumn(),
            console=console,
            transient=True,          # clears itself when done → terminal stays scrollable
        ) as progress:
            task = progress.add_task(
                f"{folder_name[:40]}",
                total=len(png_paths),
                status="",
            )

            for img_path in png_paths:
                progress.update(task, status=img_path.name[:45])
                result = compare_image(img_path, ref_gray, ref_bgr)
                folder_data["images"].append(result)
                log(
                    f"{folder_name}/{img_path.name}  "
                    f"orb={result['orb_score']:.3f}  "
                    f"hist={result['hist_dist']:.3f}  "
                    f"match={result['matched']}  "
                    f"err={result['error']}"
                )
                progress.advance(task)

        # ── Folder done: print a permanent one-liner ──
        imgs        = folder_data["images"]
        matched     = sum(1 for i in imgs if i["matched"])
        not_matched = sum(1 for i in imgs if not i["matched"] and not i["error"])
        errors      = sum(1 for i in imgs if i["error"])
        console.print(
            f"  [green]✔[/green]  [white]{folder_name:<35}[/white]"
            f"  scanned [cyan]{len(imgs)}[/cyan]"
            f"  matched [green]{matched}[/green]"
            f"  not matched [yellow]{not_matched}[/yellow]"
            + (f"  [red]errors {errors}[/red]" if errors else "")
        )

        # ── Copy non-matched immediately ──
        copied, skipped = snipe_folder(folder_data)
        if copied:
            console.print(
                f"       [dim]↳ sniped {copied} image(s) → sniped/{folder_name}/[/dim]"
            )
        total_sniped    += copied
        total_snipe_err += skipped

        all_results.append(folder_data)
        log(f"Folder done: {folder_name}  matched={matched} not_matched={not_matched} "
            f"sniped={copied} errors={errors}")

    elapsed = time.time() - t_start

    render_summary(all_results, elapsed, total_sniped, total_snipe_err)
    log(f"Total sniped={total_sniped} snipe_errors={total_snipe_err}")
    log("Done.")
    _flush()


if __name__ == "__main__":
    main()