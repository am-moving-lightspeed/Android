package services;


import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.converter.R;


public class UnitOnTouchListener implements View.OnTouchListener {

    private float   _coordinateStartY;
    private float   _upperBorder;

    private View _vContainerSelected;
    private View _vContainer0;
    private View _vContainer1;

    private int _vContainerSelectedPosition;
    private int _vContainer0Position;
    private int _vContainer1Position;


    public UnitOnTouchListener(View view, @NonNull View parent) {

        _vContainerSelected = view;
        View tempView0      = parent.findViewById(R.id.unitRow0);
        View tempView1      = parent.findViewById(R.id.unitRow1);
        View tempView2      = parent.findViewById(R.id.unitRow2);

        distributeByValue(tempView0, tempView1, tempView2);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        _upperBorder = Math.min(_vContainerSelected.getTop(),
                                Math.min(_vContainer0.getTop(), _vContainer1.getTop()));

        distributeByAltitude();

        // Move view
        try {
            handleMovingEvent(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    /*
     * _vContainerSelected is the object that has caused this event,
     * _vContainer0 and _vContainer1 are its neighbours.
     */
    private void distributeByValue(View view0, View view1, View view2) {

        if (_vContainerSelected.equals(view0)) {
            _vContainer0 = view1;
            _vContainer1 = view2;
        }
        else if (_vContainerSelected.equals(view1)) {
            _vContainer0 = view0;
            _vContainer1 = view2;
        }
        else if (_vContainerSelected.equals(view2)) {
            _vContainer0 = view0;
            _vContainer1 = view1;
        }
    }

    /*
     * Call this method to rearrange objects, ordering by
     * their Y coordinate, so _vContainer0's Y-offset is always
     * lower than _vContainer1's.
     */
    private void distributeByAltitude() {

        if (_vContainer0.getTop() > _vContainer1.getTop()) {

            View temp    = _vContainer0;
            _vContainer0 = _vContainer1;
            _vContainer1 = temp;
        }

        if (_vContainerSelected.getTop() < _vContainer0.getTop()) {
            _vContainerSelectedPosition = 0;
            _vContainer0Position        = 1;
            _vContainer1Position        = 2;
        }
        else if (_vContainerSelected.getTop() < _vContainer1.getTop()) {
            _vContainerSelectedPosition = 1;
            _vContainer0Position        = 0;
            _vContainer1Position        = 2;
        }
        else {
            _vContainerSelectedPosition = 2;
            _vContainer0Position        = 0;
            _vContainer1Position        = 1;
        }
    }

    private void handleMovingEvent(MotionEvent event) throws InterruptedException {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                _coordinateStartY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if(_vContainerSelectedPosition == 0) {
                    return;
                }

                float difference  = event.getY() - _coordinateStartY;
                float destination = _vContainerSelected.getY() + difference;
                if (difference >= 0 || destination < _upperBorder) {
                    break;
                }

                switch (_vContainerSelectedPosition) {
                    case 1:
                        break;

                    case 2:
                        if (isHigher(_vContainerSelected, _vContainer1)) {


                        }
                        break;

                    default:
                        break;
                }

                _vContainerSelected.setY(destination);
                break;

            default:
                break;
        }
    }

    private boolean isHigher(View active, View passive) {

        return active.getY() < ((passive.getTop() + passive.getBottom()) / 2f);
    }

}
