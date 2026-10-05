package com.example.practicestudentroombindingfragment;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();
    public abstract CourseDao courseDao();
}
