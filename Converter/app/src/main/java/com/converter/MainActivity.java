package com.converter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.javatuples.Quartet;

import fragments.CategoryFragment;


public class MainActivity extends AppCompatActivity {

    //region Constants
    private final int[] _LAYOUTS = {
        R.layout.category_slide_weight,
        R.layout.category_slide_distance,
        R.layout.category_slide_currency
    };
    //endregion

    //region Variables
    private ViewPager            _viewPager;
    private FragmentPagerAdapter _pagerAdapter;
    private FragmentManager      _fragmentManager;

    private EditText _etActiveInput;
    private EditText _etOutput0;
    private EditText _etOutput1;
    private EditText _etOutput2;
    //endregion


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        _fragmentManager = getSupportFragmentManager();
        _fragmentManager.setFragmentFactory(new ConverterFragmentFactory(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _viewPager    = findViewById(R.id.categoryPager);
        _pagerAdapter = new CategoryAreaPagerAdapter(_fragmentManager,
                                                     _LAYOUTS.length,
                                                     this);
        _viewPager.setAdapter(_pagerAdapter);


        //region Events
        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {

                CategoryFragment active =
                    (CategoryFragment) _pagerAdapter.getItem(_viewPager.getCurrentItem());
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    _etOutput0     = active.getTextFields().getValue0();
                    _etOutput1     = active.getTextFields().getValue1();
                    _etOutput2     = active.getTextFields().getValue2();
                    _etActiveInput = active.getTextFields().getValue3();

//                    InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(_etActiveInput.getWindowToken(), 0);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}
        });
        //endregion
    }

    public void setTextFields(Quartet<EditText, EditText, EditText, EditText> fields) {

        _etOutput0     = fields.getValue0();
        _etOutput1     = fields.getValue1();
        _etOutput2     = fields.getValue2();
        _etActiveInput = fields.getValue3();
    }

    public Quartet<EditText, EditText, EditText, EditText> getTextFields() {

        return new Quartet<>(_etOutput0, _etOutput1, _etOutput2, _etActiveInput);
    }

    public int getCurrentFragmentLayout() {

        return _LAYOUTS[_viewPager.getCurrentItem()];
    }

    //region OnClick event handlers
    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _etActiveInput.append(btn.getText());
    }
    //endregion

}