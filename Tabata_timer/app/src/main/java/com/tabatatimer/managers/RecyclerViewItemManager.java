package com.tabatatimer.managers;


import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;



public abstract class RecyclerViewItemManager implements IRecyclerViewItemManager {

    protected Context                    mContext;
    protected View                       mSelectedView;
    protected RecyclerView.LayoutManager mLayoutManager;


    protected int mSelectedIndex;
    protected int mCollectionLength;



    public RecyclerViewItemManager(Context context,
                                   RecyclerView.LayoutManager manager) {

        mContext       = context;
        mLayoutManager = manager;
        mSelectedView  = null;
        mSelectedIndex = NO_SELECTED;
    }


    public abstract void applyStyleDefault(int position, @IdRes int... idRes);


    @Override
    public int getSelectedIndex() {

        return mSelectedIndex;
    }


    @Override
    public void setSelectedIndex(int index) {

        mSelectedIndex = (index >= 0) && (index < mCollectionLength) ?
                         index :
                         NO_SELECTED;

        setSelectedView(mLayoutManager.findViewByPosition(mSelectedIndex));
    }


    @Override
    public View getSelectedView() {

        return mSelectedView;
    }


    @Override
    public void setSelectedView(View view) {

        mSelectedView = view;
    }


    @Override
    public void updateCollectionLength(int length) {

        mCollectionLength = length;
    }


    @Override
    public void applyStyleSelected(@IdRes int backgroundId,
                                   @IdRes int descriptionId) {

        View view = mLayoutManager.findViewByPosition(mSelectedIndex);

        if (view != null) {

            view.findViewById(backgroundId)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_bg_selected,
                        mContext.getTheme()
                    )
                );

            view.findViewById(descriptionId)
                .setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void cancelStyleSelected(@IdRes int backgroundId,
                                    @IdRes int descriptionId) {

        View view = mLayoutManager.findViewByPosition(mSelectedIndex);

        if (view != null) {

            view.findViewById(backgroundId)
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_bg_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(descriptionId)
                .setVisibility(View.GONE);
        }
    }

}
