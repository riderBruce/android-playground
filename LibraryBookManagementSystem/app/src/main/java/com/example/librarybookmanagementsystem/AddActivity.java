package com.example.librarybookmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.librarybookmanagementsystem.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = Room.databaseBuilder(this,AppDatabase.class,"book_db").allowMainThreadQueries().build();
        BookDao bookDao = db.bookDao();

        binding.btnAdd.setOnClickListener(view -> {
            String title = binding.edtTitle.getText().toString();
            String author = binding.edtAuthor.getText().toString();
            String isdn = binding.edtIsdn.getText().toString();

            Book book = new Book(title, author, isdn);
            bookDao.addBook(book);

            Toast.makeText(this, "Book Added: " + title, Toast.LENGTH_SHORT).show();
        });

        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}