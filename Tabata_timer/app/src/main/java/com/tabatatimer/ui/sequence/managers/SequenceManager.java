package com.tabatatimer.ui.sequence.managers;


import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.managers.RecyclerViewItemManager;



public class SequenceManager extends
                             RecyclerViewItemManager implements
                                                     ISequenceRecyclerViewManager {

    protected int mActiveIndex;

    protected RecyclerView mRecyclerView;

    protected ICrudButtonsManager mCrudButtonsManager;


    public SequenceManager(Context context,
                           RecyclerView recyclerView,
                           RecyclerView.LayoutManager layoutManager,
                           ICrudButtonsManager crudButtonsManager) {

        super(context, layoutManager);

        mActiveIndex        = NO_ACTIVE;
        mRecyclerView       = recyclerView;
        mCrudButtonsManager = crudButtonsManager;
    }


    @Override
    public int getActiveIndex() {

        return mActiveIndex;
    }


    @Override
    public void setActiveIndex(int index) {

        mActiveIndex = (index >= 0) && (index < mCollectionLength) ?
                       index :
                       NO_ACTIVE;
    }


    @Override
    public void applyStyleActive() {

        if (mActiveIndex == mSelectedIndex &&
            !mCrudButtonsManager.areCrudButtonsHidden()) {

            cancelStyleSelected(R.id.linearLayout_sequenceStage_background,
                                R.id.textView_sequenceStage_description);
            mCrudButtonsManager.toggleCrudButtonsVisibility();
            mSelectedIndex = NO_SELECTED;
        }

        View view = mLayoutManager.findViewByPosition(mActiveIndex);

        if (view != null) {

            view.findViewById(R.id.linearLayout_sequenceStage_background)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_bg_active,
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
                        R.drawable.item_bg_default,
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
    public void applyStyleDefault(int position, @IdRes int... idRes) {

        View view = mLayoutManager.findViewByPosition(position);

        if (view != null) {

            view.findViewById(idRes[0])
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_bg_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(idRes[1])
                .setVisibility(View.GONE);
            view.findViewById(idRes[2])
                .setVisibility(View.GONE);
            view.findViewById(idRes[3])
                .setVisibility(View.GONE);
        }
    }


    @Override
    public void smoothScrollToActivePosition() {

        if (mActiveIndex < 0 || mActiveIndex >= mCollectionLength) {

            mRecyclerView.smoothScrollToPosition(0);
        }
        else {

            mRecyclerView.smoothScrollToPosition(mActiveIndex);
        }
    }

}
