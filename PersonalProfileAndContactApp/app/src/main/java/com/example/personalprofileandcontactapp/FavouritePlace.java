package com.example.personalprofileandcontactapp;

import java.io.Serializable;

public class FavouritePlace implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String placeName;

    public FavouritePlace(int id, String placeName) {
        this.id = id;
        this.placeName = placeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
