package com.example.eventhandling;

import static com.example.eventhandling.R.id.*;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout main;
    private String TAG = MainActivity.class.getSimpleName();

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

        main = findViewById(R.id.main);
        Button greenButton = findViewById(R.id.btn_green);
        Button yellowButton = findViewById(R.id.btn_yellow);

        greenButton.setOnClickListener(this::changeColor);
        yellowButton.setOnClickListener(this::changeColor);
    }

    public void changeColor(View view) {
        int id = view.getId();

        if (id == btn_green) {
            Log.e(TAG, "Color is changed to Green");
            main.setBackgroundColor(Color.GREEN);
        } else {
            Log.e(TAG, "Color is changed to Yellow");
            main.setBackgroundColor(Color.YELLOW);
        }
    }
}