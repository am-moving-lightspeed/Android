package services;


import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.converter.MainActivity;
import com.converter.R;


public class NavigationButtonOnClickListener implements View.OnClickListener {

    private Button _bCategory0;
    private Button _bCategory1;
    private Button _bCategory2;

    private AppCompatActivity _activityContext;
    private Resources         _resources;


    public NavigationButtonOnClickListener(Button            category0,
                                           Button            category1,
                                           Button            category2,
                                           AppCompatActivity activity,
                                           Resources         resources) {

        _bCategory0      = category0;
        _bCategory1      = category1;
        _bCategory2      = category2;
        _activityContext = activity;
        _resources       = resources;
    }

    @Override
    public void onClick(View view) {

        _bCategory0.setTextColor(_resources.getColor(R.color.colorOrangeSpecific,
                                                     _activityContext.getTheme()));

        _bCategory1.setTextColor(_resources.getColor(R.color.colorLightGreySpecific,
                                                     _activityContext.getTheme()));

        _bCategory2.setTextColor(_resources.getColor(R.color.colorLightGreySpecific,
                                                     _activityContext.getTheme()));

        ((MainActivity) _activityContext).scrollPager(
            Integer.parseInt((String) _bCategory0.getTag())
        );
    }
}
