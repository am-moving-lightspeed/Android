package com.tabatatimer.viewmodels;


import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tabatatimer.sqlite.IDbManager;



public class SharedDbViewModel extends ViewModel {

    private IDbManager mDbManager;

    private MutableLiveData<Cursor>  mSequenceStageCursor;
    private MutableLiveData<Cursor>  mSequenceCursor;
    private MutableLiveData<Integer> mFK;


    public SharedDbViewModel(IDbManager manager) {

        mDbManager = manager;

        mSequenceStageCursor = new MutableLiveData<>();
        mSequenceCursor      = new MutableLiveData<>();
        mFK                  = new MutableLiveData<>();
        mFK.setValue(-1);
    }


    public Cursor getSequenceCursor() {

        mSequenceCursor.setValue(mDbManager.getSequences());
        return mSequenceCursor.getValue();
    }


    public Cursor getSequenceStageCursor() {

        mSequenceStageCursor.setValue(mDbManager.getSequenceStages());
        return mSequenceStageCursor.getValue();
    }


    public Integer getLastFK() {

        return mFK.getValue();
    }


    public void setLastFK(Integer fk) {

        mFK.setValue(fk >= 1 ? fk : -1);
    }


    @Override
    protected void onCleared() {

        super.onCleared();
    }

}
