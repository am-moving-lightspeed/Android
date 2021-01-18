package com.tabatatimer.layoutmanagers;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.StagesRecyclerViewAdapter;
import com.tabatatimer.ui.sequence.SequenceFragment;



public class StagesRecyclerViewLayoutManager extends LinearLayoutManager {

    private final int ANIMATION_DELAY    = 100;
    private final int ANIMATION_DURATION = 300;

    private boolean areCrudButtonsHidden;

    private RecyclerView mStagesRecyclerView;
    private View         mSelectedView;

    private StagesRecyclerViewAdapter mAdapter;
    private SequenceFragment          mParentFragment;

    private AnimatorSet mFabAppearing;
    private AnimatorSet mFabDisappearing;


    public StagesRecyclerViewLayoutManager(Fragment parentFragment,
                                           RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter,
                                           Context context) {

        super(context);

        mAdapter             = (StagesRecyclerViewAdapter) adapter;
        mParentFragment      = (SequenceFragment) parentFragment;
        areCrudButtonsHidden = true;
    }


    public void setRecyclerView(RecyclerView view) {

        mStagesRecyclerView = view;
    }


    public void toggleCrudButtons() {

        if (areCrudButtonsHidden) {
            performFabAppearingAnimation();
        }
        else {
            performFabDisappearingAnimation();
        }

        areCrudButtonsHidden = !areCrudButtonsHidden;
    }


    public boolean areCrudButtonsHidden() {

        return areCrudButtonsHidden;
    }


    public void setSelectedView(View view) {

        mSelectedView = view;
    }


    public View getSelectedView() {

        return mSelectedView;
    }


    // region Apply styles
    public void applyStyleActive() {

        int position = mAdapter.getActivePosition();

        if (position == mAdapter.getSelectedPosition()) {
            cancelStyleSelected();
            mAdapter.setSelectedPosition(mAdapter.NO_SELECTED);
            performFabDisappearingAnimation();
            toggleCrudButtons();
        }

        View view = findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mParentFragment.getResources(),
                        R.drawable.shapes_sequence_stage_active,
                        mParentFragment.getContext()
                                       .getTheme()
                    )
                );

            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.sequenceStageTimeLeft)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.sequenceStageProgressBar)
                .setVisibility(View.VISIBLE);
        }
        else if (position >= 0 ||
                 position < mAdapter.getItemCount()) {

            mStagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == RecyclerView.SCROLL_STATE_SETTLING) {

                        applyStyleActive();
                        mStagesRecyclerView.removeOnScrollListener(this);
                    }
                }
            });
        }
    }


    public void cancelStyleActive() {

        int position = mAdapter.getActivePosition();

        View view = findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mParentFragment.getResources(),
                        R.drawable.shapes_sequence,
                        mParentFragment.getContext()
                                       .getTheme()
                    )
                );

            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageTimeLeft)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageProgressBar)
                .setVisibility(View.GONE);
        }
    }


    public void applyStyleSelected() {

        int position = mAdapter.getSelectedPosition();

        View view = findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mParentFragment.getResources(),
                        R.drawable.shapes_sequence_stage_selected,
                        mParentFragment.getContext()
                                       .getTheme()
                    )
                );

            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.VISIBLE);
        }
    }


    public void cancelStyleSelected() {

        int position = mAdapter.getSelectedPosition();

        View view = findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mParentFragment.getResources(),
                        R.drawable.shapes_sequence,
                        mParentFragment.getContext()
                                       .getTheme()
                    )
                );

            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.GONE);
        }
    }


    public void applyDefaultStyle(int position) {

        View view = findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mParentFragment.getResources(),
                        R.drawable.shapes_sequence,
                        mParentFragment.getContext()
                                       .getTheme()
                    )
                );

            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageTimeLeft)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageProgressBar)
                .setVisibility(View.GONE);
        }
    }
    // endregion


    // region Animations
    public void buildAppearingAnimation(View... views) {

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

        mFabAppearing = animatorSet;
    }


    public void buildDisappearingAnimation(View... views) {

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

        mFabDisappearing = animatorSet;
    }


    public void performFabAppearingAnimation() {

        if (mFabAppearing == null) {

            buildAppearingAnimation(mParentFragment.getCrudButtonsFrames());
        }

        mFabAppearing.start();
    }


    public void performFabDisappearingAnimation() {

        if (mFabDisappearing == null) {

            buildDisappearingAnimation(mParentFragment.getCrudButtonsFrames());
        }

        mFabDisappearing.start();
    }
    // endregion

}
