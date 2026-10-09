package com.example.intentapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.intentapp.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
//            String name = bundle.getString("name");
//            double gpa = bundle.getDouble("gpa");
//            binding.tvOutput.setText("Name: "+ name + ", GPA: "+gpa);
            Student student = getIntent().getSerializableExtra("student_data", Student.class);
            binding.tvOutput.setText(student.toString());
        }
    }
}