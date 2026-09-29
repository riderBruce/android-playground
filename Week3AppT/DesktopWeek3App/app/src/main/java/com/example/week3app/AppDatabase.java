package com.example.week3app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Order.class},version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();
}
