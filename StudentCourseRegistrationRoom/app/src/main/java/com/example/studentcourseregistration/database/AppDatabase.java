package com.example.studentcourseregistration.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.studentcourseregistration.dao.CourseDao;
import com.example.studentcourseregistration.dao.StudentDao;
import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.Student;

@Database(
        entities = {Student.class, Course.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
    public abstract CourseDao courseDao();
}
