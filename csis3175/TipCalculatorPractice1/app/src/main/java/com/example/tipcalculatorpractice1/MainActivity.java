package com.example.tipcalculatorpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    private EditText edtBillAmount;
    private TextView tvTipPercentage, tvTipAmount, tvTotalAmount;
    private Button btnIncrease, btnDecrease, btnResult;

    private float tipPercentage = 0.15f;
    private final float TIC_TIP = 0.01f;

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

        edtBillAmount = findViewById(R.id.edtBillAmount);

        tvTipPercentage = findViewById(R.id.tvTipPercentage);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnResult = findViewById(R.id.btnResult);

        edtBillAmount.setOnEditorActionListener(this);
        edtBillAmount.setOnEditorActionListener(this);

        btnIncrease.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);

        // btnResult.setOnClickListener(view -> moveToSecondActivity(view));

        btnResult.setOnClickListener(this);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSecondActivity();
            }
        });
    }

    private void moveToSecondActivity() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        Bundle b = new Bundle();
        b.putFloat("bill_amount", getBillAmount());
        b.putFloat("tip_percentage", tipPercentage);
        intent.putExtras(b);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnIncrease) {
            tipPercentage += TIC_TIP;
        }
        else if (view.getId() == R.id.btnDecrease) {
            tipPercentage -= TIC_TIP;
            tipPercentage = Math.max(0.0f, tipPercentage);
        }
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        calculateAndDisplay();
        return false;
    }

    public void calculateAndDisplay() {
        Float billAmount = getBillAmount();

        float tipAmount = billAmount * tipPercentage;
        float totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberFormat percent = NumberFormat.getPercentInstance();

        tvTipAmount.setText(currency.format(tipAmount));
        tvTotalAmount.setText(currency.format(totalAmount));

        tvTipPercentage.setText(percent.format(tipPercentage));

    }

    private Float getBillAmount() {
        String billAmountString = edtBillAmount.getText().toString();
        float billAmount = 0;
        if (billAmountString.isEmpty()) {
            return billAmount;
        } else {
            try {
                billAmount = Float.parseFloat(billAmountString);
            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Please input a number", Toast.LENGTH_SHORT).show();
            }
        }

        return billAmount;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle b = new Bundle();
        b.putFloat("tip_percentage", tipPercentage);
        outState.putAll(b);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            tipPercentage = savedInstanceState.getFloat("tip_percentage", 0.0f);

        }
        calculateAndDisplay();
    }
}