package com.converter;


import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragments.CurrencyFragment;
import fragments.DistanceFragment;
import fragments.WeightFragment;


public class CategoryAreaPagerAdapter extends FragmentPagerAdapter {

    private int                     _count;
    private SparseArray<Fragment>   _fragments;
    private AppCompatActivity       _activityContext;


    public CategoryAreaPagerAdapter(FragmentManager   fragmentManager,
                                    int               count,
                                    AppCompatActivity activityContext) {

        super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        _fragments       = new SparseArray<>();
        _activityContext = activityContext;
        _count           = count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        _fragments.put(position, fragment);

        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        super.destroyItem(container, position, object);
        _fragments.remove(position);
    }

    @Override
    public int getCount() {

        return _count;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 1:
                return _fragments.get(1, new DistanceFragment(_activityContext));
            case 2:
                return _fragments.get(2, new CurrencyFragment(_activityContext));
            case 0:
            default:
                return _fragments.get(0, new WeightFragment(_activityContext));
        }
    }

}
