package com.converter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import fragments.CurrencyFragment;
import fragments.DistanceFragment;
import fragments.KeyboardFragment;
import fragments.NavigationFragment;
import fragments.WeightFragment;


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
        else if(fragmentClass.equals(NavigationFragment.class)) {
            return new NavigationFragment(_activityContext);
        }
        else if(fragmentClass.equals(WeightFragment.class)) {
            return new WeightFragment(_activityContext);
        }
        else if(fragmentClass.equals(DistanceFragment.class)) {
            return new DistanceFragment(_activityContext);
        }
        else if(fragmentClass.equals(CurrencyFragment.class)) {
            return new CurrencyFragment(_activityContext);
        }
        else {
            return super.instantiate(classLoader, className);
        }
    }

}
