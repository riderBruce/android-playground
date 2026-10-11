package com.example.fregmentappdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fregmentappdemo.databinding.FragmentStudentListBinding;

import java.util.List;


public class StudentListFragment extends Fragment implements StudentAdapter.OnStudentClickListener {
    FragmentStudentListBinding binding;

    public interface OnStudentListFragmentListener {
        void onShowStudentDetails(Student student);
        List<Student> onRequestAllStudents();
    }

    OnStudentListFragmentListener listener;

    RecyclerView rvStudents;
    RecyclerView.LayoutManager manager;
    StudentAdapter adapter;

    public StudentListFragment(OnStudentListFragmentListener listener) {
        // Required empty public constructor
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);

        rvStudents = binding.rvStudent;
        manager = new LinearLayoutManager(getContext());
        rvStudents.setLayoutManager(manager);

        adapter = new StudentAdapter(this);
        rvStudents.setAdapter(adapter);

        adapter.setStudents(listener.onRequestAllStudents());

//        binding.btnStudent1.setOnClickListener(v -> openStudentDetails(101, "David J."));
//        binding.btnStudent2.setOnClickListener(v -> openStudentDetails(102, "Sara K."));


        return binding.getRoot();
    }

    public void openStudentDetails(int id, String name) {

        StudentDetailsFragment fragment = new StudentDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("student_id", id);
        bundle.putString("student_name", name);
        fragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onShowStudentDetails(Student student) {
//        if (listener != null)
//            listener.onShowStudentDetails(student);

        StudentDetailsFragment fragment = new StudentDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        fragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}