package com.example.intentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.intentapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSend.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            String name = binding.edtName.getText().toString();
            double gpa = Double.parseDouble(binding.edtGpa.getText().toString());
//            intent.putExtra("name", name);
//            intent.putExtra("gpa", gpa);
            Student student = new Student(name, gpa);
            intent.putExtra("student_data", student);

            startActivity(intent);
        });
        binding.btnOpenWeb.setOnClickListener(view -> openWebsite());
        binding.btnCall.setOnClickListener(view -> placeACall());
        binding.btnEmail.setOnClickListener(view -> sendAnEmail());
        binding.btnMap.setOnClickListener(view -> openMap());

    }
    public void openWebsite(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
        startActivity(intent);
    }

    public void placeACall(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:6041234567"));
        startActivity(intent);
    }

    public void sendAnEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String str = "mailto:someone@example.com";
        intent.setData(Uri.parse(str));
        startActivity(intent);
    }

    public void openMap(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:49.2827,-123.1207"));
        startActivity(intent);
    }

}