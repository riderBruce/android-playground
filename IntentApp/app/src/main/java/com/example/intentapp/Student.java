package com.example.intentapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name is " + name + ". GPA is " + gpa;
    }
}
