#!/bin/bash
# ═══════════════════════════════════════════════════════════════
#  run.sh — Daily runner for GitHub Auto Repo Creator
#  Add to crontab to run automatically every day:
#    crontab -e
#    0 9 * * * /path/to/run.sh >> /path/to/run.log 2>&1
# ═══════════════════════════════════════════════════════════════

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR="$SCRIPT_DIR/target/github-auto-repo-jar-with-dependencies.jar"

# ── Configure here (or use config.properties) ─────────────────
export GITHUB_TOKEN="${GITHUB_TOKEN:-}"
export GITHUB_USERNAME="${GITHUB_USERNAME:-}"

# ── Validate ──────────────────────────────────────────────────
if [ -z "$GITHUB_TOKEN" ] || [ -z "$GITHUB_USERNAME" ]; then
    echo "❌ ERROR: Set GITHUB_TOKEN and GITHUB_USERNAME environment variables"
    echo "   Or fill in config.properties"
    exit 1
fi

if [ ! -f "$JAR" ]; then
    echo "📦 Building JAR..."
    cd "$SCRIPT_DIR"
    mvn package -q
fi

# ── Run daily batch ───────────────────────────────────────────
echo "$(date '+%Y-%m-%d %H:%M:%S') — Starting daily batch..."
java -jar "$JAR" daily
echo "$(date '+%Y-%m-%d %H:%M:%S') — Done!"
