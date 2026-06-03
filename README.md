# Alting
![Java](https://img.shields.io/badge/Java-24-B07219?style=flat-square&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/Python-3-3776AB?style=flat-square&logo=python&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Playwright](https://img.shields.io/badge/Playwright-scraping-2EAD33?style=flat-square&logo=playwright&logoColor=white)
![Status](https://img.shields.io/badge/status-active%20dev-brightgreen?style=flat-square)
 
</div>

> Automates redundant searching and info extraction with given parameters — so you don't have to do it 50 times.


A companion Python script (`fetch_logos.py`) handles fetching college logos from Wikipedia separately.

---

## Tech stack

| Layer | Tool |
|---|---|
| Language | Java 24 |
| UI | Swing + [FlatLaf](https://www.formdev.com/flatlaf/) |
| Scraping | [Jsoup](https://jsoup.org/) + [Playwright (Java)](https://playwright.dev/java/) |
| HTTP | Apache HttpClient |
| JSON | Jackson + org.json |
| Database | MySQL 8 |
| Build | Maven |
| Logo fetching | Python 3 + Playwright + Pillow |

---

## Project structure

```
Alting/
├── src/main/               # Java source — scrapers, UI, DB layer
├── imgDataset/             # Collected college images
├── logs/                   # Run logs
├── fetch_logos.py          # Wikipedia logo fetcher (Python)
├── scrapperDB.sql          # DB schema
└── pom.xml
```

---

## Setup

**Prerequisites:** Java 24, Maven, MySQL 8, Python 3 (for logo fetching)

```bash
# 1. Clone
git clone https://github.com/Bonsa-Dereje/Alting.git
cd Alting

# 2. Set up the database
mysql -u root -p < scrapperDB.sql

# 3. Build and run
mvn exec:java

# 4. (Optional) Fetch logos
pip install playwright pillow requests
playwright install chromium
python fetch_logos.py
```

> Make sure your DB credentials match what's configured in the Java source before running.

---

## Logo fetcher

`fetch_logos.py` takes a folder of college name subfolders, searches Wikipedia for each one, downloads all images from the article, crops whitespace, and saves them as PNGs. It skips colleges it's already processed and writes a timestamped log to `logs/` after every run — including a summary table flagging any colleges with no Wikipedia article or no images found.

```bash
python fetch_logos.py              # uses ./colleges/ folder by default
python fetch_logos.py my_list      # uses ./my_list/ folder
```

---

## Status

Active development. Schema and scraping targets may shift as sources change.
