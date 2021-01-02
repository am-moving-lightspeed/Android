package fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.converter.R;
import org.javatuples.Triplet;


public class CategoryFragment extends Fragment {

    protected EditText _etField1;
    protected EditText _etField2;
    protected EditText _etField3;


    public CategoryFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        _etField1 = view.findViewById(R.id.inputField1);
        _etField2 = view.findViewById(R.id.inputField2);
        _etField3 = view.findViewById(R.id.inputField3);
    }

    public Triplet<EditText, EditText, EditText> getTextFields() {

        return new Triplet<>(_etField1, _etField2, _etField3);
    }

}
