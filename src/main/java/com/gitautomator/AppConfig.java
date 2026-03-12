package com.gitautomator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads configuration from config.properties or environment variables.
 * Environment variables always take priority over the properties file.
 */
public class AppConfig {

    private static final String CONFIG_FILE = "config.properties";

    private String  githubToken;
    private String  githubUsername;
    private String  committerName;   // for backdated commits (defaults to username)
    private String  committerEmail;  // for backdated commits
    private int     reposPerDay;
    private int     delayBetweenReposMs;
    private boolean dryRun;
    private String  stateFile;
    private boolean makePrivate;

    public AppConfig() { load(); }

    private void load() {
        Properties p = new Properties();
        try (InputStream is = new FileInputStream(CONFIG_FILE)) {
            p.load(is);
            System.out.println("[Config] Loaded " + CONFIG_FILE);
        } catch (IOException ignored) {
            System.out.println("[Config] No config.properties found — using env vars.");
        }

        githubToken    = resolve(p, "GITHUB_TOKEN",    "github.token");
        githubUsername = resolve(p, "GITHUB_USERNAME", "github.username");
        committerName  = resolveDefault(p, "COMMITTER_NAME",  "committer.name",  null);
        committerEmail = resolveDefault(p, "COMMITTER_EMAIL", "committer.email", null);
        reposPerDay    = parseInt(resolveDefault(p, "REPOS_PER_DAY", "repos.per.day", "2"));
        delayBetweenReposMs = parseInt(resolveDefault(p, "DELAY_MS",  "delay.ms",  "2000"));
        dryRun         = Boolean.parseBoolean(resolveDefault(p, "DRY_RUN",      "dry.run",      "false"));
        stateFile      = resolveDefault(p, "STATE_FILE",    "state.file",   "repo-state.json");
        makePrivate    = Boolean.parseBoolean(resolveDefault(p, "MAKE_PRIVATE", "make.private", "false"));
    }

    public void validate() {
        if (githubToken    == null || githubToken.isBlank())
            throw new IllegalStateException(
                "GITHUB_TOKEN not set! Set env var or add 'github.token' to config.properties");
        if (githubUsername == null || githubUsername.isBlank())
            throw new IllegalStateException(
                "GITHUB_USERNAME not set! Set env var or add 'github.username' to config.properties");
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String  getGithubToken()          { return githubToken; }
    public String  getGithubUsername()       { return githubUsername; }
    public String  getCommitterName()        { return committerName  != null ? committerName  : githubUsername; }
    public String  getCommitterEmail()       { return committerEmail != null ? committerEmail : githubUsername + "@users.noreply.github.com"; }
    public int     getReposPerDay()          { return reposPerDay; }
    public int     getDelayBetweenReposMs()  { return delayBetweenReposMs; }
    public boolean isDryRun()                { return dryRun; }
    public String  getStateFile()            { return stateFile; }
    public boolean isMakePrivate()           { return makePrivate; }

    @Override public String toString() {
        return String.format("[Config] user=%s | reposPerDay=%d | private=%s | dryRun=%s | delay=%dms",
            githubUsername, reposPerDay, makePrivate, dryRun, delayBetweenReposMs);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private String resolve(Properties p, String envKey, String propKey) {
        String v = System.getenv(envKey);
        if (v != null && !v.isBlank()) return v.trim();
        v = p.getProperty(propKey);
        return (v != null && !v.isBlank()) ? v.trim() : null;
    }
    private String resolveDefault(Properties p, String envKey, String propKey, String def) {
        String v = resolve(p, envKey, propKey); return v != null ? v : def;
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return 2; }
    }
}
