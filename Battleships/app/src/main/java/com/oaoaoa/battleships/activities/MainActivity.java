package com.oaoaoa.battleships.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.fragments.MainMenuFragment;
import com.oaoaoa.battleships.misc.AnimationsProvider;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                                   .setReorderingAllowed(true)
                                   .add(R.id.fragmentContainerView_main, MainMenuFragment.class, null)
                                   .commit();

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_main_container)
        );
    }

}
