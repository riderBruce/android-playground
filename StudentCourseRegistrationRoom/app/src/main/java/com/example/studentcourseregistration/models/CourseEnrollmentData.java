package com.example.studentcourseregistration.models;

import androidx.room.ColumnInfo;

public class CourseEnrollmentData {
    @ColumnInfo(name = "course_id")
    private final int courseId;
    @ColumnInfo(name = "course_name")
    private final String courseName;
    @ColumnInfo(name = "student_id")
    private final int studentId;
    @ColumnInfo(name = "student_name")
    private final String studentName;
    @ColumnInfo(name = "student_email")
    private final String studentEmail;

    public CourseEnrollmentData(int courseId, String courseName, int studentId,
                                String studentName, String studentEmail) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getStudentEmail() { return studentEmail; }
}
