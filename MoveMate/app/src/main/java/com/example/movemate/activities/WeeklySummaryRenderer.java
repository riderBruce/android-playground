package com.example.movemate.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movemate.R;
import com.example.movemate.utils.WeeklySummary;

import java.util.Locale;

public class WeeklySummaryRenderer {
    private static final String[] NAMES = {"Running", "Walking", "Biking", "Swimming", "Stand-up"};
    private static final int[] IDS = {R.id.tvRunningWeeklyGoal, R.id.tvWalkingWeeklyGoal,
            R.id.tvBikingWeeklyGoal, R.id.tvSwimmingWeeklyGoal, R.id.tvStandUpWeeklyGoal};

    public void render(View view, WeeklySummary summary) {
        int goal = summary.getTotalGoal();
        ((TextView) view.findViewById(R.id.tvWeeklyActiveMinutes)).setText(String.format(Locale.getDefault(),
                "Total active minutes: %d / %d", summary.getActiveMinutes(), goal));
        for (int i = 0; i < NAMES.length; i++) {
            int minutes = summary.getMinutes(i), activityGoal = summary.getGoal(i);
            int percentage = activityGoal > 0 ? Math.round(minutes * 100f / activityGoal) : 0;
            ((TextView) view.findViewById(IDS[i])).setText(String.format(Locale.getDefault(),
                    "%-8s %4d / %4d (%3d%%)", NAMES[i] + ":", minutes, activityGoal, percentage));
        }
        ProgressBar progress = view.findViewById(R.id.progressWeeklySummary);
        progress.setMax(goal);
        progress.setProgress(Math.min(summary.getActiveMinutes(), goal));
    }
}
