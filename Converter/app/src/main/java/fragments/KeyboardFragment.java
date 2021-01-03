package fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.converter.MainActivity;
import com.converter.R;

import org.javatuples.Quartet;


public class KeyboardFragment extends Fragment {

    private AppCompatActivity _activityContext;


    public KeyboardFragment(AppCompatActivity context) {

        super(R.layout.keyboard);
        _activityContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button _bDot       = view.findViewById(R.id.kbDot);
        Button _bEraseAll  = view.findViewById(R.id.kbEraseAll);
        Button _bEraseLast = view.findViewById(R.id.kbEraseLast);


        //region Events
        _bDot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText active = ((MainActivity) _activityContext).getTextFields().getValue3();

                if (active.getText().toString().isEmpty()) {
                    active.append("0.");
                }
                else if (!active.getText().toString().contains(".")) {
                    active.append(".");
                }
            }
        });

        _bEraseLast.setOnClickListener(new View.OnClickListener() {

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
            }
        });

        _bEraseAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Quartet<EditText, EditText, EditText, EditText> fields =
                    ((MainActivity) _activityContext).getTextFields();

                fields.getValue0().setText("");
                fields.getValue1().setText("");
                fields.getValue2().setText("");
            }
        });
        //endregion
    }

}
