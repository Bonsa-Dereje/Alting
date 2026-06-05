"""
labeler.py
----------
Interactive OpenCV image labeler for sniped/ folders.

Workflow
--------
1. Iterates every subfolder inside  sniped/
2. Displays all PNGs in a keyboard-labeled grid (keys a s d f g h j k l …)
3. Press a label key  → toggle that image as KEEP  (green border + ✔)
4. Press  q           → open the same subfolder inside logos/ interactively
                        Select images with label keys, Enter to confirm & advance
                        to next folder.  Esc to return to sniped/ view without saving.
5. Press  Enter       → confirm sniped/ selection and advance to next folder
6. Press  Esc         → skip this folder entirely (nothing saved)
7. Kept images are saved to  handPicked/<college_name>/<original_filename>.png
   (always a subdirectory, even for a single image)

Requirements:  pip install opencv-python rich
"""

import sys
import shutil
from pathlib import Path

try:
    import cv2
    import numpy as np
    from rich.console import Console
    from rich.panel import Panel
    from rich import box
    from rich.table import Table
except ImportError:
    print("[ERROR] Missing deps.  Run:  pip install opencv-python rich")
    sys.exit(1)

console = Console()

# ── Paths ─────────────────────────────────────────────────────────────────────
PROJECT_ROOT  = Path(__file__).parent.resolve()
SNIPED_DIR    = PROJECT_ROOT / "sniped"
LOGOS_DIR     = PROJECT_ROOT / "logos"
HANDPICKED    = PROJECT_ROOT / "handPicked"

# ── Grid / display config ─────────────────────────────────────────────────────
THUMB_W       = 220          # px – thumbnail cell width
THUMB_H       = 180          # px – thumbnail cell height
COLS          = 9            # images per row  (matches 9 label keys per row)
PADDING       = 12           # px between cells
BG_COLOR      = (30, 30, 30) # dark background
LABEL_KEYS    = list("asdfghjklzxcvbnmqwertyuiop")  # q handled specially below
KEEP_KEYS     = [k for k in LABEL_KEYS if k != "q"]  # q is the peek key

# ── Label key → index mapping ─────────────────────────────────────────────────
def key_to_idx(char: str, n_images: int) -> int | None:
    """Return image index for a pressed key, or None if out of range / not a label."""
    if char == "q":
        return None          # special: peek
    try:
        idx = KEEP_KEYS.index(char)
    except ValueError:
        return None
    return idx if idx < n_images else None


# ── Build a grid image ─────────────────────────────────────────────────────────
def build_grid(
    images: list[np.ndarray],
    filenames: list[str],
    selected: set[int],
    mode: str = "sniped",       # "sniped" | "peek"
    folder_name: str = "",
) -> np.ndarray:
    n     = len(images)
    cols  = min(COLS, n)
    rows  = (n + cols - 1) // cols

    cell_w = THUMB_W + PADDING
    cell_h = THUMB_H + PADDING
    header = 52                 # px for top title bar

    canvas_w = cols * cell_w + PADDING
    canvas_h = rows * cell_h + PADDING + header

    canvas = np.full((canvas_h, canvas_w, 3), BG_COLOR, dtype=np.uint8)

    # ── Title bar ──
    if mode == "peek":
        title = f"  PEEK (interactive): logos/{folder_name}"
        hint  = "  [key] toggle keep   [Enter] confirm & next folder   [Esc] back to sniped/"
    else:
        title = f"  SNIPED: {folder_name}"
        hint  = "  [key] toggle keep   [Enter] confirm   [Esc] skip   [q] peek logos/ (interactive)"

    cv2.putText(canvas, title, (8, 20),
                cv2.FONT_HERSHEY_SIMPLEX, 0.55, (0, 210, 255), 1, cv2.LINE_AA)
    cv2.putText(canvas, hint,  (8, 42),
                cv2.FONT_HERSHEY_SIMPLEX, 0.38, (160, 160, 160), 1, cv2.LINE_AA)

    for idx, (img, fname) in enumerate(zip(images, filenames)):
        row = idx // cols
        col = idx  % cols

        x = PADDING + col * cell_w
        y = header  + PADDING + row * cell_h

        # ── Resize preserving aspect ratio ──
        h0, w0 = img.shape[:2]
        scale  = min(THUMB_W / w0, THUMB_H / h0)
        nw, nh = max(1, int(w0 * scale)), max(1, int(h0 * scale))
        thumb  = cv2.resize(img, (nw, nh), interpolation=cv2.INTER_AREA)

        # ── Paste onto cell background ──
        cell = np.full((THUMB_H, THUMB_W, 3), (55, 55, 55), dtype=np.uint8)
        ox   = (THUMB_W - nw) // 2
        oy   = (THUMB_H - nh) // 2
        cell[oy:oy+nh, ox:ox+nw] = thumb

        # ── Border: green if selected, dim grey otherwise ──
        border_color = (0, 220, 80) if idx in selected else (90, 90, 90)
        border_thick = 3           if idx in selected else 1
        cv2.rectangle(cell, (0, 0), (THUMB_W-1, THUMB_H-1),
                      border_color, border_thick)

        # ── Label key badge (top-left) ──
        if idx < len(KEEP_KEYS):
            label_char = KEEP_KEYS[idx].upper()
            # dark badge background
            cv2.rectangle(cell, (0, 0), (22, 22), (0, 0, 0), -1)
            text_color = (0, 220, 80) if idx in selected else (200, 200, 200)
            cv2.putText(cell, label_char, (4, 16),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.55, text_color, 1, cv2.LINE_AA)

        # ── ✔ overlay if selected ──
        if idx in selected:
            cv2.putText(cell, "KEEP", (THUMB_W - 52, THUMB_H - 6),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.42, (0, 220, 80), 1, cv2.LINE_AA)

        # ── Filename (bottom, truncated) ──
        short = fname if len(fname) <= 26 else fname[:23] + "…"
        cv2.putText(cell, short, (4, THUMB_H - 6),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.30, (180, 180, 180), 1, cv2.LINE_AA)

        # ── Paste cell into canvas ──
        canvas[y:y+THUMB_H, x:x+THUMB_W] = cell

    return canvas


# ── Load images from a folder ─────────────────────────────────────────────────
def load_folder_images(folder: Path) -> tuple[list[np.ndarray], list[str], list[Path]]:
    paths = sorted(folder.glob("*.png"))
    imgs, names, good_paths = [], [], []
    for p in paths:
        img = cv2.imread(str(p))
        if img is not None:
            imgs.append(img)
            names.append(p.name)
            good_paths.append(p)
    return imgs, names, good_paths


# ── Save selected images to handPicked/<college>/ ─────────────────────────────
def save_handpicked(
    folder_name: str,
    selected_paths: list[Path],
) -> list[Path]:
    college    = folder_name.replace(" ", "_")
    out_dir    = HANDPICKED / college
    out_dir.mkdir(parents=True, exist_ok=True)
    saved = []
    for src in selected_paths:
        dst = out_dir / src.name
        shutil.copy2(src, dst)
        saved.append(dst)
    return saved


# ── Peek mode: interactive selection from logos/<folder>/ ─────────────────────
def peek_logos(folder_name: str, win_name: str) -> tuple[int, bool]:
    """
    Shows logos/<folder_name>/ interactively.
    Returns (n_saved, advance_to_next).
      advance_to_next=True  → caller should move to the next sniped folder
      advance_to_next=False → caller redraws its own sniped view
    """
    logos_folder = LOGOS_DIR / folder_name

    if not logos_folder.exists():
        msg_canvas = np.full((200, 520, 3), BG_COLOR, dtype=np.uint8)
        cv2.putText(msg_canvas,
                    f"logos/{folder_name}/ not found",
                    (20, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 80, 255), 1)
        cv2.putText(msg_canvas,
                    "Press Esc or any key to return",
                    (20, 135), cv2.FONT_HERSHEY_SIMPLEX, 0.45, (160, 160, 160), 1)
        cv2.imshow(win_name, msg_canvas)
        cv2.waitKey(0)
        return 0, False

    imgs, names, paths = load_folder_images(logos_folder)
    if not imgs:
        return 0, False

    selected: set[int] = set()

    while True:
        grid = build_grid(imgs, names, selected, mode="peek", folder_name=folder_name)
        cv2.imshow(win_name, grid)
        raw = cv2.waitKey(0)

        if raw == -1:           # window closed
            return 0, True

        key = raw & 0xFF

        # ── Esc → return to sniped/ view without saving ──
        if key == 27:
            return 0, False

        # ── Enter → save selected and advance to next folder ──
        if key in (13, 10):
            if not selected:
                # Nothing picked — still advance
                return 0, True
            sel_paths = [paths[i] for i in sorted(selected)]
            saved     = save_handpicked(folder_name, sel_paths)
            return len(saved), True

        # ── label keys → toggle ──
        char = chr(key).lower()
        idx  = key_to_idx(char, len(imgs))
        if idx is not None:
            if idx in selected:
                selected.discard(idx)
            else:
                selected.add(idx)


# ── Process one sniped subfolder ──────────────────────────────────────────────
def process_folder(folder: Path, win_name: str) -> tuple[int, bool]:
    """
    Returns (n_saved, skipped).
    """
    imgs, names, paths = load_folder_images(folder)
    if not imgs:
        return 0, False

    folder_name = folder.name
    selected: set[int] = set()

    while True:
        grid = build_grid(imgs, names, selected,
                          mode="sniped", folder_name=folder_name)
        cv2.imshow(win_name, grid)
        raw = cv2.waitKey(0)

        if raw == -1:           # window closed
            return 0, True

        key = raw & 0xFF

        # ── Esc → skip folder ──
        if key == 27:
            return 0, True

        # ── Enter → confirm sniped/ selection ──
        if key in (13, 10):
            if not selected:
                return 0, False
            sel_paths = [paths[i] for i in sorted(selected)]
            saved     = save_handpicked(folder_name, sel_paths)
            return len(saved), False

        # ── q → interactive peek at logos/ ──
        if key == ord("q"):
            n_saved, advance = peek_logos(folder_name, win_name)
            if advance:
                # Peek confirmed with (or without) a selection — move on
                return n_saved, False
            # Esc in peek → fall through and redraw sniped/ grid
            continue

        # ── label keys → toggle ──
        char = chr(key).lower()
        idx  = key_to_idx(char, len(imgs))
        if idx is not None:
            if idx in selected:
                selected.discard(idx)
            else:
                selected.add(idx)


# ── Main ───────────────────────────────────────────────────────────────────────
def main():
    console.print()
    console.print(
        Panel(
            "[bold cyan]  L A B E L E R[/bold cyan]\n"
            "[dim]  Keyboard-driven image picker for sniped/ folders[/dim]",
            border_style="cyan",
            expand=False,
        )
    )

    if not SNIPED_DIR.exists():
        console.print(f"[bold red]✘  sniped/ not found:[/bold red] {SNIPED_DIR}")
        sys.exit(1)

    subfolders = sorted(
        [d for d in SNIPED_DIR.iterdir() if d.is_dir()]
    )
    if not subfolders:
        console.print("[yellow]  No subfolders found inside sniped/. Nothing to do.[/yellow]")
        sys.exit(0)

    console.print(f"  [cyan]Folders to review:[/cyan] [white]{len(subfolders)}[/white]")
    console.print()
    console.print("  [dim]Controls (sniped/ view):[/dim]")
    console.print("    [white]a s d f g h j k l …[/white]  toggle keep")
    console.print("    [white]Enter[/white]                 confirm & next folder")
    console.print("    [white]Esc[/white]                   skip folder")
    console.print("    [white]q[/white]                     peek logos/ interactively")
    console.print()
    console.print("  [dim]Controls (peek / logos/ view):[/dim]")
    console.print("    [white]a s d f g h j k l …[/white]  toggle keep")
    console.print("    [white]Enter[/white]                 confirm selection & next folder")
    console.print("    [white]Esc[/white]                   back to sniped/ view (no save)")
    console.print()
    console.print(
        "  [dim]Output:[/dim] [white]handPicked/<college_name>/<filename>.png[/white]"
    )
    console.print()

    WIN = "Labeler"
    cv2.namedWindow(WIN, cv2.WINDOW_NORMAL)
    cv2.resizeWindow(WIN, 1400, 860)

    summary_rows = []
    total_saved  = 0

    for i, folder in enumerate(subfolders, 1):
        console.print(
            f"  [{i}/{len(subfolders)}]  [cyan]{folder.name}[/cyan]  …"
        )
        n_saved, skipped = process_folder(folder, WIN)
        total_saved += n_saved

        if skipped:
            console.print(f"         [dim]↳ skipped[/dim]")
            summary_rows.append((folder.name, "—", "skipped"))
        elif n_saved == 0:
            console.print(f"         [dim]↳ nothing selected[/dim]")
            summary_rows.append((folder.name, "0", "none kept"))
        else:
            console.print(
                f"         [green]✔[/green]  saved [bold]{n_saved}[/bold] "
                f"image(s) → handPicked/{folder.name.replace(' ', '_')}/"
            )
            summary_rows.append((folder.name, str(n_saved), "✔"))

    cv2.destroyAllWindows()

    # ── Final summary ──
    console.print()
    console.rule("[bold cyan]  L A B E L E R   D O N E  [/bold cyan]", style="cyan")
    console.print()

    t = Table(box=box.SIMPLE_HEAVY, header_style="bold cyan",
              border_style="dim cyan", expand=False)
    t.add_column("Folder",  style="white",      min_width=30)
    t.add_column("Saved",   style="bold green",  justify="right")
    t.add_column("Status",  style="dim",         justify="center")
    for row in summary_rows:
        t.add_row(*row)
    console.print(t)

    console.print(
        Panel(
            f"  [bold white]Folders reviewed :[/bold white] [cyan]{len(subfolders)}[/cyan]\n"
            f"  [bold white]Total saved      :[/bold white] [bold green]{total_saved}[/bold green]\n"
            f"  [bold white]Output folder    :[/bold white] [dim]{HANDPICKED}[/dim]",
            title="[bold cyan]  TOTALS  [/bold cyan]",
            border_style="cyan",
            expand=False,
        )
    )
    console.print()


if __name__ == "__main__":
    main()