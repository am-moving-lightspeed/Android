package services;


import androidx.lifecycle.ViewModel;


public class ViewPagerViewModel extends ViewModel {

    private int _viewPagerCurrentItem = 0;


    public int getCurrentItem() {

        return _viewPagerCurrentItem;
    }


    public void setCurrentItem(int position) {

        _viewPagerCurrentItem = position;
    }

}
