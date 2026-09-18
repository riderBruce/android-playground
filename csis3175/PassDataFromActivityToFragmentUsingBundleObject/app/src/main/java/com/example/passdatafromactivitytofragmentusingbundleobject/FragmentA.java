package com.example.passdatafromactivitytofragmentusingbundleobject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    TextView tvResult;
    Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        // get the incoming Bundle Object
        Bundle bundle = getArguments();
        
        final int firstNumber = bundle.getInt("first_number", 0);
        final int secondNumber = bundle.getInt("second_number", 0);
        
        tvResult = view.findViewById(R.id.tvResult);
        btnAdd = view.findViewById(R.id.btnAdd);
        
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTwoNumbers(firstNumber, secondNumber);
            }
        });
        return view;
    }

    private void addTwoNumbers(int firstNumber, int secondNumber) {
        int result = firstNumber + secondNumber;
        tvResult.setText("Result: "+ result);
    }
}
