package fragments;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    // Event handler
    protected TextWatcher          _textWatcher;
    protected View.OnTouchListener _etOnTouchListener;


    public CategoryFragment(@LayoutRes int contentLayoutId, AppCompatActivity activity) {

        super(contentLayoutId);
        _layoutId        = contentLayoutId;
        _activityContext = activity;
        _textWatcher     = provideTextChangedListener();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        _etOutput0     = view.findViewById(R.id.inputField0);
        _etOutput1     = view.findViewById(R.id.inputField1);
        _etOutput2     = view.findViewById(R.id.inputField2);
        _etActiveInput = _etOutput0;

        provideTextFields();

        //region Events
        _etActiveInput.addTextChangedListener(_textWatcher);
        _etOutput0.setOnClickListener(provideEditTextClickHandler());
        _etOutput1.setOnClickListener(provideEditTextClickHandler());
        _etOutput2.setOnClickListener(provideEditTextClickHandler());
        _etOutput0.setOnLongClickListener(provideEditTextLongClickHandler(_etOutput0));
        _etOutput1.setOnLongClickListener(provideEditTextLongClickHandler(_etOutput1));
        _etOutput2.setOnLongClickListener(provideEditTextLongClickHandler(_etOutput2));

        View unitRow0 = view.findViewById(R.id.unitRow0);
        unitRow0.setOnTouchListener(new UnitOnTouchListener(unitRow0, view, this));

        View unitRow1 = view.findViewById(R.id.unitRow1);
        unitRow1.setOnTouchListener(new UnitOnTouchListener(unitRow1, view, this));

        View unitRow2 = view.findViewById(R.id.unitRow2);
        unitRow2.setOnTouchListener(new UnitOnTouchListener(unitRow2, view, this));
        //endregion
    }


    public Quartet<EditText, EditText, EditText, EditText> getTextFields() {

        return new Quartet<>(_etOutput0, _etOutput1, _etOutput2, _etActiveInput);
    }


    public void resetActiveInput(View newActive) {

        _etActiveInput.removeTextChangedListener(_textWatcher);
        _etActiveInput = (EditText) newActive;
        _etActiveInput.addTextChangedListener(_textWatcher);
        provideTextFields();
    }


    /*
     * Provide variables for AppCompatActivity with those,
     * which have been initialized on this fragment's view creation.
     */
    private void provideTextFields() {

        if (((MainActivity) _activityContext).getCurrentFragmentLayout() == _layoutId) {

            ((MainActivity) _activityContext).setTextFields(
                new Quartet<>(_etOutput0,
                              _etOutput1,
                              _etOutput2,
                              _etActiveInput)
            );
        }
    }


    //region "Edit text clicked" event handlers
    private View.OnClickListener provideEditTextClickHandler() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {}
        };
    }


    private View.OnLongClickListener provideEditTextLongClickHandler(final View view) {

        return new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ClipboardManager manager =
                    (ClipboardManager) _activityContext.getSystemService(Context.CLIPBOARD_SERVICE);

                manager.setPrimaryClip(
                    ClipData.newPlainText(
                        "Converter",
                        ((EditText) view).getText()
                    )
                );

                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();

                return true;
            }
        };
    }
    //endregion


    //region "Text changed" event handlers
    private TextWatcher provideTextChangedListener() {

        return new TextWatcher() {

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

                if ((buffer.length() == 2)     &&
                    (buffer.charAt(0) == '0')  &&
                    (buffer.charAt(1) == '0')) {

                    _etActiveInput.setText("0");
                }

                if ((buffer.length() == 2)     &&
                    (buffer.charAt(0) == '0')) {

                    _etActiveInput.setText(String.valueOf(buffer.charAt(1)));
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
        };
    }


    @NonNull
    private String applyAccuracy(String string) {

        int count = 0;
        for (int i = string.length() - 1; i > 0; i--) {
            if (string.charAt(i) == '0') {
                count++;
            }
            else if (string.charAt(i) == '.') {
                count++;
                break;
            }
            else {
                break;
            }
        }

        return string.substring(0, string.length() - count);
    }


    private void weightChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value / 1000.0))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value / 1000.0 / 1000.0))
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 1000.0))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value / 1000.0))
            );
        }
        else {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 1000.0 * 1000.0))
            );
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 1000.0))
            );
        }
    }


    private void distanceChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value / 1000.0))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 0.000621371))
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 1000.0))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value / 0.621371))
            );
        }
        else {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 0.000621371))
            );
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.5f", value * 0.621371))
            );
        }
    }


    private void currencyChanged(double value) {

        if (_etActiveInput.equals(_etOutput0)) {
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 28.63))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 0.38))
            );
        }
        else if (_etActiveInput.equals(_etOutput1)) {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 0.03))
            );
            _etOutput2.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 0.01))
            );
        }
        else {
            _etOutput0.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 2.63))
            );
            _etOutput1.setText(
                applyAccuracy(String.format(Locale.US, "%.2f", value * 75.36))
            );
        }
    }
    //endregion

}
