package com.tabatatimer.ui.sequence.handlers;


import com.tabatatimer.handlers.RecyclerViewItemHandlerAbstract;



public abstract class SequenceHandlerAbstract extends RecyclerViewItemHandlerAbstract {

    public final int NO_ACTIVE = -1;

    protected int mActiveIndex = NO_ACTIVE;


    public abstract void applyStyleActive();
    public abstract void cancelStyleActive();
    public abstract void smoothScrollToActivePosition();


    public int getActiveIndex() {

        return mActiveIndex;
    }


    public void setActiveIndex(int index) {

        mActiveIndex = (index >= 0) && (index < mCollectionLength) ?
                       index :
                       NO_ACTIVE;
    }

}
