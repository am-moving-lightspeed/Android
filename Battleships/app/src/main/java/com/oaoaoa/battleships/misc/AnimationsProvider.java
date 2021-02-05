package com.oaoaoa.battleships.misc;


import android.graphics.drawable.AnimationDrawable;
import android.view.View;



public class AnimationsProvider {

    public static void startBackgroundGradientAnimation(View view) {

        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

}
