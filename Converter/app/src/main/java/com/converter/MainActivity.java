package com.converter;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.javatuples.Quartet;
import org.javatuples.Triplet;

import fragments.CategoryFragment;
import fragments.NavigationFragment;
import services.ViewPagerViewModel;


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
    private ViewModel            _viewModel;
    private Vibrator             _vibrator;

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
        _viewPager.setOffscreenPageLimit(_LAYOUTS.length - 1);

        _viewModel = new ViewModelProvider(this).get(ViewPagerViewModel.class);

        _vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        //region Events
        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {

                CategoryFragment active =
                    (CategoryFragment) _pagerAdapter.getItem(_viewPager.getCurrentItem());
                NavigationFragment navigation =
                    (NavigationFragment) _fragmentManager.findFragmentById(R.id.navigationFragmentContainer);

                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    _etOutput0     = active.getTextFields().getValue0();
                    _etOutput1     = active.getTextFields().getValue1();
                    _etOutput2     = active.getTextFields().getValue2();
                    _etActiveInput = active.getTextFields().getValue3();

                    if (navigation != null) {
                        Triplet<Button, Button, Button> navigationButtons = navigation.getNavigationButtons();

                        switch (_viewPager.getCurrentItem()) {
                            case 0:
                                navigation.resetActiveButtonColor(navigationButtons.getValue0(),
                                                                  navigationButtons.getValue1(),
                                                                  navigationButtons.getValue2());
                                break;
                            case 1:
                                navigation.resetActiveButtonColor(navigationButtons.getValue1(),
                                                                  navigationButtons.getValue0(),
                                                                  navigationButtons.getValue2());
                                break;
                            case 2:
                                navigation.resetActiveButtonColor(navigationButtons.getValue2(),
                                                                  navigationButtons.getValue0(),
                                                                  navigationButtons.getValue1());
                                break;
                        }
                    }
                }
            }


            @Override
            public void onPageSelected(int position) {

                ((ViewPagerViewModel) _viewModel).setCurrentItem(position);
            }


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
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


    public void scrollPager(int position) {

        _viewPager.setCurrentItem(position);
    }


    public ViewPager getViewPager() {

        return _viewPager;
    }


    //region OnClick event handlers
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void kbNumButtonClick(View view) {

        Button btn = (Button) view;
        _etActiveInput.append(btn.getText());

        _vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    //endregion

}