package com.example.movemate.models;

public class LogRecord {
    private int id;
    private String type;
    private String date;
    private int durationMinute;
    private double distanceKm;
    private String intensity;
    private String note;

    public LogRecord(String type, String date, int durationMinute, double distanceKm, String intensity, String note) {
        this.type = type;
        this.date = date;
        this.durationMinute = durationMinute;
        this.distanceKm = distanceKm;
        this.intensity = intensity;
        this.note = note;
    }
    public LogRecord(
            int id,
            String type,
            String date,
            int durationMinute,
            double distanceKm,
            String intensity,
            String note
    ) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.durationMinute = durationMinute;
        this.distanceKm = distanceKm;
        this.intensity = intensity;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getIntensity() {
        return intensity;
    }

    public String getNote() {
        return note;
    }
}
