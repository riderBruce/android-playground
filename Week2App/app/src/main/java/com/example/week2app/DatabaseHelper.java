package com.example.week2app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "Inventory.db";
    final static int DATABASE_VERSION = 1;
    final static String TABLE_NAME = "Product_table";
    final static String T1COL1 = "ProdID";
    final static String T1COL2 = "ProdName";
    final static String T1COL3 = "Quantity";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "(" + T1COL1 + " INTEGER PRIMARY KEY, " +
                T1COL2 + " TEXT, " +
                T1COL3 + " INTEGER)";
        db.execSQL(query);
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // update data
    public boolean updateData(int id, String n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL2, n);
        String[] selectionArg = {Integer.toString(id)};

        int u = db.update(TABLE_NAME,
                values,
                "ProdID= ?",
                selectionArg);
        if (u>0)
            return true;
        else
            return false;
    }

    // delete data
    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArg = {Integer.toString(id)};
        int d = db.delete(TABLE_NAME, "ProdID = ?", selectionArg);
        if (d>0)
            return true;
        else
            return false;
    }

    //method to retrive the data
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
//        String query  = "SELECT * FROM " + TABLE_NAME + " WHERE ProdID = ?";
//        String[] selectionArg = {"1"};
//        return db.rawQuery(query, selectionArg);
    }

    //method to save the data
    public boolean saveData(String pn, int q) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL2, pn);
        contentValues.put(T1COL3, q);
        long i = db.insert(TABLE_NAME, null, contentValues);
        if (i>0)
            return true;
        else
            return false;
    }
}
