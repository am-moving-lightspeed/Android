package services;


import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.converter.MainActivity;


public class NavigationButtonOnClickListener implements View.OnClickListener {

    private Button _bCategory;

    private AppCompatActivity _activityContext;


    public NavigationButtonOnClickListener(Button            category,
                                           AppCompatActivity activity) {

        _bCategory       = category;
        _activityContext = activity;
    }


    @Override
    public void onClick(View view) {

        ((MainActivity) _activityContext).scrollPager(
            Integer.parseInt((String) _bCategory.getTag())
        );
    }

}
