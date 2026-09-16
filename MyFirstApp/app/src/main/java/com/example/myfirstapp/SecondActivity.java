package com.example.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvName = findViewById(R.id.tvName);
        TextView tvGender = findViewById(R.id.tvGender);
        TextView tvCountry = findViewById(R.id.tvCountry);

        SharedPreferences sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "not found");
        String gender = sharedPreferences.getString("gender", "not found");
        String country = sharedPreferences.getString("country", "not found");

        tvName.setText(name);
        tvGender.setText(gender);
        tvCountry.setText(country);
    }
}