package com.gitautomator;

import com.gitautomator.models.RepoState;
import com.gitautomator.models.Topic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Persists the full repo schedule to JSON.
 * On first run: builds the schedule with backdated dates.
 * On subsequent runs: loads existing state and resumes from where it left off.
 */
public class StateManager {

    private final String stateFilePath;
    private final Gson   gson;
    private List<RepoState> states = new ArrayList<>();

    public StateManager(String stateFilePath) {
        this.stateFilePath = stateFilePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // ── Schedule Initialization ───────────────────────────────────────────────

    /**
     * Builds the full schedule.
     *  - If state file exists → resume (reload) from it.
     *  - Otherwise           → create new schedule with backdated dates.
     */
    public void initializeSchedule(List<Topic> topics, int reposPerDay) {
        File stateFile = new File(stateFilePath);
        if (stateFile.exists()) {
            System.out.println("[State] Resuming from existing state: " + stateFilePath);
            loadState();
            printProgress();
            return;
        }

        // Count total repos
        int totalRepos = topics.stream().mapToInt(Topic::getRepoCount).sum();

        // Build date scheduler anchored to TODAY
        DateScheduler scheduler = new DateScheduler(totalRepos, reposPerDay, LocalDate.now());

        System.out.printf("[State] Building schedule: %d repos × 2 = %d repos over %d days%n",
            topics.size(), totalRepos, (int) Math.ceil((double) totalRepos / reposPerDay));

        int dayNumber     = 1;
        int slotInDay     = 1;          // tracks which slot within a day (1st or 2nd repo)
        int reposThisDay  = 0;

        for (Topic topic : topics) {
            for (int idx = 1; idx <= topic.getRepoCount(); idx++) {
                String scheduledDate = scheduler.isoDate(dayNumber);
                String commitTime    = scheduler.commitTimestamp(dayNumber, slotInDay);

                states.add(new RepoState(
                    topic.getName(),
                    topic.getRepoName(idx),
                    idx,
                    dayNumber,
                    scheduledDate,
                    commitTime
                ));

                reposThisDay++;
                slotInDay++;

                if (reposThisDay >= reposPerDay) {
                    dayNumber++;
                    reposThisDay = 0;
                    slotInDay    = 1;
                }
            }
        }

        System.out.printf("[State] Schedule spans %s → %s%n",
            scheduler.isoDate(1),
            scheduler.isoDate(dayNumber > 1 ? dayNumber - 1 : dayNumber));

        saveState();
    }

    // ── Queries ───────────────────────────────────────────────────────────────

    /** All repos that haven't been created yet. */
    public List<RepoState> getPendingRepos() {
        return states.stream().filter(s -> !s.isCreated()).collect(Collectors.toList());
    }

    /** Repos for a specific logical day. */
    public List<RepoState> getReposForDay(int dayNumber) {
        return states.stream()
            .filter(s -> s.getDayNumber() == dayNumber)
            .collect(Collectors.toList());
    }

    public int getTotalDays() {
        return states.stream().mapToInt(RepoState::getDayNumber).max().orElse(0);
    }

    // ── Mutations ─────────────────────────────────────────────────────────────

    public void markCreated(RepoState state, String repoUrl) {
        state.setCreated(true);
        state.setRepoUrl(repoUrl);
        saveState();
    }

    public void markCodeUploaded(RepoState state) {
        state.setCodeUploaded(true);
        saveState();
    }

    // ── Display ───────────────────────────────────────────────────────────────

    public void printProgress() {
        long total    = states.size();
        long done     = states.stream().filter(RepoState::isCreated).count();
        long withCode = states.stream().filter(RepoState::isCodeUploaded).count();

        System.out.println("┌────────────────────────────────────────────┐");
        System.out.printf( "│ 📊 PROGRESS                                │%n");
        System.out.printf( "│  Total repos   : %-6d                     │%n", total);
        System.out.printf( "│  Created       : %-6d                     │%n", done);
        System.out.printf( "│  With code     : %-6d                     │%n", withCode);
        System.out.printf( "│  Remaining     : %-6d                     │%n", total - done);
        System.out.printf( "│  Total days    : %-6d                     │%n", getTotalDays());
        System.out.println("└────────────────────────────────────────────┘");
    }

    public void printSchedule() {
        System.out.println("\n═══════════════════ FULL SCHEDULE ═══════════════════");
        int lastDay = -1;
        for (RepoState s : states) {
            if (s.getDayNumber() != lastDay) {
                lastDay = s.getDayNumber();
                System.out.printf("%n  📅 Day %3d  [%s]%n", lastDay, s.getScheduledDate());
            }
            String status = s.isCreated() ? "✅" : "⏳";
            System.out.printf("     %s %-45s commit → %s%n",
                status, s.getRepoName(), s.getCommitTime());
        }
        System.out.println("\n═════════════════════════════════════════════════════");
    }

    // ── Persistence ───────────────────────────────────────────────────────────

    private void saveState() {
        try (Writer w = new FileWriter(stateFilePath)) {
            gson.toJson(states, w);
        } catch (IOException e) {
            System.err.println("[State] ERROR saving: " + e.getMessage());
        }
    }

    private void loadState() {
        try (Reader r = new FileReader(stateFilePath)) {
            Type type = new TypeToken<List<RepoState>>(){}.getType();
            states = gson.fromJson(r, type);
            if (states == null) states = new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[State] ERROR loading: " + e.getMessage());
            states = new ArrayList<>();
        }
    }

    public List<RepoState> getAllStates() { return states; }
}
