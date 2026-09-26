package com.example.studentcourseregistration.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    private int studentId;
    @ColumnInfo(name = "student_name")
    private String studentName;
    @ColumnInfo(name = "student_email")
    private String email;

    @Ignore
    public Student(int studentId, String studentName, String email) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
    }

    public Student(String studentName, String email) {
        this.studentName = studentName;
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
