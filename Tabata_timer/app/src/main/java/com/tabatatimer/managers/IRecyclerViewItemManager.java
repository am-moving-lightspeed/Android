package com.tabatatimer.managers;


import android.view.View;

import androidx.annotation.IdRes;



public interface IRecyclerViewItemManager {

    byte NO_SELECTED = -1;


    int getSelectedIndex();
    void setSelectedIndex(int index);

    View getSelectedView();
    void setSelectedView(View view);

    void updateCollectionLength(int length);

    void applyStyleSelected(@IdRes int bgRes, @IdRes int descriptionRes);
    void cancelStyleSelected(@IdRes int bgRes, @IdRes int descriptionRes);
    void applyStyleDefault(int index, @IdRes int... idRes);

}
