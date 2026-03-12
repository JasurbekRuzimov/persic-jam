package com.gitautomator;

/**
 * ╔═════════════════════════════════════════════════════════════════╗
 * ║  GitHub Auto Repository Creator  v2.0  (Java 21)              ║
 * ║                                                                ║
 * ║  HOW IT WORKS                                                  ║
 * ║  ─────────────────────────────────────────────────────────    ║
 * ║  1. Calculates start date = today − (totalDays − 1)           ║
 * ║  2. Assigns each repo a backdated commit timestamp            ║
 * ║  3. Creates ALL repos in ONE session right now                ║
 * ║  4. Commits appear on past dates → green contribution graph   ║
 * ║                                                                ║
 * ║  EXAMPLE  (today = 2025-03-11, 82 repos, 2/day = 41 days)    ║
 * ║  ─────────────────────────────────────────────────────────    ║
 * ║  Day  1 → 2025-01-30  arithmetic-operators-1 / -2             ║
 * ║  Day  2 → 2025-01-31  comparison-operators-1 / -2             ║
 * ║  ...                                                           ║
 * ║  Day 41 → 2025-03-11  class-declaration-1 / -2                ║
 * ║                                                                ║
 * ╠═════════════════════════════════════════════════════════════════╣
 * ║  COMMANDS                                                      ║
 * ║  run (default)  Create ALL repos with backdated commits        ║
 * ║  schedule       Print the full date plan (no API calls)       ║
 * ║  progress       Show how many repos are done                  ║
 * ║  dry-run        Simulate everything (no GitHub API calls)     ║
 * ║  reset          Delete state file and start fresh             ║
 * ╚═════════════════════════════════════════════════════════════════╝
 *
 * SETUP:
 *   export GITHUB_TOKEN=ghp_xxxxxxxxxxxx
 *   export GITHUB_USERNAME=your-username
 *   java -jar github-auto-repo.jar
 */
public class Main {

    public static void main(String[] args) {
        printBanner();

        String command = args.length > 0 ? args[0].toLowerCase() : "run";

        // ── Commands that don't need a token ──────────────────────────────
        if (command.equals("schedule") || command.equals("progress")) {
            AppConfig config = new AppConfig();
            RepoCreator creator = new RepoCreator(config);
            creator.initializeSchedule();
            if (command.equals("schedule"))  creator.printSchedule();
            else                             creator.printProgress();
            return;
        }

        if (command.equals("reset")) {
            AppConfig config = new AppConfig();
            java.io.File f = new java.io.File(config.getStateFile());
            if (f.delete()) System.out.println("✅ State file deleted. Run again to start fresh.");
            else            System.out.println("⚠️  State file not found.");
            return;
        }

        // ── Commands that need token ──────────────────────────────────────
        AppConfig config = new AppConfig();
        try { config.validate(); }
        catch (IllegalStateException e) {
            System.err.println("\n❌ " + e.getMessage());
            printHelp();
            System.exit(1);
        }

        System.out.println(config + "\n");

        RepoCreator creator = new RepoCreator(config);

        try {
            switch (command) {
                case "run", "start" -> {
                    creator.initializeSchedule();
                    creator.runAll();
                }
                case "dry-run" -> {
                    // Override dry-run via system property
                    System.setProperty("dry.run", "true");
                    AppConfig dryConfig = new AppConfig() {
                        @Override public boolean isDryRun() { return true; }
                    };
                    RepoCreator dryCreator = new RepoCreator(dryConfig);
                    dryCreator.initializeSchedule();
                    dryCreator.runAll();
                }
                default -> {
                    System.err.println("❌ Unknown command: " + command);
                    printHelp();
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.err.println("\n❌ Fatal: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  🐙 GitHub Auto Repository Creator  v2.0  (Java 21)     ║");
        System.out.println("║  Creates ALL repos at once with backdated commits        ║");
        System.out.println("║  → fills contribution graph from past dates to today     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printHelp() {
        System.out.println("""
            USAGE:
              java -jar github-auto-repo.jar [command]

            COMMANDS:
              run (default)   Create ALL repos now with backdated commits
              schedule        Show full date plan (no API calls needed)
              progress        Show how many repos have been created
              dry-run         Simulate run (no GitHub API calls)
              reset           Clear state and start fresh

            REQUIRED CONFIG  (env var or config.properties):
              GITHUB_TOKEN    = ghp_xxxxxxxxxx
              GITHUB_USERNAME = your-github-username

            OPTIONAL CONFIG:
              REPOS_PER_DAY   = 2       (default: 2 repos per day)
              MAKE_PRIVATE    = false   (default: public repos)
              DELAY_MS        = 2000    (ms between repo creations)
              DRY_RUN         = false
              STATE_FILE      = repo-state.json
            """);
    }
}
