package com.example.movemate.utils;

import android.content.SharedPreferences;

import com.example.movemate.models.LogRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class WeeklySummaryCalculator {
    private static final String[] ACTIVITIES = {"Running", "Walking", "Biking", "Swimming", "StandUp"};
    private static final int DEFAULT_WEEKLY_GOAL = 210;
    private final SharedPreferences preferences;

    public WeeklySummaryCalculator(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public WeeklySummary calculate(List<LogRecord> records) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);
        int[] minutes = new int[ACTIVITIES.length];

        for (LogRecord record : records) {
            LocalDate date = LocalDate.parse(record.getDate());
            if (date.isBefore(startDate) || date.isAfter(today)) {
                continue;
            }

            int activityIndex = findActivityIndex(record.getType());
            if (activityIndex != -1) {
                minutes[activityIndex] += record.getDurationMinute();
            }
        }

        int[] goals = new int[ACTIVITIES.length];
        int activeMinutes = 0;
        int totalGoal = 0;

        for (int i = 0; i < ACTIVITIES.length; i++) {
            goals[i] = getWeeklyGoal(ACTIVITIES[i]);
            activeMinutes += minutes[i];
            totalGoal += goals[i];
        }

        if (totalGoal == 0) {
            totalGoal = 1;
        }

        return new WeeklySummary(minutes, goals, activeMinutes, totalGoal);
    }

    private int findActivityIndex(String activity) {
        String normalizedActivity = normalizeActivity(activity);

        for (int i = 0; i < ACTIVITIES.length; i++) {
            if (normalizeActivity(ACTIVITIES[i]).equals(normalizedActivity)) {
                return i;
            }
        }

        return -1;
    }

    private int getWeeklyGoal(String activity) {
        String key = "weekly_goal_" + activity.toLowerCase(Locale.ROOT);
        String value = preferences.getString(key, String.valueOf(DEFAULT_WEEKLY_GOAL));

        try {
            int goal = Integer.parseInt(value);
            return Math.max(goal, 0);
        } catch (NumberFormatException | NullPointerException ignored) {
            return DEFAULT_WEEKLY_GOAL;
        }
    }

    private String normalizeActivity(String activity) {
        return activity == null ? "" : activity.replaceAll("[-\\s]", "").toLowerCase(Locale.ROOT);
    }
}
