package com.example.sqlitedatabasedemo;
/*
How it works...

We'll start by explaining the DictionaryDatabase class as that's the heart of an SQLite
database. The first item to note is the constructor:

DictionaryDatabase(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
Notice DATABASE_VERSION? Only when you make changes to your database schema do
you need to increment this value.

Next is onCreate(), where the database is actually created. This is only called the first
time the database is created, not each time the class is created. It's also worth noting the _id
field. Android does not require tables to have a primary field, but some classes, such as
the SimpleCursorAdapter, may require an _id.

We're required to implement the onUpgrade() callback, but as this is a new database,
there's nothing to do. This method will only be called when the database version is
incremented.

The saveRecord() method handles calling addRecord() or updateRecord(), as
appropriate. Since we are going to modify the database, both methods use
getWritableDatabase() to get an updatable database reference. A writable database
requires more resources so if you don't need to make changes, get a read-only database
instead.

The last method to note is getWordList(), which returns all the words in the database
using a cursor object. We use this cursor to populate the ListView, which brings us to
ActivityMain.java. The onCreate() method does the standard initialization we've seen
before and also creates an instance of the database with the following line of code:
mDB = new DictionaryDatabase(this);

The onCreate() method is also where we set up the events to show the word definition
(with a Toast) when an item is pressed and to delete the word on a long press. Probably the
most complicated code is in updateWordList().

This isn't the first time we've used an adapter, but this is the first cursor adapter, so we'll
explain. We use the SimpleCursorAdapter to create a mapping between our field in the
cursor and the ListView item. We use the layout.simple_list_item_1 layout, which
only includes a single text field with the ID android.R.id.text1. In a real application,
we'd probably create a custom layout and include the definition in the ListView item, but
we wanted to demonstrate a method to read the definition from the database.

We call updateWordList() in three places: during onCreate() to create the initial list,
then again after we add/update an item, and lastly when deleting an item.

There's more...
Although this is a fully functioning example of SQLite, it is still just the basics. There are
many books dedicated to SQLite for Android and they are worth checking out.

Upgrading a database
As we mentioned previously, when we increment the database version, the onUpgrade()
method will be called. What you do here is dependent on the changes made to the
database. If you changed an existing table, ideally you'll want to migrate the user data to
the new format by querying the existing data and inserting it into the new format. Keep in
mind that there is no guarantee the user will upgrade in consecutive order, so they could
jump from version 1 to version 4, for example.

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final String TABLE_DICTIONARY = "dictionary";

    private static final String FIELD_WORD = "word";
    private static final String FIELD_DEFINITION = "definition";
    private static final int DATABASE_VERSION = 1;

    DictionaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_DICTIONARY +
                "(_id integer PRIMARY KEY," +
                FIELD_WORD + " TEXT, " +
                FIELD_DEFINITION + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Handle database upgrade as needed
    }

    public void saveRecord(String word, String definition) {
        long id = findWordID(word);
        if (id>0) {
            updateRecord(id, word,definition);
        } else {
            addRecord(word,definition);
        }
    }

    public long addRecord(String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);
        return db.insert(TABLE_DICTIONARY, null, values);
    }

    public int updateRecord(long id, String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);
        return db.update(TABLE_DICTIONARY, values, "_id = ?", new String[]{String.valueOf(id)});
    }
    public int deleteRecord(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DICTIONARY, "_id = ?", new String[]{String.valueOf(id)});
    }

    public long findWordID(String word) {
        long returnVal = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT _id FROM " + TABLE_DICTIONARY + " WHERE " + FIELD_WORD + " = ?",
                new String[]{word});
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            returnVal = cursor.getInt(0);
        }
        return returnVal;
    }

    public String getDefinition(long id) {
        String returnVal = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT definition FROM " + TABLE_DICTIONARY + " WHERE _id = ?",
                new String[]{String.valueOf(id)});
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            returnVal = cursor.getString(0);
        }
        return returnVal;
    }

    public Cursor getWordList() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id, " + FIELD_WORD +
                " FROM " + TABLE_DICTIONARY + " ORDER BY " + FIELD_WORD +
                " ASC";
        return db.rawQuery(query, null);
    }
}
