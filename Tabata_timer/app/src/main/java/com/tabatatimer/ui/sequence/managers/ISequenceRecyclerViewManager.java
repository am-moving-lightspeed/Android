package com.tabatatimer.ui.sequence.managers;


import android.view.View;

import com.tabatatimer.managers.IRecyclerViewItemManager;



public interface ISequenceRecyclerViewManager extends IRecyclerViewItemManager {

    byte NO_ACTIVE = -1;


    int getActiveIndex();
    void setActiveIndex(int index);

    View getActiveView();

    void applyStyleActive();
    void cancelStyleActive();
    void smoothScrollToActivePosition();

}
