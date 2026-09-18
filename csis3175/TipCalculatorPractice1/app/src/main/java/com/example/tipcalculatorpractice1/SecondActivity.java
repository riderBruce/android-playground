package com.example.tipcalculatorpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class SecondActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvResult = findViewById(R.id.tvResult);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        try {
            float billAmount = b.getFloat("bill_amount", 0.0f);
            float tipPercentage = b.getFloat("tip_percentage", 0.0f);

            float totalAmount =  billAmount * (1 + tipPercentage);

            NumberFormat currency = NumberFormat.getCurrencyInstance();
            tvResult.setText(currency.format(totalAmount));

        } catch (NullPointerException ex){
            tvResult.setText("Input is not a number");
        }
    }

}