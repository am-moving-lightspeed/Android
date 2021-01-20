package com.tabatatimer.ui.sequence.deletedialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.tabatatimer.R;



public class DeleteSequenceStageDialogFragment extends DialogFragment {

    public interface DeleteDialogButtonClickListener {

        void onPositiveClick(DeleteSequenceStageDialogFragment dialogFragment);

    }



    private DeleteDialogButtonClickListener clickListener;


    public DeleteSequenceStageDialogFragment(Fragment parentFragment) {

        clickListener = (DeleteDialogButtonClickListener) parentFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure?")
               .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       clickListener.onPositiveClick(DeleteSequenceStageDialogFragment.this);
                       dismiss();
                   }
               })
               .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       dismiss();
                   }
               });

        return builder.create();
    }

}
