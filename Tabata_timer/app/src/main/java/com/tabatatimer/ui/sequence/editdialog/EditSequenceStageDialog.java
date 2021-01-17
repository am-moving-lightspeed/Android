package com.tabatatimer.ui.sequence.editdialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.tabatatimer.R;



public class EditSequenceStageDialog extends DialogFragment {

    public interface DialogButtonClickListener {

        void onDialogPositiveClick(EditSequenceStageDialog dialogFragment);

    }



    private DialogButtonClickListener mClickListener;

    private View     mSelectedView;
    private TextView mBOk;
    private EditText mEtHeader;
    private EditText mEtDescription;
    private EditText mEtMinutes;
    private EditText mEtSeconds;

    private boolean areMinutesValid = true;
    private boolean areSecondsValid = true;


    public EditSequenceStageDialog(Fragment parentFragment,
                                   View selectedView) {

        mClickListener = (DialogButtonClickListener) parentFragment;
        mSelectedView  = selectedView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
        LayoutInflater      inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.fragment_sequence_stage_edit, null);

        mEtHeader      = view.findViewById(R.id.sequenceStageEditHeader);
        mEtDescription = view.findViewById(R.id.sequenceStageEditDescription);
        mEtMinutes     = view.findViewById(R.id.sequenceStageEditMinutes);
        mEtSeconds     = view.findViewById(R.id.sequenceStageEditSeconds);
        mBOk           = view.findViewById(R.id.abEditDialogOk);

        fillDialogView(view);

        builder.setView(view);

        setButtonOkEvent(view);
        setButtonCancelEvent(view);
        setEditTextMinutesEvent();
        setEditTextSecondsEvent();

        return builder.create();
    }


    public String getHeaderText() {

        return mEtHeader.getText()
                        .toString();
    }


    public String getDescriptionText() {

        return mEtDescription.getText()
                             .toString();
    }


    public String getMinutesText() {

        return mEtMinutes.getText()
                         .toString();
    }


    public String getSecondsText() {

        return mEtSeconds.getText()
                         .toString();
    }


    private void fillDialogView(View view) {

        String[] time = new String[2];
        time[0] = ((TextView) mSelectedView.findViewById(R.id.sequenceStageTime)).getText()
                                                                                 .toString();
        time    = time[0].split(":");

        mEtMinutes.setText(time[0]);
        mEtSeconds.setText(time[1]);

        EditText destination = view.findViewById(R.id.sequenceStageEditHeader);
        TextView source      = mSelectedView.findViewById(R.id.sequenceStageHeader);
        destination.setText(source.getText());

        destination = view.findViewById(R.id.sequenceStageEditDescription);
        source      = mSelectedView.findViewById(R.id.sequenceStageDescription);
        destination.setText(source.getText());
    }


    // region Events
    private void setButtonOkEvent(View view) {

        mBOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mClickListener != null) {
                    mClickListener.onDialogPositiveClick(EditSequenceStageDialog.this);
                }
                EditSequenceStageDialog.this.dismiss();
            }
        });
    }


    private void setButtonCancelEvent(View view) {

        view.findViewById(R.id.abEditDialogCancel)
            .setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    EditSequenceStageDialog.this.dismiss();
                }
            });
    }


    private void setEditTextMinutesEvent() {

        mEtMinutes.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void afterTextChanged(Editable s) {}


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                char[] string = s.toString()
                                 .toCharArray();

                if (isTextValid(string)) {
                    if (!areMinutesValid) {
                        mEtMinutes.setBackground(
                            ResourcesCompat.getDrawable(
                                getResources(),
                                R.drawable.shapes_sequence_stage_edit_edittext,
                                getActivity().getTheme()
                            )
                        );
                        areMinutesValid = true;
                        if (areSecondsValid) {
                            enableButtonOk();
                        }
                    }
                }
                else {
                    disableButtonOk();
                    mEtMinutes.setBackground(
                        ResourcesCompat.getDrawable(
                            getResources(),
                            R.drawable.shapes_sequence_stage_edit_edittext_incorrect,
                            getActivity().getTheme()
                        )
                    );
                    areMinutesValid = false;
                }
            }
        });
    }


    private void setEditTextSecondsEvent() {

        mEtSeconds.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void afterTextChanged(Editable s) {}


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                char[] string = s.toString()
                                 .toCharArray();

                if (isTextValid(string)) {
                    if (!areSecondsValid) {
                        mEtSeconds.setBackground(
                            ResourcesCompat.getDrawable(
                                getResources(),
                                R.drawable.shapes_sequence_stage_edit_edittext,
                                getActivity().getTheme()
                            )
                        );
                        areSecondsValid = true;
                        if (areMinutesValid) {
                            enableButtonOk();
                        }
                    }
                }
                else {
                    disableButtonOk();
                    mEtSeconds.setBackground(
                        ResourcesCompat.getDrawable(
                            getResources(),
                            R.drawable.shapes_sequence_stage_edit_edittext_incorrect,
                            getActivity().getTheme()
                        )
                    );
                    areSecondsValid = false;
                }
            }
        });
    }
    // endregion


    private boolean isTextValid(char[] string) {

        if (string.length == 0) {
            string    = new char[2];
            string[0] = string[1] = '0';
        }
        else if (string.length == 1) {
            string = new char[] {'0', string[0]};
        }

        if ((string[0] < '0' || string[0] > '9') &&
            (string[1] < '1' || string[1] > '9')) {

            return false;
        }
        else { return string[0] < '6'; }
    }


    private void disableButtonOk() {

        mBOk.setClickable(false);
        mBOk.setTextColor(
            ResourcesCompat.getColor(
                getResources(),
                R.color.colorStroke,
                getActivity().getTheme()
            )
        );
    }


    private void enableButtonOk() {

        mBOk.setClickable(true);
        mBOk.setTextColor(
            ResourcesCompat.getColor(
                getResources(),
                R.color.colorBlue,
                getActivity().getTheme()
            )
        );
    }

}
