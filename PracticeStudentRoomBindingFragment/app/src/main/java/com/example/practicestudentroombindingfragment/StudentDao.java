package com.example.practicestudentroombindingfragment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    Long addStudents(Student student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudent();

    @Query("SELECT * FROM students WHERE student_id=:id")
    Student getStudentById(int id);

    @Query("DELETE FROM students WHERE student_id=:id")
    int deleteStudentById(int id);

    @Update
    int updateStudent(Student student);

}
