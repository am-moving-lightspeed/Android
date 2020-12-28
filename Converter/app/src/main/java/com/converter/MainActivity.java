package com.converter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    //region Constants
    private final int[] _LAYOUTS = new int[] {
        R.layout.category_slide_weight,
        R.layout.category_slide_distance,
        R.layout.category_slide_currency,
    };
    //endregion

    //region Variables
    private ViewPager                   _viewPager;
    private CategoryAreaPagerAdapter    _pagerAdapter;

    private EditText    _activeInput;
    private EditText    _etGram;
    private EditText    _etKilo;
    private EditText    _etTon;

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

        _bDot       = findViewById(R.id.kbDot);
        _bEraseLast = findViewById(R.id.kbEraseLast);
        _bEraseAll  = findViewById(R.id.kbEraseAll);

        _viewPager      = findViewById(R.id.categoryPager);
        _pagerAdapter   = new CategoryAreaPagerAdapter(_LAYOUTS, this);
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

    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _activeInput.append(btn.getText());
    }

}