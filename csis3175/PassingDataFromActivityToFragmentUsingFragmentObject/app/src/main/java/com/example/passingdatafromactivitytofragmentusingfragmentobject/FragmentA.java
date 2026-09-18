package com.example.passingdatafromactivitytofragmentusingfragmentobject;

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

    private Button btnAdd;
    private TextView tvResult;
    private int firstNumber = 0;
    private int secondNumber = 0;

    private MainActivity.Employee employee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        btnAdd = view.findViewById(R.id.btnAdd);
        tvResult = view.findViewById(R.id.tvResult);

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

        tvResult.setText("Result: " + result + "\nName: " + employee.name + "\nID: "+ employee.employeeNumber);
    }

    public void setData(int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public void setEmployee(MainActivity.Employee employee) {
        this.employee = employee;
    }
}
