package com.example.studentcourseregistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcourseregistration.adapters.CourseAdapter;
import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.CourseEnrollmentData;
import com.example.studentcourseregistration.models.Student;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {
    public interface CourseFragmentListener {
        boolean onAddCourseRequested(Course course);
        List<CourseEnrollmentData> onRequestAllCourses();
        List<Student> onRequestAllStudents();
    }

    private CourseFragmentListener listener;
    private EditText edtCourseName;
    private Spinner spnStudentId;
    private Button btnAdd;
    private RecyclerView rvCourses;
    private CourseAdapter adapter;
    private List<Student> students;

    @Override
    public @Nullable View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        edtCourseName = view.findViewById(R.id.edtCourseName);
        spnStudentId = view.findViewById(R.id.spnStudentId);
        btnAdd = view.findViewById(R.id.btnAdd);
        rvCourses = view.findViewById(R.id.rvCourses);

        students = listener.onRequestAllStudents();
        List<String> studentLabels = new ArrayList<>();
        for (Student student : students) {
            studentLabels.add(student.getId() + " - " + student.getStudentName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                studentLabels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudentId.setAdapter(spinnerAdapter);

        rvCourses.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new CourseAdapter((CourseAdapter.OnCourseClickListener) requireActivity());
        rvCourses.setAdapter(adapter);

        adapter.setCourses(listener.onRequestAllCourses());

        btnAdd.setOnClickListener(this::addCourse);

        return view;
    }

    public void refreshCourses() {
        if (adapter != null && listener != null) {
            adapter.setCourses(listener.onRequestAllCourses());
            edtCourseName.setText("");
        }
    }

    private void addCourse(View view) {
        String courseName = edtCourseName.getText().toString().trim();

        int position = spnStudentId.getSelectedItemPosition();

        Course course = new Course(courseName, students.get(position).getId());
        if (listener != null && listener.onAddCourseRequested(course)) {
            refreshCourses();

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CourseFragmentListener) {
            listener = (CourseFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement CourseFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
