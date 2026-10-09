package com.example.fregmentappdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fregmentappdemo.databinding.FragmentStudentDetailsBinding;

public class StudentDetailsFragment extends Fragment {
    FragmentStudentDetailsBinding binding;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentDetailsBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int id = bundle.getInt("student_id", 999);
            String studentName = bundle.getString("student_name", "n/a");

            binding.tvStudentId.setText(String.valueOf(id));
            binding.tvStudentName.setText(studentName);
        }

        binding.btnBack.setOnClickListener(v->{
            getParentFragmentManager()
                    .popBackStack();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}