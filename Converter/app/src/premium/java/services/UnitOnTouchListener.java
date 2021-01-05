package services;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.converter.R;

import fragments.CategoryFragment;


public class UnitOnTouchListener implements View.OnTouchListener {

    //region Variables
    private Fragment _context;

    private float _coordinateStartY0;
    private float _coordinateStartY1;
    private float _coordinateStartY2;

    private View _vContainerSelected;
    private View _vContainer0;
    private View _vContainer1;
    private View _etActiveInput;

    private int _ContainerSelectedPosition;

    // To make sure #setTops method executes once.
    private boolean areTopsDefined = false;
    //endregion


    public UnitOnTouchListener(View view, @NonNull View parent, Fragment context) {

        _vContainerSelected = view;
        _context            = context;
        View tempView0      = parent.findViewById(R.id.unitRow0);
        View tempView1      = parent.findViewById(R.id.unitRow1);
        View tempView2      = parent.findViewById(R.id.unitRow2);

        distributeByValue(tempView0, tempView1, tempView2);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        distributeByAltitude();

        if (!areTopsDefined) {
            setTops();
            areTopsDefined = true;
        }

        handleMovingEvent(event);

        ((CategoryFragment) _context).resetActiveInput(_etActiveInput);
        ((EditText) _etActiveInput).setText(((EditText) _etActiveInput).getText());

        view.performClick();
        return true;
    }


    /*
     * _vContainerSelected is the object that has caused this event,
     * _vContainer0 and _vContainer1 are its neighbours.
     */
    private void distributeByValue(View view0, View view1, View view2) {

        if (_vContainerSelected.equals(view0)) {
            _vContainer0   = view1;
            _vContainer1   = view2;
            _etActiveInput = view0.findViewById(R.id.inputField0);
        }
        else if (_vContainerSelected.equals(view1)) {
            _vContainer0   = view0;
            _vContainer1   = view2;
            _etActiveInput = view1.findViewById(R.id.inputField1);
        }
        else if (_vContainerSelected.equals(view2)) {
            _vContainer0   = view0;
            _vContainer1   = view1;
            _etActiveInput = view2.findViewById(R.id.inputField2);
        }
    }


    /*
     * Call this method to rearrange objects, ordering by
     * their Y coordinate, so _vContainer0's Y coordinate is always
     * lower than _vContainer1's.
     */
    private void distributeByAltitude() {

        if (_vContainer0.getY() > _vContainer1.getY()) {
            View temp = _vContainer0;
            _vContainer0 = _vContainer1;
            _vContainer1 = temp;
        }

        if (_vContainerSelected.getY() < _vContainer0.getY()) {
            _ContainerSelectedPosition = 0;
        }
        else if (_vContainerSelected.getY() < _vContainer1.getY()) {
            _ContainerSelectedPosition = 1;
        }
        else {
            _ContainerSelectedPosition = 2;
        }
    }


    /*
     * Remember Y-start position for every container.
     */
    private void setTops() {

        switch (_ContainerSelectedPosition) {
            case 0:
                _coordinateStartY0 = _vContainerSelected.getY();
                _coordinateStartY1 = _vContainer0.getY();
                _coordinateStartY2 = _vContainer1.getY();
                break;

            case 1:
                _coordinateStartY0 = _vContainer0.getY();
                _coordinateStartY1 = _vContainerSelected.getY();
                _coordinateStartY2 = _vContainer1.getY();
                break;
            case 2:
                _coordinateStartY0 = _vContainer0.getY();
                _coordinateStartY1 = _vContainer1.getY();
                _coordinateStartY2 = _vContainerSelected.getY();
                break;
        }
    }


    private void handleMovingEvent(@NonNull MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            switch (_ContainerSelectedPosition) {
                case 1:
                    performPositionExchange();
                    break;

                case 2:
                    performPositionDoubleExchange();
                    break;

                case 0:
                default:
                    break;
            }
        }
    }


    //region Animations
    private void performPositionExchange() {

        ObjectAnimator animator0 = ObjectAnimator.ofFloat(_vContainerSelected,
                                                          "y",
                                                          _coordinateStartY0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(_vContainer0,
                                                          "y",
                                                          _coordinateStartY1);
        animator0.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator0).with(animator1);
        animatorSet.start();
    }


    private void performPositionDoubleExchange() {

        ObjectAnimator animator0_ = ObjectAnimator.ofFloat(_vContainerSelected,
                                                           "y",
                                                           _coordinateStartY0);
        ObjectAnimator animator1_ = ObjectAnimator.ofFloat(_vContainer0,
                                                           "y",
                                                           _coordinateStartY1);
        ObjectAnimator animator2_ = ObjectAnimator.ofFloat(_vContainer1,
                                                           "y",
                                                           _coordinateStartY2);
        animator0_.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1_.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2_.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet_ = new AnimatorSet();
        animatorSet_.play(animator0_)
                    .with(animator1_)
                    .with(animator2_);
        animatorSet_.start();
    }
    //endregion

}
