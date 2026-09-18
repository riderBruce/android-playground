package com.example.sendingdatafromonefragmenttoanother;

import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements MyListener {

    FragmentManager manager;
    int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = getSupportFragmentManager();

        addFragmentA();
        addFragmentB();
    }

    private void addFragmentA() {
        FragmentA fragmentA = new FragmentA();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerFragmentA, fragmentA, "fragA");
        transaction.commit();
    }

    private void addFragmentB() {
        FragmentB fragmentB = new FragmentB();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerFragmentB, fragmentB, "fragB");
        transaction.commit();
    }

    @Override
    public void addTwoNumbers(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;

        Toast.makeText(this, "Numbers Received in Activity: " + num1 + ", "+ num2, Toast.LENGTH_LONG).show();
    }

    public void sendDataToFragmentB(View view) {
        FragmentB fragmentB = (FragmentB) manager.findFragmentByTag("fragB");

        if (fragmentB != null){
            fragmentB.addTwoNumbers(num1, num2);
        }
    }

}