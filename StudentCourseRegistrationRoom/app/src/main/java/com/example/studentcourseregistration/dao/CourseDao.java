package com.example.studentcourseregistration.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.CourseEnrollmentData;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    long addCourse(Course course);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE course_id=:id")
    Course getCourseById(int id);

    @Update
    int updateCourse(Course course);

    @Query("DELETE FROM courses WHERE course_id=:id")
    int deleteCourseById(int id);

    //         String query = "SELECT c." + COL_COURSE_ID + ", c." + COL_COURSE_NAME
    //                + ", s." + COL_STUDENT_ID + ", s." + COL_STUDENT_NAME
    //                + ", s." + COL_STUDENT_EMAIL
    //                + " FROM " + TABLE_NAME_COURSE + " c"
    //                + " INNER JOIN " + TABLE_NAME_STUDENT + " s"
    //                + " ON c." + COL_STUDENT_ID + " = s." + COL_STUDENT_ID
    //                + " ORDER BY c." + COL_COURSE_ID;

    @Query("SELECT c.course_id, c.course_name, c.student_id, s.student_name, s.student_email" +
            " FROM courses c" +
            " INNER JOIN students s" +
            " ON c.student_id = s.student_id")
    List<CourseEnrollmentData> getCourseEnrollmentData();
}
