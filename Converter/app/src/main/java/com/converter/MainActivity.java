package com.converter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //region Classes
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new CategoryFragment();
        }

        @Override
        public int getCount() {
            return CATEGORIES_AMOUNT;
        }
    }
    //endregion

    //region Constants
    private static final int CATEGORIES_AMOUNT = 3;
    //endregion

    //region Variables
    private ViewPager       _viewPager;
    private PagerAdapter    _pagerAdapter;

    private EditText    _activeInput;
    private EditText    _etGram;
    private EditText    _etKilo;
    private EditText    _etTon;

    private Button      _bNum0;
    private Button      _bNum1;
    private Button      _bNum2;
    private Button      _bNum3;
    private Button      _bNum4;
    private Button      _bNum5;
    private Button      _bNum6;
    private Button      _bNum7;
    private Button      _bNum8;
    private Button      _bNum9;
    private Button      _bDot;
    private Button      _bEraseLast;
    private Button      _bEraseAll;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Find by id
        _etGram = findViewById(R.id.inputField1);
        _etKilo = findViewById(R.id.inputField2);
        _etTon = findViewById(R.id.inputField3);

        _bNum0      = findViewById(R.id.kbNum0);
        _bNum1      = findViewById(R.id.kbNum1);
        _bNum2      = findViewById(R.id.kbNum2);
        _bNum3      = findViewById(R.id.kbNum3);
        _bNum4      = findViewById(R.id.kbNum4);
        _bNum5      = findViewById(R.id.kbNum5);
        _bNum6      = findViewById(R.id.kbNum6);
        _bNum7      = findViewById(R.id.kbNum7);
        _bNum8      = findViewById(R.id.kbNum8);
        _bNum9      = findViewById(R.id.kbNum9);
        _bDot       = findViewById(R.id.kbDot);
        _bEraseLast = findViewById(R.id.kbEraseLast);
        _bEraseAll  = findViewById(R.id.kbEraseAll);

        _viewPager      = findViewById(R.id.categoryPager);
        _pagerAdapter   = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        _viewPager.setAdapter(_pagerAdapter);
        //endregion

        _activeInput = _etGram;

        //region Events
        _bEraseLast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (_activeInput.getText().length() == 1) {
                    _activeInput.setText("");
                }
                else {
                    _activeInput.setText(
                        _activeInput.getText()
                                    .toString()
                                    .substring(0, _activeInput.length() - 1)
                    );
                }
            }
        });

        _bEraseAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                _activeInput.setText("");
            }
        });

        _bDot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (_activeInput.getText().toString().isEmpty()) {
                    return;
                }
                else {
                    _activeInput.append(".");
                }
            }
        });

        _activeInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String buffer;
                double value;

                if (s.charAt(s.length() - 1) == '.') {
                    buffer = new String(s.toString() + "0");
                }
                else {
                    buffer = new String(s.toString());
                }

                value = Double.parseDouble(buffer);

                if (_activeInput.equals(_etGram)) {
                    _etKilo.setText(
                        String.format(Locale.US, "%f", value / 1000.0)
                    );
                    _etTon.setText(
                        String.format(Locale.US, "%f", value / 1000.0 / 1000.0)
                    );
                }
                else if (_activeInput.equals(_etKilo)) {
                    _etGram.setText(
                        String.format(Locale.US, "%f", value * 1000.0)
                    );
                    _etTon.setText(
                        String.format(Locale.US, "%f", value / 1000.0)
                    );
                }
                else {
                    _etGram.setText(
                        String.format(Locale.US, "%f", value * 1000.0 * 1000.0)
                    );
                    _etKilo.setText(
                        String.format(Locale.US, "%f", value * 1000.0)
                    );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        //endregion
    }

    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _activeInput.append(btn.getText());
    }

}