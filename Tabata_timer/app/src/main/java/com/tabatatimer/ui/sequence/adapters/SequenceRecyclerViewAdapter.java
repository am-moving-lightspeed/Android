package com.tabatatimer.ui.sequence.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.handlers.ICrudButtonsHandler;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.ui.sequence.handlers.SequenceHandlerAbstract;
import com.tabatatimer.viewholders.SequenceStageViewHolder;



public class SequenceRecyclerViewAdapter extends RecyclerView.Adapter<SequenceStageViewHolder> {

    private SequenceStageInfoStructure[] mSequenceStagesData;

    private ICrudButtonsHandler     mCrudButtonsHandler;
    private SequenceHandlerAbstract mSequenceHandler;


    public SequenceRecyclerViewAdapter(SequenceStageInfoStructure[] data) {

        mSequenceStagesData = data;
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

        if (mSequenceHandler.getActiveIndex() == position) {
            mSequenceHandler.applyStyleActive();
        }
        else if (mSequenceHandler.getSelectedIndex() == position) {
            mSequenceHandler.applyStyleSelected();
        }
        else {
            mSequenceHandler.applyStyleDefault(position);
        }
    }


    public void setSequenceHandler(SequenceHandlerAbstract handler) {

        mSequenceHandler = handler;
        mSequenceHandler.updateCollectionLength(mSequenceStagesData.length);
    }


    public void setCrudButtonsHandler(ICrudButtonsHandler handler) {

        mCrudButtonsHandler = handler;
    }


    public void resolveViewHolderClickEvent(int position) {

        if (position != mSequenceHandler.getActiveIndex()) {

            if (position != mSequenceHandler.getSelectedIndex()) {
                if (mCrudButtonsHandler.areCrudButtonsHidden()) {
                    mCrudButtonsHandler.toggleCrudButtonsVisibility();
                }

                mSequenceHandler.cancelStyleSelected();
                mSequenceHandler.setSelectedIndex(position);
                mSequenceHandler.applyStyleSelected();
            }
            else {
                mSequenceHandler.cancelStyleSelected();
                mSequenceHandler.setSelectedIndex(mSequenceHandler.NO_SELECTED);
                mCrudButtonsHandler.toggleCrudButtonsVisibility();
            }
        }

    }


    public void deleteItem(int position) {

        SequenceStageInfoStructure[] old = mSequenceStagesData;
        mSequenceStagesData = new SequenceStageInfoStructure[old.length - 1];


        System.arraycopy(old, 0, mSequenceStagesData, 0, position);
        System.arraycopy(old, position + 1, mSequenceStagesData, position, old.length - (position + 1));

        if (position < mSequenceHandler.getActiveIndex()) {
            mSequenceHandler.setActiveIndex(
                mSequenceHandler.getActiveIndex() - 1
            );
        }

        mSequenceHandler.updateCollectionLength(mSequenceStagesData.length);
    }

}
