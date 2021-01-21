package com.tabatatimer.handlers;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;



public class CrudButtonsHandler implements ICrudButtonsHandler {

    private final int ANIMATION_DELAY    = 100;
    private final int ANIMATION_DURATION = 300;

    private boolean mAreCrudButtonsHidden = true;
    private View[]  mButtons;

    private AnimatorSet mAppearingAnimatorSet;
    private AnimatorSet mDisappearingAnimatorSet;


    public CrudButtonsHandler(View... buttons) {

        mButtons = buttons;
    }


    @Override
    public void toggleCrudButtonsVisibility() {

        if (mAreCrudButtonsHidden) {
            performAppearingAnimation();
        }
        else {
            performDisappearingAnimation();
        }

        mAreCrudButtonsHidden = !mAreCrudButtonsHidden;
    }


    @Override
    public void performAppearingAnimation() {

        if (mAppearingAnimatorSet == null) {
            buildAppearingAnimation(mButtons);
        }

        mAppearingAnimatorSet.start();
    }


    @Override
    public void performDisappearingAnimation() {

        if (mDisappearingAnimatorSet == null) {
            buildDisappearingAnimation(mButtons);
        }

        mDisappearingAnimatorSet.start();
    }


    @Override
    public boolean areCrudButtonsHidden() {

        return mAreCrudButtonsHidden;
    }


    private void buildAppearingAnimation(View[] views) {

        ObjectAnimator[] animators   = new ObjectAnimator[views.length];
        AnimatorSet      animatorSet = new AnimatorSet();

        for (int i = 0; i < views.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(views[i],
                                                             "x",
                                                             views[i].getX() - views[i].getWidth());
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setStartDelay(i * ANIMATION_DELAY);

            animators[i] = animator;
        }

        animatorSet.playTogether(animators);

        animatorSet.setDuration(ANIMATION_DURATION);

        mAppearingAnimatorSet = animatorSet;
    }


    private void buildDisappearingAnimation(View[] views) {

        ObjectAnimator[] animators   = new ObjectAnimator[views.length];
        AnimatorSet      animatorSet = new AnimatorSet();

        for (int i = 0; i < views.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(views[i],
                                                             "x",
                                                             views[i].getX() + views[i].getWidth());
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setStartDelay(i * ANIMATION_DELAY);

            animators[i] = animator;
        }

        animatorSet.playTogether(animators);

        animatorSet.setDuration(ANIMATION_DURATION);

        mDisappearingAnimatorSet = animatorSet;
    }

}
