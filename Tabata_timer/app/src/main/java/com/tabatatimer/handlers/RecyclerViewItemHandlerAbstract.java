package com.tabatatimer.handlers;


import android.view.View;



public abstract class RecyclerViewItemHandlerAbstract {

    public final int NO_SELECTED = -1;

    protected ICrudButtonsHandler mCrudButtonsHandler;
    protected View                mSelectedView;
    protected int                 mSelectedIndex = NO_SELECTED;
    protected int                 mCollectionLength;


    public abstract void applyStyleSelected();
    public abstract void cancelStyleSelected();
    public abstract void applyStyleDefault(int index);
    public abstract void setSelectedView();


    public int getSelectedIndex() {

        return mSelectedIndex;
    }


    public void setSelectedIndex(int index) {

        mSelectedIndex = (index >= 0) && (index < mCollectionLength) ?
                         index :
                         NO_SELECTED;

        setSelectedView();
    }


    public View getSelectedView() {

        return mSelectedView;
    }


    public void setCrudButtonsHandler(ICrudButtonsHandler handler) {

        mCrudButtonsHandler = handler;
    }


    public void updateCollectionLength(int length) {

        mCollectionLength = length;
    }

}
