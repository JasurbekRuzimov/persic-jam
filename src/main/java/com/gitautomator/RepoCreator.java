package com.gitautomator;

import com.gitautomator.models.RepoState;
import com.gitautomator.models.Topic;

import java.util.List;
import java.util.Map;

/**
 * Orchestrates the full workflow.
 *
 * Default mode (run / no-arg):
 *   1. Build backdated schedule for ALL topics (first run only)
 *   2. Create EVERY pending repo right now in a single session
 *   3. Each commit gets its pre-calculated backdated timestamp
 *      → contribution graph fills in from (today − N days) to today
 */
public class RepoCreator {

    // English commit messages — varied to look organic
    private static final String[] COMMIT_TEMPLATES = {
        "feat: implement %s with practical examples",
        "Add %s fundamentals with runnable code",
        "Initial commit: Java %s exercises",
        "feat(java): Add %s practice problems",
        "Add working examples for %s concept",
        "Initial: %s patterns and demonstrations",
        "feat: core %s implementation in Java 21",
        "Add: %s exercises and solutions",
        "Java 21: demonstrate %s with real use-cases",
        "Practice: implement %s step by step"
    };

    private final AppConfig    config;
    private final GitHubClient github;
    private final StateManager stateManager;
    private final CodeGenerator codeGen;

    public RepoCreator(AppConfig config) {
        this.config       = config;
        this.github       = new GitHubClient(config.getGithubToken(), config.getGithubUsername());
        this.stateManager = new StateManager(config.getStateFile());
        this.codeGen      = new CodeGenerator();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void initializeSchedule() {
        stateManager.initializeSchedule(TopicRegistry.getAllTopics(), config.getReposPerDay());
    }

    /**
     * Creates ALL pending repos in one session.
     * This is the main command — run it once, walk away.
     * All commits are backdated automatically.
     */
    public void runAll() throws Exception {
        validateConnection();

        List<RepoState> pending = stateManager.getPendingRepos();
        if (pending.isEmpty()) {
            System.out.println("✅ Nothing to do — all repos already created!");
            stateManager.printProgress();
            return;
        }

        int total = pending.size();
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.printf( "│  🚀 Creating %3d repos with backdated commits       │%n", total);
        System.out.printf( "│  This will fill your contribution graph from         │%n");
        System.out.printf( "│  %s  →  today                           │%n",
            pending.get(0).getScheduledDate());
        System.out.println("└─────────────────────────────────────────────────────┘");
        System.out.println();

        int done = 0;
        for (RepoState state : pending) {
            System.out.printf("[%3d/%3d] 📁 %s  [commit date: %s]%n",
                ++done, total, state.getRepoName(), state.getScheduledDate());

            if (config.isDryRun()) {
                System.out.println("         [DRY-RUN] skipping API call");
                stateManager.markCreated(state, "https://github.com/dry-run/" + state.getRepoName());
                stateManager.markCodeUploaded(state);
                continue;
            }

            try {
                processRepo(state);
                System.out.printf("         ✅ %s%n%n", state.getRepoUrl());
            } catch (Exception e) {
                System.err.printf("         ❌ FAILED: %s%n%n", e.getMessage());
                // Continue with next repo instead of aborting
            }

            // Small pause between repos to avoid secondary rate limit
            if (done < total) {
                Thread.sleep(config.getDelayBetweenReposMs());
            }
        }

        System.out.println();
        stateManager.printProgress();
    }

    // ── Core ──────────────────────────────────────────────────────────────────

    /**
     * Full lifecycle for one repo:
     *   create → upload README (backdated) → upload code (backdated) → set tags
     */
    private void processRepo(RepoState state) throws Exception {
        Topic topic = findTopic(state.getTopicName());

        String description = topic != null
            ? topic.getDescription() + " (Part " + state.getRepoIndex() + ")"
            : "Java 21 practice: " + state.getRepoName();

        // 1. Create repo
        if (github.repoExists(state.getRepoName())) {
            System.out.println("         [SKIP-CREATE] repo already exists");
            state.setRepoUrl("https://github.com/" + config.getGithubUsername()
                + "/" + state.getRepoName());
            stateManager.markCreated(state, state.getRepoUrl());
        } else {
            String url = github.createRepository(
                state.getRepoName(), description, config.isMakePrivate());
            stateManager.markCreated(state, url);
            Thread.sleep(1500);  // let GitHub finish initializing the repo
        }

        // 2. Upload files with backdated timestamps
        if (!state.isCodeUploaded()) {
            Map<String, String> files = codeGen.generateCode(
                state.getTopicName(), state.getRepoIndex());

            int fileIdx = 0;
            for (Map.Entry<String, String> entry : files.entrySet()) {
                String commitMsg = buildCommitMsg(state.getTopicName(), entry.getKey(), fileIdx++);
                github.uploadFile(
                    state.getRepoName(),
                    entry.getKey(),
                    entry.getValue(),
                    commitMsg,
                    state.getCommitTime()   // ← BACKDATED TIMESTAMP
                );
                System.out.printf("         📄 %s%n", entry.getKey());
            }
            stateManager.markCodeUploaded(state);
        }

        // 3. Set GitHub topic tags
        if (topic != null && !topic.getTags().isEmpty()) {
            github.setTopics(state.getRepoName(), topic.getTags());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String buildCommitMsg(String topicName, String filePath, int idx) {
        String display = topicName.replace("-", " ");
        if (filePath.equals("README.md"))
            return "docs: add README for " + display;
        String template = COMMIT_TEMPLATES[idx % COMMIT_TEMPLATES.length];
        return String.format(template, display);
    }

    private Topic findTopic(String topicName) {
        return TopicRegistry.getAllTopics().stream()
            .filter(t -> t.getName().equals(topicName))
            .findFirst().orElse(null);
    }

    private void validateConnection() throws Exception {
        System.out.println("[Auth] Validating GitHub token...");
        String user = github.getAuthenticatedUser();
        System.out.println("[Auth] ✅ Logged in as: " + user);
        github.getRateLimit();
    }

    public void printSchedule()  { stateManager.printSchedule();  }
    public void printProgress()  { stateManager.printProgress();  }
}
