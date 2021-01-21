package com.tabatatimer.ui.library.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tabatatimer.R;
import com.tabatatimer.adapters.RecyclerViewAdapterAbstract;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.managers.IRecyclerViewItemManager;
import com.tabatatimer.misc.SequenceInfoStructure;
import com.tabatatimer.ui.library.viewholders.SequenceViewHolder;



public class LibraryRecyclerViewAdapter extends RecyclerViewAdapterAbstract<SequenceViewHolder> {

    private SequenceInfoStructure[] mSequencesData;

    private IRecyclerViewItemManager mItemManager;
    private ICrudButtonsManager      mCrudButtonsManager;

    private Context mContext;


    public LibraryRecyclerViewAdapter(SequenceInfoStructure[] data,
                                      Context context) {

        mSequencesData = data;
        mContext       = context;
    }


    public void setItemManager(IRecyclerViewItemManager itemManager) {

        mItemManager = itemManager;
        mItemManager.updateCollectionLength(mSequencesData.length);
    }


    public void setCrudButtonsManager(ICrudButtonsManager crudButtonsManager) {

        mCrudButtonsManager = crudButtonsManager;
    }


    @NonNull
    @Override
    public SequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SequenceViewHolder(
            LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.item_sequence, parent, false),
            mContext,
            this
        );
    }


    @Override
    public void onBindViewHolder(@NonNull SequenceViewHolder holder, int position) {

        holder.setHeader(mSequencesData[position].header)
              .setDescription(mSequencesData[position].description)
              .setTotalTime(mSequencesData[position].totalTimeInfo)
              .setStagesAmount(mSequencesData[position].phasesAmountInfo);
    }


    @Override
    public int getItemCount() {

        return mSequencesData.length;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull SequenceViewHolder holder) {

        super.onViewAttachedToWindow(holder);

        int position = holder.getAdapterPosition();

        if (mItemManager.getSelectedIndex() == position) {
            mItemManager.applyStyleSelected(R.id.linearLayout_sequence_background,
                                            R.id.textView_sequence_description);
        }
        else {
            mItemManager.applyStyleDefault(position,
                                           R.id.linearLayout_sequence_background,
                                           R.id.textView_sequence_description);
        }
    }


    @Override
    public void deleteItem(int position) {

        // TODO: implement db usage
        SequenceInfoStructure[] old = mSequencesData;
        mSequencesData = new SequenceInfoStructure[old.length - 1];


        System.arraycopy(old, 0, mSequencesData, 0, position);
        System.arraycopy(old, position + 1, mSequencesData, position, old.length - (position + 1));

        mItemManager.updateCollectionLength(mSequencesData.length);
    }


    @Override
    public void resolveItemClickEvent(int position) {

        if (position != mItemManager.getSelectedIndex()) {
            if (mCrudButtonsManager.areCrudButtonsHidden()) {
                mCrudButtonsManager.toggleCrudButtonsVisibility();
            }

            mItemManager.cancelStyleSelected(R.id.linearLayout_sequence_background,
                                             R.id.textView_sequence_description);
            mItemManager.setSelectedIndex(position);
            mItemManager.applyStyleSelected(R.id.linearLayout_sequence_background,
                                            R.id.textView_sequence_description);
        }
        else {
            mItemManager.cancelStyleSelected(R.id.linearLayout_sequence_background,
                                             R.id.textView_sequence_description);
            mItemManager.setSelectedIndex(mItemManager.NO_SELECTED);
            mCrudButtonsManager.toggleCrudButtonsVisibility();
        }
    }

}
