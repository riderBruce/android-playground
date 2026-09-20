package com.example.studentcourseregistration;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentcourseregistration.adapters.CourseAdapter;
import com.example.studentcourseregistration.adapters.SchoolDatabaseHelper;
import com.example.studentcourseregistration.adapters.StudentAdapter;
import com.example.studentcourseregistration.models.Student;
import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.CourseEnrollmentData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements StudentFragment.StudentFragmentListener,
        CourseFragment.CourseFragmentListener
{

    private FragmentManager manager;
    private BottomNavigationView bottomNavigationView;
    private SchoolDatabaseHelper schoolDatabaseHelper;

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

        schoolDatabaseHelper = new SchoolDatabaseHelper(this);

        manager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            showFragment(new StudentFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navStudent) {
                showFragment(new StudentFragment());
                return true;
            } else if (itemId == R.id.navCourse) {
                showFragment(new CourseFragment());
                return true;
            }
            return false;
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);

        transaction.commit();
    }

    @Override
    public void onAddStudentRequested(Student student) {
        if (schoolDatabaseHelper.addStudent(student)) {
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Student add failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public List<Student> onRequestAllStudents() {
        return schoolDatabaseHelper.getAllStudentsAsList();
    }

    @Override
    public void onRequestDeleteStudentClicked(int id) {
        if (schoolDatabaseHelper.deleteStudent(id)) {
            refreshStudentList();
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Student deleted failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestUpdateStudentClicked(Student student) {
        if (schoolDatabaseHelper.updateStudent(student)) {
            refreshStudentList();
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Student update failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onAddCourseRequested(Course course) {
        boolean added = schoolDatabaseHelper.addCourse(course);
        Toast.makeText(this, added ? "Course added successfully" : "Course add failed",
                Toast.LENGTH_SHORT).show();
        return added;
    }

    @Override
    public List<CourseEnrollmentData> onRequestAllCourses() {
        return schoolDatabaseHelper.getAllEnrollmentAsList();
    }

    @Override
    public void onRequestDeleteCourseClicked(int id) {
        if (schoolDatabaseHelper.deleteCourse(id)) {
            refreshCourseList();
            Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course delete failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestUpdateCourseClicked(Course course) {
        if (schoolDatabaseHelper.upadateCourse(course)) {
            refreshCourseList();
            Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCourseList() {
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof CourseFragment) {
            ((CourseFragment) fragment).refreshCourses();
        }
    }

    private void refreshStudentList() {
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof StudentFragment) {
            ((StudentFragment) fragment).refreshStudents();
        }
    }
}
