package com.tabatatimer.ui.library.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tabatatimer.R;
import com.tabatatimer.managers.IRecyclerViewItemManager;
import com.tabatatimer.misc.SequenceInfoStructure;
import com.tabatatimer.sqlite.DbHelper;

import java.util.Locale;



public class EditLibraryDialogFragment extends DialogFragment {

    private SequenceInfoStructure[] mModels;

    private IRecyclerViewItemManager mItemManager;

    private View     mSelectedView;
    private EditText mEtHeader;
    private EditText mEtDescription;


    public EditLibraryDialogFragment(View selectedView,
                                     IRecyclerViewItemManager manager,
                                     SequenceInfoStructure[] models) {

        mItemManager  = manager;
        mSelectedView = selectedView;
        mModels       = models;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
        LayoutInflater      inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.fragment_sequence_stage_edit, null);

        mEtHeader      = view.findViewById(R.id.editText_sequenceStageEdit_header);
        mEtDescription = view.findViewById(R.id.editText_sequenceStageEdit_description);
        TextView mBOk     = view.findViewById(R.id.textView_sequenceStageEdit_buttonOk);
        TextView mBCancel = view.findViewById(R.id.textView_sequenceStageEdit_buttonCancel);

        fillDialogView(view);

        builder.setView(view);

        setButtonOkEvent(mBOk);
        setButtonCancelEvent(mBCancel);

        return builder.create();
    }


    private void fillDialogView(View view) {

        EditText destination = view.findViewById(R.id.editText_sequenceStageEdit_header);
        TextView source      = mSelectedView.findViewById(R.id.textView_sequenceStage_header);
        destination.setText(source.getText());

        destination = view.findViewById(R.id.editText_sequenceStageEdit_description);
        source      = mSelectedView.findViewById(R.id.textView_sequenceStage_description);
        destination.setText(source.getText());
    }


    // region Events
    private void setButtonOkEvent(View bOk) {

        bOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SQLiteDatabase db = new DbHelper(getContext()).getWritableDatabase();
                view = mItemManager.getSelectedView();

                String header      = mEtHeader.toString();
                String description = mEtDescription.toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DbHelper.HEADER_COLUMN, header);
                contentValues.put(DbHelper.DESCRIPTION_COLUMN, description);

                db.update(DbHelper.TABLE_NAME_SEQUENCE_STAGE,
                          contentValues,
                          String.format(Locale.US,
                                        "%s = %d",
                                        DbHelper.ID_COLUMN,
                                        mModels[mItemManager.getSelectedIndex()].id),
                          null);

                ((TextView) view.findViewById(R.id.textView_sequenceStage_header)).setText(header);
                ((TextView) view.findViewById(R.id.textView_sequenceStage_description)).setText(description);

                db.close();
                EditLibraryDialogFragment.this.dismiss();
            }
        });
    }


    private void setButtonCancelEvent(View bCancel) {

        bCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditLibraryDialogFragment.this.dismiss();
            }
        });
    }
    // endregion

}
