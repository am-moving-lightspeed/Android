package com.tabatatimer.activities;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.tabatatimer.R;
import com.tabatatimer.factories.CustomFragmentFactory;
import com.tabatatimer.factories.CustomViewModelFactory;
import com.tabatatimer.sqlite.DbManager;
import com.tabatatimer.sqlite.IDbManager;
import com.tabatatimer.sqlite.IFetching;
import com.tabatatimer.ui.library.LibraryFragment;
import com.tabatatimer.viewmodels.SharedDbViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController       mNavController;
    private NavigationView      mNavView;

    private IDbManager mDbManager;


    public NavController getNavController() {

        return mNavController;
    }

    public NavigationView getNavView() {

        return mNavView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DbManager.instantiate(getApplicationContext());
        mDbManager = DbManager.getInstance();

        SharedDbViewModel sharedDbVM = new ViewModelProvider(this, new CustomViewModelFactory(mDbManager))
                                           .get(SharedDbViewModel.class);

        getSupportFragmentManager().setFragmentFactory(new CustomFragmentFactory(this,
                                                                                 sharedDbVM));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            preloadLibrary();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                                       .setReorderingAllowed(true)
                                       .add(R.id.main_navigationHostFragment, LibraryFragment.class, null)
                                       .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout   drawer         = findViewById(R.id.drawer_layout);
        mNavView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.fragment_sequence,
                                                               R.id.fragment_library,
                                                               R.id.fragment_settings,
                                                               R.id.fragment_empty)
                                   .setOpenableLayout(drawer)
                                   .build();

        mNavController = Navigation.findNavController(this, R.id.main_navigationHostFragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavView, mNavController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.main_navigationHostFragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
               || super.onSupportNavigateUp();
    }


    private void preloadLibrary() {

        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
        startActivity(intent);

        mDbManager.setFetchingListener(new IFetching() {

            @Override
            public void onFetchingComplete(Cursor cursor) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("preloadComplete", "true");
                startActivity(intent);
                mDbManager.removeFetchingListener();
            }
        });
        mDbManager.fetchSequences(3000);
    }

}