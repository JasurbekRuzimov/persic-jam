@echo off
REM ═══════════════════════════════════════════════════════════════
REM  run.bat — Daily runner for GitHub Auto Repo Creator (Windows)
REM  Schedule with Task Scheduler to run every day.
REM ═══════════════════════════════════════════════════════════════

SET JAR=%~dp0target\github-auto-repo-jar-with-dependencies.jar

REM ── Set these or use config.properties ───────────────────────
IF NOT DEFINED GITHUB_TOKEN (
    echo ERROR: GITHUB_TOKEN not set
    exit /b 1
)
IF NOT DEFINED GITHUB_USERNAME (
    echo ERROR: GITHUB_USERNAME not set
    exit /b 1
)

IF NOT EXIST "%JAR%" (
    echo Building JAR...
    cd "%~dp0"
    mvn package -q
)

echo Starting daily batch...
java -jar "%JAR%" daily
echo Done!
