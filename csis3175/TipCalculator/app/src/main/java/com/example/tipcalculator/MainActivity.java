package com.example.tipcalculator;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {
    private EditText edtBillAmountInput;
    private TextView tvTipPercentValue;
    private TextView tvTipAmountValue;
    private TextView tvTotalAmountValue;
    private Button btnTipIncrease;
    private Button btnTipDecrease;

    private String billAmountString = "";
    private float tipPercent = .15f;
    private static final float TIP_PERCENT_STEP = 0.01f;

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

        edtBillAmountInput = findViewById(R.id.edtBillAmountInput);

        tvTipPercentValue = findViewById(R.id.tvTipPercentValue);
        tvTipAmountValue = findViewById(R.id.tvTipAmountValue);
        tvTotalAmountValue = findViewById(R.id.tvTotalAnountValue);

        btnTipIncrease = findViewById(R.id.btnTipIncrease);
        btnTipDecrease = findViewById(R.id.btnTipDecrease);

        edtBillAmountInput.setOnEditorActionListener(this);

        btnTipIncrease.setOnClickListener(this);
        btnTipDecrease.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnTipIncrease) {
            tipPercent += TIP_PERCENT_STEP;
        } else if (view.getId() == R.id.btnTipDecrease) {
            tipPercent = Math.max(0.0f, tipPercent - TIP_PERCENT_STEP);
        }
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        calculateAndDisplay();
        return false;
    }

    private void calculateAndDisplay() {
        billAmountString = edtBillAmountInput.getText().toString();
        float billAmount;
        if (billAmountString.isEmpty()) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAmountString);
        }

        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tvTipAmountValue.setText(currency.format(tipAmount));
        tvTotalAmountValue.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        tvTipPercentValue.setText(percent.format(tipPercent));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) return;

        billAmountString = savedInstanceState.getString("bill_amount","");
        tipPercent = savedInstanceState.getFloat("tip_percent",0.15f);

        calculateAndDisplay();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bill_amount", billAmountString);
        outState.putFloat("tip_percent", tipPercent);
    }
}