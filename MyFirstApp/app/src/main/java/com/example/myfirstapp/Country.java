package com.example.myfirstapp;

import androidx.annotation.NonNull;

public enum Country {
    KOREA("Korea"),
    CANADA("Canada"),
    USA("United State"),
    JAPAN("Japan");

    private String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    @NonNull
    @Override
    public String toString() {
        return displayName;
    }
}
