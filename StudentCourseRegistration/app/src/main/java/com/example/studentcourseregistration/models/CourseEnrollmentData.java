package com.example.studentcourseregistration.models;

public class CourseEnrollmentData {
    private final int courseId;
    private final String courseName;
    private final int studentId;
    private final String studentName;
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
