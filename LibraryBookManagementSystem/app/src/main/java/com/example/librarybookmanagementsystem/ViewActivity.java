package com.example.librarybookmanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.librarybookmanagementsystem.databinding.ActivityViewBinding;

public class ViewActivity extends AppCompatActivity {

    ActivityViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // db
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "book_db").allowMainThreadQueries().build();
        BookDao bookDao = db.bookDao();

        // recyclerView
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.rvBooks.setLayoutManager(manager);

        BookAdapter adapter = new BookAdapter(this);
        binding.rvBooks.setAdapter(adapter);

        adapter.setBooks(bookDao.getAllBooks());
    }
}