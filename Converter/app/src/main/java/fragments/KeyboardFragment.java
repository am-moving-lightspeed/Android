package fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.converter.MainActivity;
import com.converter.R;

import org.javatuples.Quartet;


public class KeyboardFragment extends Fragment {

    private AppCompatActivity _activityContext;
    private Vibrator          _vibrator;


    public KeyboardFragment(AppCompatActivity context) {

        super(R.layout.keyboard);
        _activityContext = context;
        _vibrator        = (Vibrator) _activityContext.getSystemService(Context.VIBRATOR_SERVICE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button bDot = view.findViewById(R.id.kbDot);
        Button bEraseAll = view.findViewById(R.id.kbEraseAll);
        Button bEraseLast = view.findViewById(R.id.kbEraseLast);


        //region Events
        bDot.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {

                EditText active = ((MainActivity) _activityContext).getTextFields().getValue3();

                if (active.getText().toString().isEmpty()) {
                    active.append("0.");
                }
                else if (!active.getText().toString().contains(".")) {
                    active.append(".");
                }

                _vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        });

        bEraseLast.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                Quartet<EditText, EditText, EditText, EditText> fields =
                    ((MainActivity) _activityContext).getTextFields();
                EditText active = fields.getValue3();

                if (active.getText().length() <= 1) {
                    fields.getValue0().setText("");
                    fields.getValue1().setText("");
                    fields.getValue2().setText("");
                }
                else {
                    active.setText(
                        active.getText()
                              .toString()
                              .substring(0, active.length() - 1)
                    );
                }

                _vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        });

        bEraseAll.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                Quartet<EditText, EditText, EditText, EditText> fields =
                    ((MainActivity) _activityContext).getTextFields();

                fields.getValue0().setText("");
                fields.getValue1().setText("");
                fields.getValue2().setText("");

                _vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        });
        //endregion
    }

}
