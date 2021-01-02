package com.converter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fragments.CategoryFragment;


public class MainActivity extends AppCompatActivity {

    //region Constants
    private final int CATEGORIES_AMOUNT = 3;
    //endregion

    //region Variables
    private ViewPager                   _viewPager;
    private FragmentPagerAdapter        _pagerAdapter;
    private FragmentManager             _fragmentManager;

    private EditText    _etActiveInput;
    private EditText    _etOutput1;
    private EditText    _etOutput2;

    private CategoryFragment _activeFragment;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        _fragmentManager = getSupportFragmentManager();
        _fragmentManager.setFragmentFactory(new ConverterFragmentFactory(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _viewPager    = findViewById(R.id.categoryPager);
        _pagerAdapter = new CategoryAreaPagerAdapter(_fragmentManager, CATEGORIES_AMOUNT);
        _viewPager.setAdapter(_pagerAdapter);


        //region Events
//        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//
//            @Override
//            public void onPageSelected(int position) {}
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
////                    _activeFragment = (CategoryFragment) _pagerAdapter.getItem(_viewPager.getCurrentItem());
////                    _etActiveInput  = _activeFragment.getTextFields().getValue0();
////                    _etOutput1      = _activeFragment.getTextFields().getValue1();
////                    _etOutput2      = _activeFragment.getTextFields().getValue2();
//                }
//            }
//        });

//        _activeInput.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                String buffer;
//                double value;
//
//                if (s.charAt(s.length() - 1) == '.') {
//                    buffer = new String(s.toString() + "0");
//                }
//                else {
//                    buffer = new String(s.toString());
//                }
//
//                value = Double.parseDouble(buffer);
//
//                if (_activeInput.equals(_etGram)) {
//                    _etKilo.setText(
//                        String.format(Locale.US, "%f", value / 1000.0)
//                    );
//                    _etTon.setText(
//                        String.format(Locale.US, "%f", value / 1000.0 / 1000.0)
//                    );
//                }
//                else if (_activeInput.equals(_etKilo)) {
//                    _etGram.setText(
//                        String.format(Locale.US, "%f", value * 1000.0)
//                    );
//                    _etTon.setText(
//                        String.format(Locale.US, "%f", value / 1000.0)
//                    );
//                }
//                else {
//                    _etGram.setText(
//                        String.format(Locale.US, "%f", value * 1000.0 * 1000.0)
//                    );
//                    _etKilo.setText(
//                        String.format(Locale.US, "%f", value * 1000.0)
//                    );
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
        //endregion
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        _activeFragment = (CategoryFragment) _pagerAdapter.getItem(_viewPager.getCurrentItem());
////        _etActiveInput  = _activeFragment.getTextFields().getValue0();
////        _etActiveInput  = _activeFragment.getTextFields().getValue1();
////        _etActiveInput  = _activeFragment.getTextFields().getValue2();
//    }

    public EditText getActiveInput() {
        return _etActiveInput;
    }

    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _etActiveInput.append(btn.getText());
    }

}