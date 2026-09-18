package com.example.sendingdatafromonefragmenttoanother;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {
    TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        tvResult = view.findViewById(R.id.tvResult);

        return view;
    }

    public void addTwoNumbers(int num1, int num2) {
        int result = num1 + num2;
        tvResult.setText("Result: " + result);

    }
}
