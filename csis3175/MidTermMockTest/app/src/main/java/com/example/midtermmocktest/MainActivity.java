package com.example.midtermmocktest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner spnLoanTerm;
    Switch swcInsurance;
    RadioButton rbNewCar, rbUsedCar;
    Button btnCalculate;
    TextView tvResult;

    String result;

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

        spnLoanTerm = findViewById(R.id.spnLoanTerm);
        swcInsurance = findViewById(R.id.swcInsurance);
        rbNewCar = findViewById(R.id.rbNewCar);
        rbUsedCar = findViewById(R.id.rbUsedCar);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        String[] loanTerms = {"24 months", "36 months", "48 months", "60 months"};

        // spinner control
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                loanTerms
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoanTerm.setAdapter(adapter);
        // ----------
        // https://developer.android.com/develop/ui/views/components/spinner?hl=en&_gl=1*1q6e22p*_up*MQ..*_gs*MQ..&gclid=CjwKCAjwgO7RBhBKEiwAZNP85iDuqvybTh2GyLd6hTUfl-3VZWc90LzAYE4FHFGWNBJWPGBGcwQNtBoCOykQAvD_BwE&gclsrc=aw.ds&gbraid=0AAAAAC-IOZltiEV4_HeHCielbqX1QeJax#java


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySelection();
            }});
    }

    private void displaySelection() {
        String selectedTerm = spnLoanTerm.getSelectedItem().toString();

        int months = 0;

        if (selectedTerm.equals("24 months")) {
            months = 24;
        } else if (selectedTerm.equals("36 months")) {
            months = 36;
        } else if (selectedTerm.equals("48 months")) {
            months = 48;
        } else if (selectedTerm.equals("60 months")) {
            months = 60;
        }

        double interestRate = 6.5;

        if (rbUsedCar.isChecked()) {
            interestRate += 1.0;
        }

        double insuranceCost = 0;

        if (swcInsurance.isChecked()) {
            insuranceCost = 120;
        }

        String result = "Months: " + months +
                "\nInterest Rate: " + interestRate + "%" +
                "\nInsurance: $" + insuranceCost;

        this.result = result;
        moveToSecond();
    }

    private void moveToSecond() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        Bundle b = new Bundle();
        b.putString("result", result);
        intent.putExtras(b);

        startActivity(intent);
    }
}