package com.example.viewdemo;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    final String TAG = "VIEW_DEMO";
    boolean showingText = false;

    @SuppressLint("ClickableViewAccessibility")
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
            showingText = !showingText;
            if (showingText) {
                tvTitle.setVisibility(VISIBLE);
                ivSample.setVisibility(INVISIBLE);
                btnShowTextOrImage.setText(R.string.text_show_image);
            } else {
                tvTitle.setVisibility(GONE);
                ivSample.setVisibility(VISIBLE);
                btnShowTextOrImage.setText(R.string.text_show_text);
            }
        });

//        btnShowTextOrImage.setOnClickListener(v->{
//            if (btnShowTextOrImage.getText().equals(getString(R.string.text_show_text))){
//                tvTitle.setVisibility(VISIBLE);
//                ivSample.setVisibility(INVISIBLE);
//                btnShowTextOrImage.setText(R.string.text_show_image);
//            } else {
//                tvTitle.setVisibility(GONE);
//                ivSample.setVisibility(VISIBLE);
//                btnShowTextOrImage.setText(R.string.text_show_text);
//            }
//        });

        btnShowBoth.setOnClickListener(v->{
            tvTitle.setVisibility(VISIBLE);
            ivSample.setVisibility(VISIBLE);
        });

        tvTitle.setOnTouchListener(new CustomTouchListener(MainActivity.this){
            @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            @Override
            public void onDoubleClick() {
                Log.d("GESTURE_DEMO", "Double Clicked on tvTitle");
                float currentSP = TypedValue.deriveDimension(COMPLEX_UNIT_SP, tvTitle.getTextSize(), getResources().getDisplayMetrics());
                if (currentSP != 16) {
                    tvTitle.setTextSize(16);
                } else {
                    tvTitle.setTextSize(14);
                }
                super.onDoubleClick();
            }

            @Override
            public void onLongClick() {
                Log.d("GESTURE_DEMO", "Long Clicked on tvTitle");
                if (tvTitle.getPaint().isStrikeThruText()) {
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                super.onLongClick();
            }

            @Override
            public void onSingleClick() {
                Log.d("GESTURE_DEMO", "Single Clicked on tvTitle");
                if (tvTitle.getCurrentTextColor() != getColor(R.color.purple)){
                    tvTitle.setTextColor(getColor(R.color.purple));
                } else {
                    tvTitle.setTextColor(getColor(R.color.white));
                }
                super.onSingleClick();
            }

            @Override
            public void onUpSwipe() {
                Log.d("GESTURE_DEMO", "Swipe Up on tvTitle");
                super.onUpSwipe();
            }

            @Override
            public void onDownSwipe() {
                Log.d("GESTURE_DEMO", "Swipe Down on tvTitle");
                super.onDownSwipe();
            }

            @Override
            public void onLeftSwipe() {
                Log.d("GESTURE_DEMO", "Swipe Left on tvTitle");
                super.onLeftSwipe();
            }

            @Override
            public void onRightSwipe() {
                Log.d("GESTURE_DEMO", "Swipe Right on tvTitle");
                super.onRightSwipe();
            }
        });

        ivSample.setOnTouchListener(new CustomTouchListener(MainActivity.this){
            @Override
            public void onDoubleClick() {
                Log.d("GESTURE_DEMO", "Double Click on ivSample");
                if (ivSample.getScaleType() == ImageView.ScaleType.FIT_XY) {
                    ivSample.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    ivSample.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                super.onDoubleClick();
            }
        });
    }
}