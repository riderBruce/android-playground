package com.example.librarybookmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.librarybookmanagementsystem.databinding.ActivityUpdateBinding;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // db
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "book_db").allowMainThreadQueries().build();
        BookDao bookDao = db.bookDao();

        // spinner
        List<Book> books = bookDao.getAllBooks();
        List<String> titles = new ArrayList<>();
        for (var b: books) {
            titles.add(b.getTitle());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnTitle.setAdapter(spinnerAdapter);

        // btn
        binding.btnUpdate.setOnClickListener(view -> {
            if (binding.edtAuther.getText() != null) {
                int position = binding.spnTitle.getSelectedItemPosition();
                Book book = books.get(position);
                String author = binding.edtAuther.getText().toString();
                book.setAuthor(author);

                int count = bookDao.updateBook(book);
                if (count > 0) {
                    Toast.makeText(this, "Updated: " + count, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}