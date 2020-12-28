package com.converter;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    //region Variables
    private EditText    _activeInput;
    private EditText    _etValue1;
    private EditText    _etValue2;
    private EditText    _etValue3;

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
        _etValue1 = findViewById(R.id.inputField1);
        _etValue2 = findViewById(R.id.inputField2);
        _etValue3 = findViewById(R.id.inputField3);

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
        //endregion

        _activeInput = _etValue1;

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
        //endregion
    }

    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _activeInput.append(btn.getText());
    }

}