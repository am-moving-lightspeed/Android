package com.tabatatimer.ui.sequence.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tabatatimer.R;
import com.tabatatimer.ui.sequence.adapters.SequenceRecyclerViewAdapter;
import com.tabatatimer.handlers.ICrudButtonsHandler;
import com.tabatatimer.ui.sequence.handlers.SequenceHandlerAbstract;



public class DeleteSequenceStageDialogFragment extends DialogFragment {

    private SequenceHandlerAbstract     mSequenceHandler;
    private ICrudButtonsHandler         mCrudButtonsHandler;
    private SequenceRecyclerViewAdapter mAdapter;


    public DeleteSequenceStageDialogFragment(SequenceHandlerAbstract sequenceHandler,
                                             ICrudButtonsHandler crudButtonsHandler,
                                             SequenceRecyclerViewAdapter adapter) {

        mSequenceHandler    = sequenceHandler;
        mCrudButtonsHandler = crudButtonsHandler;
        mAdapter            = adapter;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure?")
               .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       // TODO: implement database usage
                       mAdapter.deleteItem(mSequenceHandler.getSelectedIndex());

                       mAdapter.notifyItemRemoved(mSequenceHandler.getSelectedIndex());
                       mAdapter.notifyDataSetChanged();

                       mSequenceHandler.setSelectedIndex(mSequenceHandler.NO_SELECTED);
                       mCrudButtonsHandler.toggleCrudButtonsVisibility();

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
