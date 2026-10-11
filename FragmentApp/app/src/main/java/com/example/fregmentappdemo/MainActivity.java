package com.example.fregmentappdemo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.fregmentappdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements StudentListFragment.OnStudentListFragmentListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnShow.setOnClickListener(view ->{
            Fragment fragment = new StudentListFragment(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        });


    }

    @Override
    public void onShowStudentDetails(Student student) {

    }

    @Override
    public List<Student> onRequestAllStudents() {
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1, "Alex"),
                new Student(2, "Akito"),
                new Student(3, "Alice"),
                new Student(4, "Jane")
        ));
        return students;
    }
}