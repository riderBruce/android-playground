package com.example.passingdatafromactivitytofragmentusingfragmentobject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private EditText edtFirstNumber, edtSecondNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtFirstNumber = findViewById(R.id.edtFirstNumber);
        edtSecondNumber = findViewById(R.id.edtSecondNumber);

        manager = getSupportFragmentManager();
    }

    public void sendDataToFregment(View view) {
        int firstNumber = Integer.valueOf(edtFirstNumber.getText().toString());
        int secondNumber = Integer.valueOf(edtSecondNumber.getText().toString());

        FragmentA fragmentA = new FragmentA();
        fragmentA.setData(firstNumber, secondNumber); // passing primitive data type

        // just to show that we can pass the non-primitive data as well, Let's pass an Employee object to the fregment.
        fragmentA.setEmployee(new Employee(123123123, "Alex Kim"));

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.containerFragmentA, fragmentA, "fregA");
        transaction.commit();
    }

    public class Employee {
        int employeeNumber;
        String name;
        public Employee(int employeeNumber, String name){
            this.employeeNumber = employeeNumber;
            this.name = name;
        }
    }

}