package com.example.converter;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final float USD_TO_EUR_RATE = 0.85f;
    private EditText dollarInput;
    private TextView resultView;

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

        dollarInput = findViewById(R.id.edt_dollar);
        resultView = findViewById(R.id.tv_result);

        Button convertBtn = findViewById(R.id.btn_convert);

//        convertBtn.setOnClickListener(this::convertCurrency);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency(view);
            }
        });
    }


    public void convertCurrency(View view) {
        String dollarText = dollarInput.getText().toString().trim();

        if (dollarText.isEmpty()) {
            resultView.setText("Input a number.");
            return;
        }

        float dollarAmount;
        try {
             dollarAmount = Float.parseFloat(dollarText);
        } catch (NumberFormatException ex){
            resultView.setText("Input a valid number");
            return;
        }

        float euroAmount = dollarAmount * USD_TO_EUR_RATE;
        resultView.setText(String.format(Locale.US, "%.2f", euroAmount));
//        resultView.setText(String.valueOf(euroAmount));
    }
}