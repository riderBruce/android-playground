package com.example.movemate.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movemate.models.LogRecord;

import java.util.ArrayList;
import java.util.List;

public class MoveLogDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movelog.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOG = "log_record";

    public static final String COL_ID = "_id";
    public static final String COL_TYPE = "type";
    public static final String COL_DATE = "date";
    public static final String COL_DURATION = "duration_minute";
    public static final String COL_DISTANCE = "distance_km";
    public static final String COL_INTENSITY = "intensity";
    public static final String COL_NOTE = "note";

    public MoveLogDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_LOG + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT NOT NULL, " +
                COL_DATE + " TEXT NOT NULL, " +
                COL_DURATION + " INTEGER NOT NULL, " +
                COL_DISTANCE + " REAL NOT NULL, " +
                COL_INTENSITY + " TEXT, " +
                COL_NOTE + " TEXT" +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Replace with proper migrations when the schema changes.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        onCreate(db);
    }

    public long addRecord(LogRecord record) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TYPE, record.getType());
        values.put(COL_DATE, record.getDate());
        values.put(COL_DURATION, record.getDurationMinute());
        values.put(COL_DISTANCE, record.getDistanceKm());
        values.put(COL_INTENSITY, record.getIntensity());
        values.put(COL_NOTE, record.getNote());

        return db.insert(TABLE_LOG, null, values);
    }

    public int updateRecord(LogRecord record) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TYPE, record.getType());
        values.put(COL_DATE, record.getDate());
        values.put(COL_DURATION, record.getDurationMinute());
        values.put(COL_DISTANCE, record.getDistanceKm());
        values.put(COL_INTENSITY, record.getIntensity());
        values.put(COL_NOTE, record.getNote());

        return db.update(
                TABLE_LOG,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(record.getId())}
        );
    }

    public int deleteRecord(int id) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_LOG,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public Cursor getAllRecords() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_LOG,
                null,
                null,
                null,
                null,
                null,
                COL_DATE + " DESC"
        );
    }

    public List<LogRecord> getAllRecordsAsList() {
        List<LogRecord> records = new ArrayList<>();

        try (Cursor cursor = this.getAllRecords()) {
            int idIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_ID);
            int typeIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_TYPE);
            int dateIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_DATE);
            int durationIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_DURATION);
            int distanceIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_DISTANCE);
            int intensityIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_INTENSITY);
            int noteIndex = cursor.getColumnIndexOrThrow(MoveLogDB.COL_NOTE);

            while (cursor.moveToNext()) {
                records.add(new LogRecord(
                        cursor.getInt(idIndex),
                        cursor.getString(typeIndex),
                        cursor.getString(dateIndex),
                        cursor.getInt(durationIndex),
                        cursor.getDouble(distanceIndex),
                        cursor.getString(intensityIndex),
                        cursor.getString(noteIndex)
                ));
            }
        }

        return records;
    }

    public LogRecord getRecordById(int id) {
        try (Cursor cursor = getReadableDatabase().query(
                TABLE_LOG,
                null,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        )) {
            if (!cursor.moveToFirst()) {
                return null;
            }

            return new LogRecord(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_DURATION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_DISTANCE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_INTENSITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE))
            );
        }
    }
}
