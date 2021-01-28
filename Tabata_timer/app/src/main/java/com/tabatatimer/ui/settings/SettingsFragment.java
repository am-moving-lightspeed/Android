package com.tabatatimer.ui.settings;



import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.preference.ListPreference;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.SwitchPreference;

import com.tabatatimer.R;
import com.tabatatimer.sqlite.DbManager;

import java.util.Arrays;
import java.util.Locale;



public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences preferences;
    private int               fontScaleIndex;
    private int               languageIndex;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        if (preferences.getBoolean("theme", true)) {
            //TODO replace
            requireActivity().setTheme(R.style.MainTheme);
        }
        Configuration configuration = new Configuration();
        configuration.locale    = initLanguage();
        configuration.fontScale = initFontSize();

        requireActivity().getBaseContext()
                         .getResources()
                         .updateConfiguration(configuration, null);
//        getFragmentManager().beginTransaction()
//                            .replace(android.R.id.content, new ChangeSettingsFragment())
//                            .commit();

        super.onCreate(savedInstanceState);

//        addPreferencesFromResource(R.xml.preferences);

        SwitchPreference theme = findPreference("theme");
        if (theme != null) {
            theme.setOnPreferenceChangeListener(onThemeChange);
        }

        ListPreference font = findPreference("font_size");
        if (font != null) {
            font.setOnPreferenceChangeListener(onFontChange);
            font.setValueIndex(fontScaleIndex);
        }

        ListPreference language = findPreference("lang");
        if (language != null) {
            language.setOnPreferenceChangeListener(onLanguageChange);
            language.setValueIndex(languageIndex);
        }

        Preference button = findPreference("DeleteAll");
        if (button != null) {
            button.setOnPreferenceClickListener(onDeleteClick);
        }

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.preferences);
    }


    private Locale initLanguage() {

        String value = preferences.getString("lang", "en");
        String asd = preferences.getString("font_size", "5.0");

        assert value != null;
        Locale locale = new Locale(value);
        Locale.setDefault(locale);

        languageIndex = Arrays.asList((getResources().getStringArray(R.array.preferences_languageAliasArray)))
                              .indexOf(value);

        return locale;
    }


    private float initFontSize() {

        String font = preferences.getString("font_size", "1.0");
        fontScaleIndex = Arrays.asList((getResources().getStringArray(R.array.preferences_textScaleAliasArray)))
                               .indexOf(font);

        assert font != null;
        return Float.parseFloat(font);
    }


    OnPreferenceChangeListener onLanguageChange = new OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue) {

            Locale locale = new Locale(newValue.toString());

            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getActivity().getResources()
                         .updateConfiguration(configuration, null);
            getActivity().recreate();

            return true;
        }
    };


    OnPreferenceChangeListener onFontChange = new OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue) {

            Configuration configuration = getResources().getConfiguration();

            configuration.fontScale = Float.parseFloat(newValue.toString());

            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager()
                         .getDefaultDisplay()
                         .getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getActivity().getBaseContext()
                         .getResources()
                         .updateConfiguration(configuration, metrics);
            getActivity().recreate();

            return true;
        }
    };


    OnPreferenceChangeListener onThemeChange = new OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue) {

            if ((boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            getActivity().recreate();

            return true;
        }
    };


    OnPreferenceClickListener onDeleteClick = new OnPreferenceClickListener() {

        @Override
        public boolean onPreferenceClick(Preference preference) {

            if (DbManager.getInstance() != null) {
                DbManager.getInstance()
                         .dropDatabase();
            }

            return false;
        }
    };

}
