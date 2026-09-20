package com.example.studentcourseregistration.models;

public class Course {
    private int id;
    private String courseName;
    private int studentId;

    public Course(int id, String courseName, int studentId) {
        this.id = id;
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public Course(String courseName, int studentId) {
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getStudentId() {
        return studentId;
    }
}
