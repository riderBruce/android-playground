package com.example.movemate.models;

public class DailyWeather {
    private final String date;
    private final int weatherCode;
    private final double minimumTemperature;
    private final double maximumTemperature;

    public DailyWeather(
            String date,
            int weatherCode,
            double minimumTemperature,
            double maximumTemperature
    ) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
    }

    public String getDate() {
        return date;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }
}
