package com.oaoaoa.battleships.activities;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.fragments.MainMenuFragment;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                                   .setReorderingAllowed(true)
                                   .add(R.id.fragmentContainerView_main, MainMenuFragment.class, null)
                                   .commit();

        startBackgroundAnimation(findViewById(R.id.constraintLayout_main_container));
    }


    private void startBackgroundAnimation(View view) {

        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

}
