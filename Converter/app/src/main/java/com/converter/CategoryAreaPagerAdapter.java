package com.converter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class CategoryAreaPagerAdapter extends PagerAdapter {

    private int[]           _layouts;
    private LayoutInflater  _inflater;

    public CategoryAreaPagerAdapter(int[]   layouts,
                                    Context context) {

        this._layouts = layouts;
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return _layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = _inflater.inflate(_layouts[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        View view = (View) object;
        container.removeView(view);
    }
}
