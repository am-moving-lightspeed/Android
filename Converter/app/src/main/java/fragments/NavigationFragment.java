package fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.converter.R;

import org.javatuples.Triplet;

import services.NavigationButtonOnClickListener;
import services.ViewPagerViewModel;


public class NavigationFragment extends Fragment {

    private AppCompatActivity _activityContext;

    private ViewModel _viewModel;

    private Button _bCategory0;
    private Button _bCategory1;
    private Button _bCategory2;


    public NavigationFragment(AppCompatActivity activity) {

        super(R.layout.navigation);
        _activityContext = activity;
        _viewModel       = new ViewModelProvider(_activityContext).get(ViewPagerViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        _bCategory0 = view.findViewById(R.id.nav0);
        _bCategory1 = view.findViewById(R.id.nav1);
        _bCategory2 = view.findViewById(R.id.nav2);


        switch (((ViewPagerViewModel) _viewModel).getCurrentItem()) {
            case 0:
                resetActiveButtonColor(_bCategory0,
                                       _bCategory1,
                                       _bCategory2);
                break;
            case 1:
                resetActiveButtonColor(_bCategory1,
                                       _bCategory0,
                                       _bCategory2);
                break;
            case 2:
                resetActiveButtonColor(_bCategory2,
                                       _bCategory0,
                                       _bCategory1);
                break;
        }


        //region Events
        _bCategory0.setOnClickListener(
            new NavigationButtonOnClickListener(_bCategory0, _activityContext)
        );

        _bCategory1.setOnClickListener(
            new NavigationButtonOnClickListener(_bCategory1, _activityContext)
        );

        _bCategory2.setOnClickListener(
            new NavigationButtonOnClickListener(_bCategory2, _activityContext)
        );
        //endregion
    }


    public Triplet<Button, Button, Button> getNavigationButtons() {

        return new Triplet<>(_bCategory0, _bCategory1, _bCategory2);
    }


    public void resetActiveButtonColor(Button category0, Button category1, Button category2) {

        category0.setTextColor(
            getResources().getColor(R.color.colorOrangeSpecific,
                                    _activityContext.getTheme())
        );

        category1.setTextColor(
            getResources().getColor(R.color.colorLightGreySpecific,
                                    _activityContext.getTheme())
        );

        category2.setTextColor(
            getResources().getColor(R.color.colorLightGreySpecific,
                                    _activityContext.getTheme())
        );
    }

}
