package com.tabatatimer.ui.sequence.managers;


import com.tabatatimer.managers.IRecyclerViewItemManager;



public interface ISequenceRecyclerViewManager extends IRecyclerViewItemManager {

    byte NO_ACTIVE = -1;


    int getActiveIndex();
    void setActiveIndex(int index);

    void applyStyleActive();
    void cancelStyleActive();
    void smoothScrollToActivePosition();

}
