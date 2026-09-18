package com.example.ferrytickets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    double costToTripToCatalina = 34;
    double costToTripToLongBeach = 40;
    int numberOfTickets;
    double totalCost;
    String tripTo;

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

        EditText edtTicketCount = findViewById(R.id.edtTicketCount);
        Spinner spnDestination = findViewById(R.id.spnDestination);
        TextView tvResult = findViewById(R.id.tvResult);

        Button btnComputeCost = findViewById(R.id.btnComputeCost);

        btnComputeCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    numberOfTickets = Integer.parseInt(edtTicketCount.getText().toString());
                } catch (NumberFormatException ex) {
                    tvResult.setText("Please enter the number of tickets you need!");
                    return;
                }

                NumberFormat currency = NumberFormat.getCurrencyInstance();

                tripTo = spnDestination.getSelectedItem().toString();

                if (spnDestination.getSelectedItemPosition() == 0){
                    totalCost = costToTripToCatalina * numberOfTickets;
                } else {
                    totalCost = costToTripToLongBeach * numberOfTickets;
                }

                tvResult.setText("One way trip " + tripTo + " for " + numberOfTickets + " passengers: " + currency.format(totalCost));

            }
        });
    }
}