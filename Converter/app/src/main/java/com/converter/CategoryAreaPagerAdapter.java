package com.converter;


import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragments.CurrencyFragment;
import fragments.DistanceFragment;
import fragments.WeightFragment;


public class CategoryAreaPagerAdapter extends FragmentPagerAdapter {

    private int                     _count;
    private SparseArray<Fragment>   _fragments;


    public CategoryAreaPagerAdapter(FragmentManager fragmentManager, int count) {

        super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        _count     = count;
        _fragments = new SparseArray<>();
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
                return _fragments.get(1, new DistanceFragment());
            case 2:
                return _fragments.get(2, new CurrencyFragment());
            case 0:
            default:
                return _fragments.get(0, new WeightFragment());
        }
    }

}
