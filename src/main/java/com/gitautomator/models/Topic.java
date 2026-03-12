package com.gitautomator.models;

import java.util.List;

/**
 * Represents a programming topic with its associated repositories.
 */
public class Topic {
    private String name;          // e.g., "encapsulation"
    private String displayName;   // e.g., "Encapsulation"
    private String description;   // Topic description
    private List<String> tags;    // GitHub topic tags
    private int repoCount;        // Number of repos for this topic (default: 2)

    public Topic(String name, String displayName, String description, List<String> tags) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.tags = tags;
        this.repoCount = 2;
    }

    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public List<String> getTags() { return tags; }
    public int getRepoCount() { return repoCount; }
    public void setRepoCount(int repoCount) { this.repoCount = repoCount; }

    /**
     * Returns repo name for given index: e.g., "encapsulation-1"
     */
    public String getRepoName(int index) {
        return name + "-" + index;
    }
}
