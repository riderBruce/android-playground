package com.example.customtouchlistenerpractice;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

public class CustomTouchListener implements View.OnTouchListener {
    GestureDetector gestureDetector;

    public CustomTouchListener(Context context){
        this.gestureDetector = new GestureDetector(context, new CustomOnGestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    // hook method
    public void onUpSwipe() {
    }

    public void onDownSwipe() {
    }

    public void onLeftSwipe() {
    }

    public void onRightSwipe() {
    }

    public void onLongClick() {
    }

    public void onDoubleClick() {
    }

    public void onSingleClick() {
    }

    // custom on gesture listener
    private class CustomOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(@NonNull MotionEvent e) {
//            return super.onDown(e);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            onSingleClick();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            onLongClick();
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            Log.d("GESTURE_DEMO", "Dectected on Fling");

            if (e1 == null) {
                return false;
            }

            final float SWIPE_DISTANCE_THRESHOLD = 10f;
            final float SWIPE_VELOCITY_THRESHOLD = 20f;

            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();

            float absDeltaX = Math.abs(deltaX);
            float absDeltaY = Math.abs(deltaY);

            boolean isHorizontalSwipe = absDeltaX > absDeltaY;

            if (isHorizontalSwipe) {
                boolean isValidSwipe = absDeltaX > SWIPE_DISTANCE_THRESHOLD &&
                                        Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD;
                if (!isValidSwipe) {
                    return false;
                }

                if (deltaX > 0) {
                    onRightSwipe();
                } else {
                    onLeftSwipe();
                }
            } else {
                boolean isValidSwipe = absDeltaY > SWIPE_DISTANCE_THRESHOLD &&
                                        Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD;
                if (!isValidSwipe) {
                    return false;
                }

                if (deltaY > 0) {
                    onDownSwipe();
                } else {
                    onUpSwipe();
                }
            }

            return true;
        }
    }

}
