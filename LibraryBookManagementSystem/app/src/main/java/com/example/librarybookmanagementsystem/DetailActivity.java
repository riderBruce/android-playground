package com.example.librarybookmanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarybookmanagementsystem.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Book book = getIntent().getSerializableExtra("book_details", Book.class);
            binding.tvBookId.setText(String.valueOf(book.getId()));
            binding.tvBookTitle.setText(book.getTitle());
            binding.tvBookAuthor.setText(book.getAuthor());
            binding.tvBookIsdn.setText(book.getIsdn());
        }
    }
}