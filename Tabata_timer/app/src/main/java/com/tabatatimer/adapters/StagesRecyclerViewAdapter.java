package com.tabatatimer.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.layoutmanagers.StagesRecyclerViewLayoutManager;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.viewholders.SequenceStageViewHolder;



public class StagesRecyclerViewAdapter extends RecyclerView.Adapter<SequenceStageViewHolder> {

    public final int NO_SELECTED = -1;
    public final int NO_ACTIVE   = -1;

    private SequenceStageInfoStructure[] mSequenceStagesData;
    private int                          mActivePosition;
    private int                          mSelectedPosition;

    private StagesRecyclerViewLayoutManager mLayoutManager;


    public StagesRecyclerViewAdapter(SequenceStageInfoStructure[] data) {

        mSequenceStagesData = data;
        mActivePosition     = NO_ACTIVE;
        mSelectedPosition   = NO_SELECTED;
    }


    @NonNull
    @Override
    public SequenceStageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SequenceStageViewHolder(
            LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.sequence_stage, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull SequenceStageViewHolder holder, int position) {

        holder.setHeader(mSequenceStagesData[position].header)
              .setDescription(mSequenceStagesData[position].description)
              .setTime(mSequenceStagesData[position].time)
              .setTimeLeft(mSequenceStagesData[position].timeLeft)
              .setAdapter(this)
              .setLayoutManager(mLayoutManager);
    }


    @Override
    public int getItemCount() {

        return mSequenceStagesData.length;
    }


    public void setActivePosition(int position) {

        mActivePosition = (position >= 0) && (position < mSequenceStagesData.length) ?
                          position :
                          0;
    }


    public int getActivePosition() {

        return mActivePosition;
    }


    public void setSelectedPosition(int position) {

        mSelectedPosition = (position >= 0) && (position < mSequenceStagesData.length) ?
                            position :
                            NO_SELECTED;
    }


    public int getSelectedPosition() {

        return mSelectedPosition;
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {

        mLayoutManager = (StagesRecyclerViewLayoutManager) layoutManager;
    }

}
