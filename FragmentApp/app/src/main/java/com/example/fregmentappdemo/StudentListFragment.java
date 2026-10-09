package com.example.fregmentappdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fregmentappdemo.databinding.FragmentStudentListBinding;


public class StudentListFragment extends Fragment {
    FragmentStudentListBinding binding;

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
        binding.btnStudent1.setOnClickListener(v -> openStudentDetails(101, "David J."));
        binding.btnStudent2.setOnClickListener(v -> openStudentDetails(102, "Sara K."));
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
}