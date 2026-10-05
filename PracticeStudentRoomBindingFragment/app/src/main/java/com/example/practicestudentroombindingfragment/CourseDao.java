package com.example.practicestudentroombindingfragment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE course_id = :id")
    Course getCourseById(int id);

    @Query("DELETE FROM courses WHERE course_id = :id")
    int deleteCourseById(int id);

    @Insert
    long addCourse(Course course);

    @Update
    int updateCourse(Course course);
}
