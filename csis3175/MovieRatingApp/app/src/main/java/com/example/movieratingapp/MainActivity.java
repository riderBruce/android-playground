package com.example.movieratingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {
    private EditText edtTitle;
    private Slider sldRating;
    private TextView tvRating;
    private Button btnSubmit;

    private String title;
    private int rating;

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

        edtTitle = findViewById(R.id.edtTitle);
        sldRating = findViewById(R.id.sldRating);
        tvRating = findViewById(R.id.tvRating);
        btnSubmit = findViewById(R.id.btnSubmit);

        sldRating.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                int value = (int) slider.getValue();
                tvRating.setText(String.valueOf(value));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        loadData();
    }

    private void onSubmit() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);

        title = edtTitle.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(MainActivity.this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }
        rating = (int) sldRating.getValue();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("rating", rating);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", edtTitle.getText().toString());
        outState.putInt("rating", (int) sldRating.getValue());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        title = savedInstanceState.getString("title", "default");
        rating = savedInstanceState.getInt("rating", 5);
        edtTitle.setText(title);
        sldRating.setValue((float) rating);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_data", Context.MODE_PRIVATE);
        title = sharedPreferences.getString("title", "shared_data");
        rating = sharedPreferences.getInt("rating", 5);
        edtTitle.setText(title);
        sldRating.setValue((float) rating);
    }
}