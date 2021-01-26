package com.tabatatimer.activities;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tabatatimer.R;
import com.tabatatimer.misc.LoadingAnimationThread;

import java.util.ArrayList;



@SuppressWarnings("FieldCanBeLocal")
public class LoadingActivity extends AppCompatActivity {

    private final float AMPLITUDE = 25.0f;
    private final int   DURATION  = 200;
    private final int   DELAY     = 200;

    private AnimatorSet mJumpingAnimation;

    private LoadingAnimationThread mAnimationThread;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler(Looper.getMainLooper());
        mAnimationThread = new LoadingAnimationThread(
            handler,
            new Runnable() {

                @Override
                public void run() {

                    mJumpingAnimation.start();
                }
            }
        );
    }


    @Override
    public void onStart() {

        super.onStart();

        reinitializeVariables();
        mAnimationThread.start();
    }


    @Override
    public void onStop() {

        super.onStop();
        try {
            mAnimationThread.stop();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void reinitializeVariables() {

        TextView letter_L    = findViewById(R.id.textView_loading_L);
        TextView letter_O    = findViewById(R.id.textView_loading_O);
        TextView letter_A    = findViewById(R.id.textView_loading_A);
        TextView letter_D    = findViewById(R.id.textView_loading_D);
        TextView letter_I    = findViewById(R.id.textView_loading_I);
        TextView letter_N    = findViewById(R.id.textView_loading_N);
        TextView letter_G    = findViewById(R.id.textView_loading_G);
        TextView letter_dot1 = findViewById(R.id.textView_loading_dot0);
        TextView letter_dot2 = findViewById(R.id.textView_loading_dot1);
        TextView letter_dot3 = findViewById(R.id.textView_loading_dot2);

        buildJumpingAnimation(letter_L, letter_O, letter_A, letter_D, letter_I,
                              letter_N, letter_G, letter_dot1, letter_dot2, letter_dot3);
    }


    private void buildJumpingAnimation(View... views) {

        AnimatorSet         animatorSet = new AnimatorSet();
        ArrayList<Animator> sets        = new ArrayList<>();

        for (int i = 0, j = 1; i < views.length; i++, j++) {

            ObjectAnimator animator0 = ObjectAnimator.ofFloat(views[i],
                                                              "translationY",
                                                              -AMPLITUDE);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(views[i],
                                                              "translationY",
                                                              0.0f);

            animator0.setInterpolator(new AccelerateInterpolator());
            animator0.setDuration(DURATION);
            animator0.setStartDelay(DELAY * i);
            animator1.setInterpolator(new AnticipateOvershootInterpolator());
            animator1.setDuration(DURATION);
            animator1.setStartDelay(DELAY * j);

            sets.add(animator0);
            sets.add(animator1);
        }

        animatorSet.playTogether(sets);

        mJumpingAnimation = animatorSet;
    }

}
