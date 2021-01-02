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


public class KeyboardFragment extends Fragment {

    private MainActivity _activityContext;


    public KeyboardFragment(AppCompatActivity context) {

        super(R.layout.keyboard);
        _activityContext = (MainActivity) context;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//
//        super.onViewCreated(view, savedInstanceState);
//
//        Button _bDot       = view.findViewById(R.id.kbDot);
//        Button _bEraseAll  = view.findViewById(R.id.kbEraseAll);
//        Button _bEraseLast = view.findViewById(R.id.kbEraseLast);
//
//
//        //region Events
//        _bDot.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (_activityContext.getActiveInput().getText().toString().isEmpty()) {
//                    _activityContext.getActiveInput().append("0.");
//                }
//                else {
//                    _activityContext.getActiveInput().append(".");
//                }
//            }
//        });
//
//        _bEraseLast.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                EditText active = _activityContext.getActiveInput();
//
//                if (active.getText().length() == 1) {
//                    active.setText("");
//                }
//                else {
//                    active.setText(
//                        active.getText()
//                              .toString()
//                              .substring(0, active.length() - 1)
//                    );
//                }
//            }
//        });
//
//        _bEraseAll.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                _activityContext.getActiveInput().setText("");
//            }
//        });
//        //endregion
//    }

}
