package fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.converter.MainActivity;
import com.converter.R;

import org.javatuples.Quartet;

import java.util.Locale;

import services.UnitOnTouchListener;


public class CategoryFragment extends Fragment {

    protected EditText _etOutput0;
    protected EditText _etOutput1;
    protected EditText _etOutput2;
    protected EditText _etActiveInput;

    protected int               _layoutId;
    protected AppCompatActivity _activityContext;


    public CategoryFragment(@LayoutRes int contentLayoutId, AppCompatActivity activity) {

        super(contentLayoutId);
        _layoutId = contentLayoutId;
        _activityContext = activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        _etOutput0     = view.findViewById(R.id.inputField0);
        _etOutput1     = view.findViewById(R.id.inputField1);
        _etOutput2     = view.findViewById(R.id.inputField2);
        _etActiveInput = _etOutput0;

        if (((MainActivity) _activityContext).getCurrentFragmentLayout() == _layoutId) {

            ((MainActivity) _activityContext).setTextFields(
                new Quartet<>(_etOutput0,
                              _etOutput1,
                              _etOutput2,
                              _etActiveInput)
            );
        }


        //region Events
        _etActiveInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    return;
                }

                String buffer = s.toString();
                double value;

                if (buffer.charAt(buffer.length() - 1) == '.') {
                    buffer += "0";
                }

                value = Double.parseDouble(buffer);

                if (_layoutId == R.layout.category_slide_weight) {
                    weightChanged(value);
                }
                else if (_layoutId == R.layout.category_slide_distance) {
                    distanceChanged(value);
                }
                else if (_layoutId == R.layout.category_slide_currency) {
                    currencyChanged(value);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        View unitRow0 = view.findViewById(R.id.unitRow0);
        unitRow0.setOnTouchListener(new UnitOnTouchListener(unitRow0, view));

        View unitRow1 = view.findViewById(R.id.unitRow1);
        unitRow0.setOnTouchListener(new UnitOnTouchListener(unitRow1, view));

        View unitRow2 = view.findViewById(R.id.unitRow2);
        unitRow0.setOnTouchListener(new UnitOnTouchListener(unitRow2, view));
        //endregion
    }

    public Quartet<EditText, EditText, EditText, EditText> getTextFields() {

        return new Quartet<>(_etOutput0, _etOutput1, _etOutput2, _etActiveInput);
    }

    //region TextChanged event handlers
    public void weightChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                String.format(Locale.US, "%f", value / 1000.0)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value / 1000.0 / 1000.0)
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 1000.0)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value / 1000.0)
            );
        }
        else {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 1000.0 * 1000.0)
            );
            _etOutput1.setText(
                String.format(Locale.US, "%f", value * 1000.0)
            );
        }
    }

    public void distanceChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                String.format(Locale.US, "%f", value / 1000.0)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value * 0.000621371)
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 1000.0)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value / 0.621371)
            );
        }
        else {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 0.000621371)
            );
            _etOutput1.setText(
                String.format(Locale.US, "%f", value * 0.621371)
            );
        }
    }

    public void currencyChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                String.format(Locale.US, "%f", value * 28.63)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value * 0.38)
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 0.03)
            );
            _etOutput2.setText(
                String.format(Locale.US, "%f", value * 0.01)
            );
        }
        else {
            _etOutput0.setText(
                String.format(Locale.US, "%f", value * 2.63)
            );
            _etOutput1.setText(
                String.format(Locale.US, "%f", value * 75.36)
            );
        }
    }
    //endregion

}
