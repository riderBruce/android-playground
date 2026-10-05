package com.example.practicestudentroombindingfragment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.practicestudentroombindingfragment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(this, AppDatabase.class, "school.db").allowMainThreadQueries().build();

        StudentDao studentDao = db.studentDao();
        CourseDao courseDao = db.courseDao();

        binding.btnRegister.setOnClickListener(v->{
            String name = binding.edtStudentName.getText().toString();
            studentDao.addStudents(new Student(name));
        });

        binding.btnShow.setOnClickListener(v->{
            Fragment fragment = new StudentFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        });
    }
}