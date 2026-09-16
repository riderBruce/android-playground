package com.example.movemate.utils;

public class WeeklySummary {
    private final int[] minutes;
    private final int[] goals;
    private final int activeMinutes;
    private final int totalGoal;

    public WeeklySummary(int[] minutes, int[] goals, int activeMinutes, int totalGoal) {
        this.minutes = minutes.clone();
        this.goals = goals.clone();
        this.activeMinutes = activeMinutes;
        this.totalGoal = totalGoal;
    }

    public int getMinutes(int index) { return minutes[index]; }
    public int getGoal(int index) { return goals[index]; }
    public int getActiveMinutes() { return activeMinutes; }
    public int getTotalGoal() { return totalGoal; }
}
