package com.gitautomator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Calculates backdated commit timestamps for each repository.
 *
 * Strategy:
 *   totalDays  = ceil(totalRepos / reposPerDay)
 *   startDate  = today - totalDays + 1
 *
 * Example (today = 2025-03-11, 82 repos, 2/day → 41 days):
 *   startDate  = 2025-01-30
 *   Day  1 → 2025-01-30  (repos 1 & 2)
 *   Day  2 → 2025-01-31  (repos 3 & 4)
 *   ...
 *   Day 41 → 2025-03-11  (repos 81 & 82)
 *
 * Each commit time is randomized to a plausible working-hours slot
 * (09:00 – 21:00) so it looks natural on the contribution graph.
 */
public class DateScheduler {

    private static final DateTimeFormatter DATE_FMT   = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter COMMIT_FMT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final Random RNG = new Random();

    private final LocalDate startDate;
    private final int       reposPerDay;

    /**
     * @param totalRepos  total number of repos to create
     * @param reposPerDay how many repos per day (normally 2)
     * @param today       anchor "today" date
     */
    public DateScheduler(int totalRepos, int reposPerDay, LocalDate today) {
        this.reposPerDay = reposPerDay;
        int totalDays    = (int) Math.ceil((double) totalRepos / reposPerDay);
        // Start date: go back (totalDays - 1) days from today
        this.startDate   = today.minusDays(totalDays - 1);

        System.out.printf("[Scheduler] totalRepos=%d | reposPerDay=%d | totalDays=%d%n",
            totalRepos, reposPerDay, totalDays);
        System.out.printf("[Scheduler] startDate=%s → endDate=%s%n",
            startDate, today);
    }

    /**
     * Returns the calendar date for logical day N (1-indexed).
     */
    public LocalDate dateForDay(int dayNumber) {
        return startDate.plusDays(dayNumber - 1);
    }

    /**
     * Returns an ISO-8601 timestamp for the commit, using the given date
     * and a randomized hour:minute in working-hours range.
     * e.g. "2025-01-30T14:23:07+00:00"
     */
    public String commitTimestamp(int dayNumber, int slotInDay) {
        LocalDate date = dateForDay(dayNumber);

        // Spread commits through the day: first commit in morning, second in afternoon
        int baseHour  = (slotInDay == 1) ? 9 : 14;   // 09:xx or 14:xx
        int hour      = baseHour + RNG.nextInt(3);    // ±3 hours jitter
        int minute    = RNG.nextInt(60);
        int second    = RNG.nextInt(60);

        ZonedDateTime zdt = ZonedDateTime.of(date,
            LocalTime.of(hour, minute, second), ZoneOffset.UTC);
        return COMMIT_FMT.format(zdt);
    }

    /**
     * ISO date string for display / state tracking.
     */
    public String isoDate(int dayNumber) {
        return DATE_FMT.format(dateForDay(dayNumber));
    }

    public LocalDate getStartDate() { return startDate; }
}
