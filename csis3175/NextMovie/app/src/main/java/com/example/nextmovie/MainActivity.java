package com.example.nextmovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    private EditText edtKeyword;
    private SeekBar sbRating;
    private Slider sliderRating;
    private Button btnSearch;

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

        edtKeyword = findViewById(R.id.edtKeyword);
        sbRating = findViewById(R.id.sbRating);
        sliderRating = findViewById(R.id.sliderRating);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> onSearchClicked());
    }

    private void onSearchClicked() {

        String keyword = edtKeyword.getText().toString();
        int rating = sbRating.getProgress();
        int sliderValue = (int) sliderRating.getValue();

        // search movies based on keyword and rating logic

        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("rating", rating);
        startActivity(intent);

    }
}