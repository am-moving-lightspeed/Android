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



public class EditSequenceStageDialogFragment extends DialogFragment {

    public interface EditDialogButtonClickListener {

        void onPositiveClick(EditSequenceStageDialogFragment dialogFragment);

    }



    private EditDialogButtonClickListener mClickListener;

    private View     mSelectedView;
    private TextView mBOk;
    private EditText mEtHeader;
    private EditText mEtDescription;
    private EditText mEtMinutes;
    private EditText mEtSeconds;

    private boolean areMinutesValid = true;
    private boolean areSecondsValid = true;


    public EditSequenceStageDialogFragment(Fragment parentFragment,
                                           View selectedView) {

        mClickListener = (EditDialogButtonClickListener) parentFragment;
        mSelectedView  = selectedView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
        LayoutInflater      inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.fragment_sequence_stage_edit, null);

        mEtHeader      = view.findViewById(R.id.editText_sequenceStageEdit_header);
        mEtDescription = view.findViewById(R.id.editText_sequenceStageEdit_description);
        mEtMinutes     = view.findViewById(R.id.editText_sequenceStageEdit_minutes);
        mEtSeconds     = view.findViewById(R.id.editText_sequenceStageEdit_seconds);
        mBOk           = view.findViewById(R.id.textView_sequenceStageEdit_buttonOk);

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
        time[0] = ((TextView) mSelectedView.findViewById(R.id.textView_sequenceStage_time)).getText()
                                                                                           .toString();
        time    = time[0].split(":");

        mEtMinutes.setText(time[0]);
        mEtSeconds.setText(time[1]);

        EditText destination = view.findViewById(R.id.editText_sequenceStageEdit_header);
        TextView source      = mSelectedView.findViewById(R.id.textView_sequenceStage_header);
        destination.setText(source.getText());

        destination = view.findViewById(R.id.editText_sequenceStageEdit_description);
        source      = mSelectedView.findViewById(R.id.textView_sequenceStage_description);
        destination.setText(source.getText());
    }


    // region Events
    private void setButtonOkEvent(View view) {

        mBOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mClickListener != null) {
                    mClickListener.onPositiveClick(EditSequenceStageDialogFragment.this);
                }
                EditSequenceStageDialogFragment.this.dismiss();
            }
        });
    }


    private void setButtonCancelEvent(View view) {

        view.findViewById(R.id.textView_sequenceStageEdit_buttonCancel)
            .setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    EditSequenceStageDialogFragment.this.dismiss();
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
                                R.drawable.item_content_edittext,
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
                            R.drawable.item_content_edittext_error,
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
                                R.drawable.item_content_edittext,
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
                            R.drawable.item_content_edittext_error,
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
                R.color.colour_gunmetal,
                getActivity().getTheme()
            )
        );
    }


    private void enableButtonOk() {

        mBOk.setClickable(true);
        mBOk.setTextColor(
            ResourcesCompat.getColor(
                getResources(),
                R.color.colour_cornflowerBlue,
                getActivity().getTheme()
            )
        );
    }

}
