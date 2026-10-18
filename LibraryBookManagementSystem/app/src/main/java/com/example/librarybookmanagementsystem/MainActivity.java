package com.example.librarybookmanagementsystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.librarybookmanagementsystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AppDatabase db;

    BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDb();

        setupButtons();

    }

    private void setupButtons() {
        binding.btnAdd.setOnClickListener(view -> addBook());
        binding.btnDelete.setOnClickListener(view -> deleteBook());
        binding.btnUpdate.setOnClickListener(view -> updateBook());
        binding.btnViewAll.setOnClickListener(view -> viewAllBook());
    }

    private void viewAllBook() {
        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
        startActivity(intent);
    }

    private void updateBook() {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(intent);
    }

    private void deleteBook() {
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivity(intent);
    }

    private void addBook() {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }



    private void setupDb() {
        // db
        db = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "book_db"
        ).allowMainThreadQueries().build();

        bookDao = db.bookDao();
    }
}