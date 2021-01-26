package com.tabatatimer.factories;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tabatatimer.sqlite.IDbManager;
import com.tabatatimer.viewmodels.SharedDbViewModel;

import java.lang.reflect.InvocationTargetException;



public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private IDbManager mDbManager;


    public CustomViewModelFactory(IDbManager manager) {

        mDbManager = manager;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        try {
            if (modelClass.equals(SharedDbViewModel.class)) {
                return modelClass.getConstructor(IDbManager.class)
                                 .newInstance(mDbManager);
            }
            else {
                return super.create(modelClass);
            }
        }
        catch (IllegalAccessException | InstantiationException |
                   InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return super.create(modelClass);
        }
    }

}
