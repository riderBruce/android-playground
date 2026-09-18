package com.example.sqlitedatabasedemo;
/*
Creating and using an SQLite database
In this app, we're going to demonstrate working with an SQLite database.
If you are already familiar with SQL databases from other platforms,
then much of what you know will apply.

If you are new to SQLite, take a look at the following reference links

SQLite homepage:
https://www.sqlite.org/
SQLite database Android reference:
http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html

as this app assumes a basic understanding of database concepts,
including schemas,tables, cursors, and raw SQL.

To get you up and running with an SQLite database quickly, our example implements the
basic CRUD operations. Generally, when creating a database in Android, you create a class
that extends SQLiteOpenHelper, which is where your database functionality is
implemented.

Here is a list of the CRUD (create, read, update, and delete) functions:

Create: insert()
Read: query() and rawQuery()
Update: update()
Delete: delete()

To demonstrate a fully working database, we will create a simple Dictionary database
where we'll store words and their definitions.

We'll demonstrate the CRUD operations by adding new words (with their definitions)
and updating existing word definitions.
We'll show words in a ListView using a cursor. Pressing a word in the ListView will read the
definition from the database and display it in a Toast message.
A long press will delete the word.

Please see my comments explaining the DictionaryDatabase class in DictionaryDatabase file.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextWord;
    EditText mEditTextDefinition;
    DictionaryDatabase mDB;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mDB = new DictionaryDatabase(this);
        mEditTextWord = findViewById(R.id.editTextWord);
        mEditTextDefinition = findViewById(R.id.editTextDefinition);
        Button buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });

        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mDB.getDefinition(id), Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Records deleted = " + mDB.deleteRecord(id), Toast.LENGTH_SHORT).show();
                refreshWordList();
                return true;
            }
        });
        refreshWordList();
    }


    private void saveRecord() {
        mDB.saveRecord(mEditTextWord.getText().toString(), mEditTextDefinition.getText().toString());
        mEditTextWord.setText("");
        mEditTextDefinition.setText("");
        refreshWordList();
    }

    private void refreshWordList() {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getWordList(),
                new String[]{"word"},
                new int[]{android.R.id.text1},
                0);
        mListView.setAdapter(simpleCursorAdapter);
    }
}