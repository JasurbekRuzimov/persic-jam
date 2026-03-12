package com.gitautomator.models;

/**
 * Tracks state of one repository in the schedule.
 */
public class RepoState {
    private String topicName;
    private String repoName;
    private int    repoIndex;
    private int    dayNumber;        // logical day: 1, 2, 3 ...
    private String scheduledDate;    // ISO date: "2025-01-01" (backdated)
    private String commitTime;       // ISO-8601 with time+Z for GitHub API
    private boolean created;
    private boolean codeUploaded;
    private String repoUrl;

    public RepoState() {}

    public RepoState(String topicName, String repoName, int repoIndex,
                     int dayNumber, String scheduledDate, String commitTime) {
        this.topicName     = topicName;
        this.repoName      = repoName;
        this.repoIndex     = repoIndex;
        this.dayNumber     = dayNumber;
        this.scheduledDate = scheduledDate;
        this.commitTime    = commitTime;
    }

    // ── getters / setters ──────────────────────────────────────────────
    public String  getTopicName()     { return topicName; }
    public String  getRepoName()      { return repoName; }
    public int     getRepoIndex()     { return repoIndex; }
    public int     getDayNumber()     { return dayNumber; }
    public String  getScheduledDate() { return scheduledDate; }
    public String  getCommitTime()    { return commitTime; }
    public boolean isCreated()        { return created; }
    public boolean isCodeUploaded()   { return codeUploaded; }
    public String  getRepoUrl()       { return repoUrl; }

    public void setCreated(boolean v)      { this.created = v; }
    public void setCodeUploaded(boolean v) { this.codeUploaded = v; }
    public void setRepoUrl(String v)       { this.repoUrl = v; }

    @Override public String toString() {
        return String.format("Day%3d | %-40s | %s | created=%-5s | code=%-5s",
            dayNumber, repoName, scheduledDate, created, codeUploaded);
    }
}
