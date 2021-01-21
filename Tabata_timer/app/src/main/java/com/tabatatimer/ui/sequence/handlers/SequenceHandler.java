package com.tabatatimer.ui.sequence.handlers;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;



public final class SequenceHandler extends SequenceHandlerAbstract {

    private Context                    mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView               mRecyclerView;


    public SequenceHandler(Context context,
                           RecyclerView recyclerView,
                           RecyclerView.LayoutManager layoutManager) {

        mContext       = context;
        mRecyclerView  = recyclerView;
        mLayoutManager = layoutManager;
    }


    @Override
    public void applyStyleActive() {

        if (mActiveIndex == mSelectedIndex &&
            !mCrudButtonsHandler.areCrudButtonsHidden()) {

            cancelStyleSelected();
            mCrudButtonsHandler.toggleCrudButtonsVisibility();
            mSelectedIndex = NO_SELECTED;
        }

        View view = mLayoutManager.findViewByPosition(mActiveIndex);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_background_active,
                        mContext.getTheme()
                    )
                );

            view.findViewById(R.id.textView_sequenceStage_description)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.textView_sequenceStage_timeLeft)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.progressBar_sequenceStage_progressBar)
                .setVisibility(View.VISIBLE);
        }
        else if (mActiveIndex >= 0 ||
                 mActiveIndex < mCollectionLength) {

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == RecyclerView.SCROLL_STATE_SETTLING) {

                        applyStyleActive();
                        mRecyclerView.removeOnScrollListener(this);
                    }
                }
            });
        }
    }


    @Override
    public void cancelStyleActive() {

        View view = mLayoutManager.findViewByPosition(mActiveIndex);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_background_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(R.id.textView_sequenceStage_description)
                .setVisibility(View.GONE);
            view.findViewById(R.id.textView_sequenceStage_timeLeft)
                .setVisibility(View.GONE);
            view.findViewById(R.id.progressBar_sequenceStage_progressBar)
                .setVisibility(View.GONE);
        }
    }


    @Override
    public void applyStyleSelected() {

        View view = mLayoutManager.findViewByPosition(mSelectedIndex);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_background_selected,
                        mContext.getTheme()
                    )
                );

            view.findViewById(R.id.textView_sequenceStage_description)
                .setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void cancelStyleSelected() {

        View view = mLayoutManager.findViewByPosition(mSelectedIndex);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_background_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(R.id.textView_sequenceStage_description)
                .setVisibility(View.GONE);
        }
    }


    @Override
    public void applyStyleDefault(int position) {

        View view = mLayoutManager.findViewByPosition(position);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_background_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(R.id.textView_sequenceStage_description)
                .setVisibility(View.GONE);
            view.findViewById(R.id.textView_sequenceStage_timeLeft)
                .setVisibility(View.GONE);
            view.findViewById(R.id.progressBar_sequenceStage_progressBar)
                .setVisibility(View.GONE);
        }
    }


    @Override
    public void setSelectedView() {

        mSelectedView = mLayoutManager.findViewByPosition(mSelectedIndex);
    }


    public void smoothScrollToActivePosition() {

        if (mActiveIndex < 0 || mActiveIndex >= mCollectionLength) {

            mRecyclerView.smoothScrollToPosition(0);
        }
        else {

            mRecyclerView.smoothScrollToPosition(mActiveIndex);
        }
    }

}
