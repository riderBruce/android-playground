package com.example.studentcourseregistration.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "courses",
        foreignKeys = {
                @ForeignKey(
                        entity = Student.class,
                        parentColumns = "student_id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                ),
        },
        indices = {
                @Index(value = "student_id")
        }
)
public class Course {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "course_name")
    private String courseName;
    @ColumnInfo(name = "student_id")
    private int studentId;

    @Ignore
    public Course(int courseId, String courseName, int studentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public Course(String courseName, int studentId) {
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
