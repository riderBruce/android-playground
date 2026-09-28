package com.example.viewdemo;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    final String TAG = "VIEW_DEMO";
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

        try {
            int number = Integer.parseInt("23.5");
        } catch (Exception e) {
            //Toast.makeText(this, "Error in Input", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "check the number");
            Log.d(TAG, Log.getStackTraceString(e));
            e.printStackTrace(System.err);
        }

        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView ivSample = findViewById(R.id.ivSample);
        Button btnShowTextOrImage = findViewById(R.id.btnShowTextOrImage);
        Button btnShowBoth = findViewById(R.id.btnShowBoth);

        Drawable img = ResourcesCompat.getDrawable(getResources(), R.drawable.border, getTheme());
        if (img != null) {
            img.setBounds(0,0,img.getIntrinsicWidth(),img.getIntrinsicHeight());
            tvTitle.setCompoundDrawables(img,null,img,null);
            tvTitle.setCompoundDrawablePadding(16);
        }

        btnShowTextOrImage.setOnClickListener(v->{
            if (btnShowTextOrImage.getText().equals(getString(R.string.text_show_text))){
                tvTitle.setVisibility(VISIBLE);
                ivSample.setVisibility(INVISIBLE);
                btnShowTextOrImage.setText(R.string.text_show_image);
            } else {
                tvTitle.setVisibility(GONE);
                ivSample.setVisibility(VISIBLE);
                btnShowTextOrImage.setText(R.string.text_show_text);
            }
        });

        btnShowBoth.setOnClickListener(v->{
            tvTitle.setVisibility(VISIBLE);
            ivSample.setVisibility(VISIBLE);
        });
    }
}