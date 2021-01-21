package com.tabatatimer.ui.sequence.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tabatatimer.R;
import com.tabatatimer.adapters.RecyclerViewAdapterAbstract;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.ui.sequence.managers.ISequenceRecyclerViewManager;
import com.tabatatimer.ui.sequence.viewholders.SequenceStageViewHolder;



public class SequenceRecyclerViewAdapter extends RecyclerViewAdapterAbstract<SequenceStageViewHolder> {

    private SequenceStageInfoStructure[] mSequenceStagesData;

    private ICrudButtonsManager          mCrudButtonsManager;
    private ISequenceRecyclerViewManager mSequenceManager;


    public SequenceRecyclerViewAdapter(SequenceStageInfoStructure[] data) {

        mSequenceStagesData = data;
    }


    public void setItemManager(ISequenceRecyclerViewManager manager) {

        mSequenceManager = manager;
        mSequenceManager.updateCollectionLength(mSequenceStagesData.length);
    }


    public void setCrudButtonsManager(ICrudButtonsManager manager) {

        mCrudButtonsManager = manager;
    }


    @NonNull
    @Override
    public SequenceStageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SequenceStageViewHolder(
            LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.item_sequence_stage, parent, false),
            this
        );
    }


    @Override
    public void onBindViewHolder(@NonNull SequenceStageViewHolder holder, int position) {

        holder.setHeader(mSequenceStagesData[position].header)
              .setDescription(mSequenceStagesData[position].description)
              .setTime(mSequenceStagesData[position].time)
              .setTimeLeft(mSequenceStagesData[position].timeLeft);
    }


    @Override
    public int getItemCount() {

        return mSequenceStagesData.length;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull SequenceStageViewHolder holder) {

        super.onViewAttachedToWindow(holder);

        int position = holder.getAdapterPosition();

        if (mSequenceManager.getActiveIndex() == position) {
            mSequenceManager.applyStyleActive();
        }
        else if (mSequenceManager.getSelectedIndex() == position) {
            mSequenceManager.applyStyleSelected(R.id.linearLayout_sequenceStage_background,
                                                R.id.textView_sequenceStage_description);
        }
        else {
            mSequenceManager.applyStyleDefault(position,
                                               R.id.linearLayout_sequenceStage_background,
                                               R.id.textView_sequenceStage_description,
                                               R.id.textView_sequenceStage_timeLeft,
                                               R.id.progressBar_sequenceStage_progressBar);
        }
    }


    @Override
    public void deleteItem(int position) {

        SequenceStageInfoStructure[] old = mSequenceStagesData;
        mSequenceStagesData = new SequenceStageInfoStructure[old.length - 1];


        System.arraycopy(old, 0, mSequenceStagesData, 0, position);
        System.arraycopy(old, position + 1, mSequenceStagesData, position, old.length - (position + 1));

        if (position < mSequenceManager.getActiveIndex()) {
            mSequenceManager.setActiveIndex(
                mSequenceManager.getActiveIndex() - 1
            );
        }

        mSequenceManager.updateCollectionLength(mSequenceStagesData.length);
    }


    @Override
    public void resolveItemClickEvent(int position) {

        if (position != mSequenceManager.getActiveIndex()) {

            if (position != mSequenceManager.getSelectedIndex()) {
                if (mCrudButtonsManager.areCrudButtonsHidden()) {
                    mCrudButtonsManager.toggleCrudButtonsVisibility();
                }

                mSequenceManager.cancelStyleSelected(R.id.linearLayout_sequenceStage_background,
                                                     R.id.textView_sequenceStage_description);
                mSequenceManager.setSelectedIndex(position);
                mSequenceManager.applyStyleSelected(R.id.linearLayout_sequenceStage_background,
                                                    R.id.textView_sequenceStage_description);
            }
            else {
                mSequenceManager.cancelStyleSelected(R.id.linearLayout_sequenceStage_background,
                                                     R.id.textView_sequenceStage_description);
                mSequenceManager.setSelectedIndex(mSequenceManager.NO_SELECTED);
                mCrudButtonsManager.toggleCrudButtonsVisibility();
            }
        }
    }

}
