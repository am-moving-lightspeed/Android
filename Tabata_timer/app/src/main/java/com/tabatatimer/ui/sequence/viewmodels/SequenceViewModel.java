package com.tabatatimer.ui.sequence.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class SequenceViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public SequenceViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }


    public LiveData<String> getText() {

        return mText;
    }

}