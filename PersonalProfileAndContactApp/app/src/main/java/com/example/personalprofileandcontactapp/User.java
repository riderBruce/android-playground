package com.example.personalprofileandcontactapp;

public class User {
    private String name;
    private Gender gender;
    private Country country;

    public User(String name, Gender gender, Country country) {
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name+","+gender+","+country;
    }
}
