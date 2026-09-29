package com.example.viewbinding;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.viewbinding.databinding.ActivityMainBinding;
import com.example.viewbinding.databinding.ActivitySecondBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActivitySecondBinding binding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAdd.setOnClickListener(view->{
            String name = binding.inputName.getText().toString();
            Toast.makeText(this, "name: " + name, Toast.LENGTH_SHORT)
                    .show();
        });
        binding2 = ActivitySecondBinding.inflate(getLayoutInflater());
        binding2.btnSecond.setOnClickListener(v->{});

    }
}