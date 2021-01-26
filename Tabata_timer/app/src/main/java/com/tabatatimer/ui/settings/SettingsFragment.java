package com.tabatatimer.ui.settings;


import android.content.ContentProviderClient;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.tabatatimer.R;



public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel                                  settingsViewModel;
    private SharedPreferences                                  sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preferences, rootKey);

        assert getContext() != null;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener =
            providePreferenceChangeListener();

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }


    private SharedPreferences.OnSharedPreferenceChangeListener providePreferenceChangeListener() {

        return new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                assert getActivity() != null;

                switch (key) {
                    case "theme":
                        String value = sharedPreferences.getString(key, "2");

                        assert value != null;

                        resolveTheme(value);

                        break;

                    default:
                        break;

                }
            }
        };
    }


    private void resolveTheme(String value) {

        switch (value) {
            case "0":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

}