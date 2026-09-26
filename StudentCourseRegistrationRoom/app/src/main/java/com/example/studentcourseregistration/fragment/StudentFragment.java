package com.example.studentcourseregistration.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcourseregistration.R;
import com.example.studentcourseregistration.adapters.StudentAdapter;
import com.example.studentcourseregistration.models.Student;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class StudentFragment extends Fragment implements StudentAdapter.OnStudentClickListener {


    public interface StudentFragmentListener {
        void onRequestAddStudent(Student student);
        List<Student> onRequestAllStudents();
        void onRequestDeleteStudent(int id);
        void onRequestUpdateStudent(Student student);
    }

    private StudentFragmentListener listener;

    EditText edtStudentName, edtStudentEmail;
    Button btnAddStudent;
    BottomSheetDialog dialog;

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

        adapter = new StudentAdapter(this);
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
            listener.onRequestAddStudent(student);
            this.refreshStudents();
        }
    }
    @Override
    public void onDeleteStudentClicked(int id) {
        if (listener != null) {
            listener.onRequestDeleteStudent(id);
        }
    }

    @Override
    public void onUpdateStudentClicked(Student student) {
        showUpdateStudentSheet(student);
    }

    private void showUpdateStudentSheet(Student student) {
        dialog = new BottomSheetDialog(requireContext());

        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_update_student, null);

        TextInputEditText edtUpdateStudentName = sheetView.findViewById(R.id.edtUpdateStudentName);
        TextInputEditText edtUpdateStudentEmail = sheetView.findViewById(R.id.edtUpdateStudentEmail);
        Button btnUpdateStudent = sheetView.findViewById(R.id.btnUpdateStudent);

        edtUpdateStudentName.setText(student.getStudentName());
        edtUpdateStudentEmail.setText(student.getEmail());

        btnUpdateStudent.setOnClickListener(view -> {
            String name = String.valueOf(edtUpdateStudentName.getText());
            String email = String.valueOf(edtUpdateStudentEmail.getText());

            Student updatedStudent = new Student(student.getStudentId(), name, email);
            if (listener != null) {
                listener.onRequestUpdateStudent(updatedStudent);
            }
            dialog.dismiss();
        });

        dialog.setContentView(sheetView);
        dialog.show();
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
