package com.example.nextmovie;

public class Movie {
    private String title;
    private String posterUrl;
    private double rating;
    private String description;

    public Movie(String title, String posterUrl, double rating, String description) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}
