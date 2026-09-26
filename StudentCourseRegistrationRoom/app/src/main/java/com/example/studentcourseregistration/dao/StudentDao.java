package com.example.studentcourseregistration.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentcourseregistration.models.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    long addStudent(Student student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudents();

    @Query("SELECT * FROM students WHERE student_id = :id")
    Student getStudentById(int id);

    @Update
    int updateStudent(Student student);

    @Query("DELETE FROM students WHERE student_id = :id")
    int deleteStudentById(int id);

}
