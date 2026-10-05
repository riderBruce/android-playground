package com.example.viewdemo;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomTouchListener implements View.OnTouchListener {
    GestureDetector gestureDetector;
    Context context; // need this for creating gestureDetector

    public CustomTouchListener(Context context) {
        this.context = context;
        // create gesture detector
        gestureDetector = new GestureDetector(context, new CustomOnGestureListener());
    }

    // need customGestureListener to create gesture detector object
    public class CustomOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            Log.d("GESTURE_DEMO", "Dectected on Double Tap");
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            Log.d("GESTURE_DEMO", "Dectected on Fling");

            float SWIPE_DIST_THRESHOLD = 10;
            float SWIPE_SPEED_THRESHOLD = 20;

            float xOff = e2.getX() - e1.getX();
            float yOff = e2.getY() - e1.getY();

            if (Math.abs(xOff) > Math.abs(yOff)
                && Math.abs(xOff) > SWIPE_DIST_THRESHOLD
                && Math.abs(velocityX) > SWIPE_SPEED_THRESHOLD) {
                // horizontal swipe
                if (xOff > 0) {
                    //right swipe
                    onRightSwipe();
                } else {
                    //left swipe
                    onLeftSwipe();
                }
            } else if (Math.abs(yOff) > Math.abs(xOff)
                    && Math.abs(yOff) > SWIPE_DIST_THRESHOLD
                    && Math.abs(velocityY) > SWIPE_SPEED_THRESHOLD) {
                // vertical swipe
                if (yOff > 0) {
                    onDownSwipe();
                } else {
                    onUpSwipe();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            Log.d("GESTURE_DEMO", "Dectected on LongPress");
            onLongClick();
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            Log.d("GESTURE_DEMO", "Dectected on Single tap confirmed");
            onSingleClick();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            Log.d("GESTURE_DEMO", "Entered on Down");
//            return super.onDown(e); // default is false
            return true;
        }
    }

    public void onUpSwipe() {
    }

    public void onDownSwipe() {
    }

    public void onLeftSwipe() {
    }

    public void onRightSwipe() {
    }

    public void onSingleClick() {
    }

    public void onLongClick() {
    }

    public void onDoubleClick() {
        // blank shell method -> need to override
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("GESTURE_DEMO", "Entered Custom On Touch");
//        return false;
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
