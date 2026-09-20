package com.example.studentcourseregistration.models;

public class Student {
    private int id;
    private String studentName;
    private String email;

    public Student(int id, String studentName, String email) {
        this.id = id;
        this.studentName = studentName;
        this.email = email;
    }

    public Student(String studentName, String email) {
        this.studentName = studentName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }
}
