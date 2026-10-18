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

import com.example.librarybookmanagementsystem.databinding.ActivityDeleteBinding;

public class DeleteActivity extends AppCompatActivity {

    ActivityDeleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "book_db").allowMainThreadQueries().build();
        BookDao bookDao = db.bookDao();

        binding.btnDelete.setOnClickListener(view -> {
            if (binding.edtTitle.getText() != null) {
                String title = binding.edtTitle.getText().toString();
                int count = bookDao.deleteBookByTitle(title);
                if (count > 0) {
                    Toast.makeText(this, "Deleted: " + count, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not found ", Toast.LENGTH_SHORT).show();
                }

            } else if (binding.edtAuthor.getText() != null) {
                String author = binding.edtAuthor.getText().toString();
                int count = bookDao.deleteBookByAuthor(author);
                if (count > 0) {
                    Toast.makeText(this, "Deleted: "+ count, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Input at least one field", Toast.LENGTH_SHORT).show();
            }

        });

        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}