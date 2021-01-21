package com.tabatatimer.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.RecyclerViewAdapterAbstract;
import com.tabatatimer.managers.IRecyclerViewItemManager;
import com.tabatatimer.managers.ICrudButtonsManager;



public class DeleteDialogFragment extends DialogFragment {

    private IRecyclerViewItemManager                                       mItemManager;
    private ICrudButtonsManager                                            mCrudButtonsManager;
    private RecyclerViewAdapterAbstract<? extends RecyclerView.ViewHolder> mAdapter;


    public DeleteDialogFragment(IRecyclerViewItemManager sequenceHandler,
                                ICrudButtonsManager crudButtonsHandler,
                                RecyclerViewAdapterAbstract<? extends RecyclerView.ViewHolder> adapter) {

        mItemManager        = sequenceHandler;
        mCrudButtonsManager = crudButtonsHandler;
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
                       mAdapter.deleteItem(mItemManager.getSelectedIndex());

                       mAdapter.notifyItemRemoved(mItemManager.getSelectedIndex());
                       mAdapter.notifyDataSetChanged();

                       mItemManager.setSelectedIndex(mItemManager.NO_SELECTED);
                       mCrudButtonsManager.toggleCrudButtonsVisibility();

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
