package com.example.customtouchlistenerpractice;

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
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.customtouchlistenerpractice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String TAG = "VIEW_DEMO";
    private boolean showingText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        demonstrateLogCat();

        setupCompoundDrawable();

        demonstrateCoordinatedUiStateChange();

        setupButtons();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupButtons() {
        // custom on touch listener
        binding.tvTitle.setOnTouchListener(new CustomTouchListener(MainActivity.this){
            @Override
            public void onSingleClick() {
                if (binding.tvTitle.getCurrentTextColor() != getColor(R.color.purple)) {
                    binding.tvTitle.setTextColor(getColor(R.color.purple));
                } else {
                    binding.tvTitle.setTextColor(getColor(R.color.white));
                }
                super.onSingleClick();
            }

            @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            @Override
            public void onDoubleClick() {
                float currentSP = TypedValue.deriveDimension(
                        COMPLEX_UNIT_SP, binding.tvTitle.getTextSize(), getResources().getDisplayMetrics()
                );

                if (currentSP != 16) {
                    binding.tvTitle.setTextSize(16);
                } else {
                    binding.tvTitle.setTextSize(14);
                }
                super.onDoubleClick();
            }

            @Override
            public void onLongClick() {
                if (binding.tvTitle.getPaint().isStrikeThruText()) {
                    binding.tvTitle.setPaintFlags(
                            binding.tvTitle.getPaintFlags() &
                                    ~Paint.STRIKE_THRU_TEXT_FLAG
                    );
                } else {
                    binding.tvTitle.setPaintFlags(
                            binding.tvTitle.getPaintFlags() |
                                    Paint.STRIKE_THRU_TEXT_FLAG
                    );
                }
                super.onLongClick();
            }

            @Override
            public void onUpSwipe() {
                Log.d(TAG, "Swipe Up on tvTitle");
                super.onUpSwipe();
            }

            @Override
            public void onDownSwipe() {
                Log.d(TAG, "Swipe Down on tvTitle");
                super.onDownSwipe();
            }

            @Override
            public void onLeftSwipe() {
                Log.d(TAG, "Swipe Left on tvTitle");
                super.onLeftSwipe();
            }

            @Override
            public void onRightSwipe() {
                Log.d(TAG, "Swipe Right on tvTitle");
                super.onRightSwipe();
            }
        });

        binding.ivSample.setOnTouchListener(new CustomTouchListener(MainActivity.this){
            @Override
            public void onDoubleClick() {
                Log.d("GESTURE_DEMO", "Double Click on ivSample");
                if (binding.ivSample.getScaleType() == ImageView.ScaleType.FIT_XY) {
                    binding.ivSample.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    binding.ivSample.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                super.onDoubleClick();
            }
        });
    }

    private void demonstrateCoordinatedUiStateChange() {
        // single user interaction triggers coordinated UI state changes
        // and provides immediate visual feedback
        binding.btnToggleTextOrImage.setOnClickListener(v->{
            showingText = !showingText;
            if (showingText) {
                binding.tvTitle.setVisibility(VISIBLE);
                binding.ivSample.setVisibility(INVISIBLE);
                binding.btnToggleTextOrImage.setText(R.string.show_only_image);
            } else {
                binding.tvTitle.setVisibility(GONE);
                binding.ivSample.setVisibility(VISIBLE);
                binding.btnToggleTextOrImage.setText(R.string.show_only_text);
            }
        });

        binding.btnShowBoth.setOnClickListener(v->{
            binding.tvTitle.setVisibility(VISIBLE);
            binding.ivSample.setVisibility(VISIBLE);
        });
    }

    private void setupCompoundDrawable() {
        // Compound Drawable
        Drawable img = ResourcesCompat.getDrawable(getResources(), R.drawable.border, getTheme());
        if (img != null) {
            img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
            binding.tvTitle.setCompoundDrawables(img, null, img, null);
            binding.tvTitle.setCompoundDrawablePadding(16);
        }
    }

    private void demonstrateLogCat() {
        // Debugging Technique
        try {
            int number = Integer.parseInt("2.23");
        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
    }
}