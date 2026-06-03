import os
import sys
import time
import re
import requests
from urllib.parse import quote
from datetime import datetime
from PIL import Image
from playwright.sync_api import sync_playwright

PROJECT_ROOT = os.path.dirname(os.path.abspath(__file__))
LOGOS_DIR    = os.path.join(PROJECT_ROOT, "logos")
LOGS_DIR     = os.path.join(PROJECT_ROOT, "logs")

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
    "Referer": "https://en.wikipedia.org/"
}

WIKIPEDIA_API = "https://en.wikipedia.org/w/api.php"

SKIP_KEYWORDS = [
    "commons-logo", "edit-ltr", "edit-rtl", "red_pog", "blue_pog",
    "question_mark", "wikimedia-logo", "powered_by", "wiki_letter",
    "increase2", "decrease2", "steady2", "disambig", "folder",
    "portal-puzzle", "icon", "button", "star", "lock", "sound",
    "audio", "video", "speaker", "arrow", "sig_", "signatur",
    "placeholder", "noimage", "missing"
]

CROP_PADDING = 10  # pixels of padding around cropped logo

# ----------------------------
# LOG FILE SETUP
# ----------------------------
_log_lines: list[str] = []
_log_file_path: str = ""

def init_log_file():
    global _log_file_path
    os.makedirs(LOGS_DIR, exist_ok=True)
    run_timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    _log_file_path = os.path.join(LOGS_DIR, f"run_{run_timestamp}.txt")
    # Also maintain a rolling "latest" log for convenience
    _write_log_header(run_timestamp)

def _write_log_header(timestamp: str):
    header = [
        "=" * 60,
        f"  Logo Scraper Run — {timestamp}",
        "=" * 60,
        ""
    ]
    _log_lines.extend(header)
    for line in header:
        print(line, flush=True)

def flush_log():
    """Write all buffered log lines to disk."""
    if _log_file_path:
        with open(_log_file_path, "w", encoding="utf-8") as f:
            f.write("\n".join(_log_lines))
        # Also overwrite logs/latest.txt
        latest_path = os.path.join(LOGS_DIR, "latest.txt")
        with open(latest_path, "w", encoding="utf-8") as f:
            f.write("\n".join(_log_lines))


# ----------------------------
# LOGGER
# ----------------------------
def log(msg: str):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    print(line, flush=True)
    _log_lines.append(line)
    # Flush to disk after every line so logs survive crashes
    flush_log()


# ----------------------------
# PROGRESS BAR
# ----------------------------
def print_progress(done: int, total: int, label: str = ""):
    if total == 0:
        return
    pct   = done / total
    width = 40
    filled = int(width * pct)
    bar   = "█" * filled + "░" * (width - filled)
    line  = f"\n  Progress: [{bar}] {done}/{total} ({pct:.0%})  {label}\n"
    print(line, flush=True)
    _log_lines.append(line)
    flush_log()


# ----------------------------
# RESUME: FIND ALREADY-DONE COLLEGES
# ----------------------------
def get_already_done(logos_dir: str) -> set[str]:
    """
    Returns a set of safe_name'd college names that already have a folder
    in the logos directory (i.e., were processed in a previous run).
    A folder is considered 'done' if it exists AND contains at least one .png file.
    """
    done = set()
    if not os.path.isdir(logos_dir):
        return done
    for entry in os.listdir(logos_dir):
        folder = os.path.join(logos_dir, entry)
        if os.path.isdir(folder):
            pngs = [f for f in os.listdir(folder) if f.lower().endswith(".png")]
            if pngs:
                done.add(entry)
    return done


# ----------------------------
# SHOULD WE SKIP THIS FILENAME?
# ----------------------------
def is_skip_file(filename: str) -> bool:
    low = filename.lower()
    return any(k in low for k in SKIP_KEYWORDS)


# ----------------------------
# SANITIZE FILENAME FOR OS
# ----------------------------
def safe_name(name: str) -> str:
    return re.sub(r'[<>:"/\\|?*]', "_", name)


# ----------------------------
# CROP WHITESPACE WITH PILLOW getbbox()
# ----------------------------
def crop_whitespace(path: str, padding: int = CROP_PADDING):
    try:
        img = Image.open(path).convert("RGBA")
        r, g, b, a = img.split()

        background = Image.new("RGBA", img.size, (255, 255, 255, 255))
        background.paste(img, mask=a)
        flat = background.convert("RGB")

        gray     = flat.convert("L")
        inverted = gray.point(lambda px: 0 if px >= 245 else 255)
        bbox     = inverted.getbbox()

        if not bbox:
            log(f"    Crop: no content found, keeping original")
            return

        left, top, right, bottom = bbox
        width, height = img.size
        left   = max(0,      left   - padding)
        top    = max(0,      top    - padding)
        right  = min(width,  right  + padding)
        bottom = min(height, bottom + padding)

        cropped = flat.crop((left, top, right, bottom))
        cropped.save(path)
        log(f"    Crop: {width}x{height} → {right - left}x{bottom - top} (padding={padding}px)")

    except Exception as e:
        log(f"    Crop error: {e}")


# ----------------------------
# STEP 1: RESOLVE PAGE TITLE VIA SEARCH API
# ----------------------------
def resolve_page_title(college_name: str) -> str | None:
    log(f"  Searching API: {college_name}")
    try:
        r = requests.get(WIKIPEDIA_API, headers=HEADERS, timeout=15, params={
            "action":    "opensearch",
            "search":    college_name,
            "limit":     1,
            "redirects": "resolve",
            "format":    "json"
        })
        data = r.json()
        titles = data[1]
        if titles:
            log(f"  Found title: {titles[0]}")
            return titles[0]
    except Exception as e:
        log(f"  Search API error: {e}")
    log(f"  No article found for: {college_name}")
    return None


# ----------------------------
# STEP 2: GET ALL IMAGE FILENAMES ON THE PAGE
# ----------------------------
def get_page_image_filenames(page_title: str) -> list[str]:
    filenames = []
    params = {
        "action":  "query",
        "titles":  page_title,
        "prop":    "images",
        "imlimit": "max",
        "format":  "json"
    }

    while True:
        try:
            r = requests.get(WIKIPEDIA_API, headers=HEADERS, timeout=15, params=params)
            data = r.json()
        except Exception as e:
            log(f"  Images API error: {e}")
            break

        pages = data.get("query", {}).get("pages", {})
        for page in pages.values():
            for img in page.get("images", []):
                title    = img.get("title", "")
                filename = title.replace("File:", "").replace("file:", "").strip()
                if filename and not is_skip_file(filename):
                    filenames.append(filename)

        if "continue" in data:
            params["imcontinue"] = data["continue"]["imcontinue"]
        else:
            break

    log(f"  API returned {len(filenames)} usable image filename(s)")
    return filenames


# ----------------------------
# STEP 3: SCREENSHOT + CROP EACH IMAGE
# ----------------------------
def screenshot_images(filenames: list[str], out_dir: str, browser) -> int:
    saved = 0
    total = len(filenames)

    for idx, filename in enumerate(filenames, 1):
        log(f"    [{idx}/{total}] {filename}")

        file_url  = f"https://en.wikipedia.org/wiki/Special:FilePath/{quote(filename)}"
        base      = os.path.splitext(filename)[0]
        save_path = os.path.join(out_dir, f"{safe_name(base)}.png")

        log(f"    Opening: {file_url}")

        try:
            page = browser.new_page()
            page.set_viewport_size({"width": 1920, "height": 1080})
            page.goto(file_url, timeout=30000, wait_until="networkidle")

            try:
                page.wait_for_selector("img", timeout=10000)
            except:
                pass

            page.wait_for_timeout(1500)

            img_el = page.query_selector("img")

            if img_el:
                img_el.screenshot(path=save_path)
                log(f"    ✅ Screenshot saved (element): {os.path.basename(save_path)}")
            else:
                page.screenshot(path=save_path, full_page=False)
                log(f"    ✅ Screenshot saved (full page): {os.path.basename(save_path)}")

            page.close()

            crop_whitespace(save_path)
            saved += 1

        except Exception as e:
            log(f"    ❌ Screenshot failed: {e}")
            try:
                page.close()
            except:
                pass

        if idx < total:
            log(f"    Waiting 3s...")
            time.sleep(3)

    return saved


# ----------------------------
# PROCESS ONE COLLEGE
# ----------------------------
def process_college(college_name: str, browser) -> bool:
    log(f"\n{'='*50}")
    log(f"COLLEGE: {college_name}")
    log(f"{'='*50}")

    page_title = resolve_page_title(college_name)
    if not page_title:
        log(f"  ❌ No Wikipedia article found")
        return False

    time.sleep(1)

    filenames = get_page_image_filenames(page_title)
    if not filenames:
        log(f"  ❌ No images found on page")
        return False

    out_dir = os.path.join(LOGOS_DIR, safe_name(college_name))
    os.makedirs(out_dir, exist_ok=True)
    log(f"  Saving to: {out_dir}")

    saved = screenshot_images(filenames, out_dir, browser)

    log(f"\n  ✅ Saved {saved}/{len(filenames)} images → {out_dir}")
    return saved > 0


# ----------------------------
# MAIN
# ----------------------------
def main():
    init_log_file()

    name_param = sys.argv[1] if len(sys.argv) > 1 else "colleges"
    base_dir   = os.path.join(PROJECT_ROOT, name_param)

    if not os.path.isdir(base_dir):
        log(f"❌ Directory not found: {base_dir}")
        log(f"   Expected: {base_dir}/<CollegeName>/")
        sys.exit(1)

    all_college_names = sorted([
        d for d in os.listdir(base_dir)
        if os.path.isdir(os.path.join(base_dir, d))
    ])

    if not all_college_names:
        log(f"❌ No subfolders found in: {base_dir}")
        sys.exit(1)

    total_colleges = len(all_college_names)

    # --- RESUME DETECTION ---
    os.makedirs(LOGOS_DIR, exist_ok=True)
    already_done_safe_names = get_already_done(LOGOS_DIR)

    # Match colleges to their safe_name equivalents
    skipped   = [n for n in all_college_names if safe_name(n) in already_done_safe_names]
    remaining = [n for n in all_college_names if safe_name(n) not in already_done_safe_names]

    log(f"📋 Total colleges in '{name_param}' folder : {total_colleges}")
    log(f"✅ Already completed (logos exist)         : {len(skipped)}")
    log(f"🔄 Remaining to process                    : {len(remaining)}")
    log(f"📁 Logos output root                       : {LOGOS_DIR}")
    log(f"📝 Log file                                : {_log_file_path}")

    if skipped:
        log(f"\n--- Skipping already-done colleges ---")
        for name in skipped:
            log(f"  ⏭  {name}")

    # Show overall progress bar (already done vs total)
    print_progress(len(skipped), total_colleges, "overall (resume point)")

    if not remaining:
        log("\n🎉 All colleges already processed. Nothing to do!")
        flush_log()
        return

    log(f"\n--- Starting processing for {len(remaining)} college(s) ---\n")

    results       = {"ok": 0, "fail": 0}
    completed_so_far = len(skipped)  # how many are done at the start of this run

    with sync_playwright() as p:
        browser = p.chromium.launch(
            headless=True,
            args=["--no-sandbox", "--disable-gpu", "--window-size=1920,1080"]
        )

        for i, name in enumerate(remaining, 1):
            log(f"\n[This run: {i}/{len(remaining)}]")
            success = process_college(name, browser)
            results["ok" if success else "fail"] += 1

            if success:
                completed_so_far += 1

            # Show progress relative to the full original list
            print_progress(
                completed_so_far,
                total_colleges,
                f"total — just finished: {name}"
            )

            if i < len(remaining):
                log(f"\n  Waiting 3s before next college...")
                time.sleep(3)

        browser.close()

    log("\n" + "=" * 50)
    log("DONE — Run Summary")
    log("=" * 50)
    log(f"  📋 Total colleges      : {total_colleges}")
    log(f"  ⏭  Skipped (done prev) : {len(skipped)}")
    log(f"  ✅ Succeeded this run  : {results['ok']}")
    log(f"  ❌ Failed  this run    : {results['fail']}")
    log(f"  📁 Output              : {LOGOS_DIR}")
    log(f"  📝 Log saved to        : {_log_file_path}")
    log("=" * 50)

    print_progress(completed_so_far, total_colleges, "final overall progress")
    flush_log()


if __name__ == "__main__":
    main()