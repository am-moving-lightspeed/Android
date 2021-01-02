package com.converter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import fragments.KeyboardFragment;


public class ConverterFragmentFactory extends FragmentFactory {

    private AppCompatActivity _activityContext;


    public ConverterFragmentFactory(AppCompatActivity activityContext) {

        super();
        _activityContext = activityContext;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);
        if(fragmentClass.equals(KeyboardFragment.class)) {
            return new KeyboardFragment(_activityContext);
        }
        else {
            return super.instantiate(classLoader, className);
        }
    }

}
