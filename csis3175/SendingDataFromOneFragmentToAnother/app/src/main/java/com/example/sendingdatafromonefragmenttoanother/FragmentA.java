package com.example.sendingdatafromonefragmenttoanother;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    EditText edtFirstNumber, edtSecondNumber;
    Button btnSend;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        edtFirstNumber = view.findViewById(R.id.edtFirstNumber);
        edtSecondNumber = view.findViewById(R.id.edtSecondNumber);

        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        return view;
    }

    private void sendData() {

        int firstNumber = Integer.valueOf(edtFirstNumber.getText().toString());
        int secondNumber = Integer.valueOf(edtSecondNumber.getText().toString());

        MyListener myListener = (MyListener) getActivity();
        myListener.addTwoNumbers(firstNumber, secondNumber);

    }
}
