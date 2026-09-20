package com.example.studentcourseregistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcourseregistration.adapters.StudentAdapter;
import com.example.studentcourseregistration.models.Student;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class StudentFragment extends Fragment {
    public interface StudentFragmentListener extends StudentAdapter.OnStudentClickListener {
        void onAddStudentRequested(Student student);
        List<Student> onRequestAllStudents();
    }

    private StudentFragmentListener listener;

    EditText edtStudentName, edtStudentEmail;
    Button btnAddStudent;

    RecyclerView rvStudents;
    RecyclerView.LayoutManager manager;
    StudentAdapter adapter;

    @Override
    public @Nullable View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        edtStudentName = view.findViewById(R.id.edtStudentName);
        edtStudentEmail = view.findViewById(R.id.edtStudentEmail);
        btnAddStudent = view.findViewById(R.id.btnAddStudent);

        btnAddStudent.setOnClickListener(this::addStudent);

        rvStudents = view.findViewById(R.id.rvStudents);
        manager = new LinearLayoutManager(getContext());
        rvStudents.setLayoutManager(manager);

        adapter = new StudentAdapter(listener);
        rvStudents.setAdapter(adapter);

        adapter.setStudents(listener.onRequestAllStudents());

        return view;
    }

    public void refreshStudents() {
        if (adapter != null && listener != null) {
            adapter.setStudents(listener.onRequestAllStudents());
            edtStudentName.setText("");
            edtStudentEmail.setText("");
        }
    }

    private void addStudent(View view) {
        Student student = new Student(edtStudentName.getText().toString(), edtStudentEmail.getText().toString());

        if (listener != null) {
            listener.onAddStudentRequested(student);
            this.refreshStudents();

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof StudentFragmentListener) {
            listener = (StudentFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement StudentFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
