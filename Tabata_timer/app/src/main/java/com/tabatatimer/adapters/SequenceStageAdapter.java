package com.tabatatimer.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.viewholders.SequenceStageViewHolder;



public class SequenceStageAdapter extends RecyclerView.Adapter<SequenceStageViewHolder> {

    private SequenceStageInfoStructure[] mSequenceStagesData;


    public SequenceStageAdapter(SequenceStageInfoStructure[] data) {

        mSequenceStagesData = data;
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
              .setTimeLeft(mSequenceStagesData[position].timeLeft);
    }


    @Override
    public int getItemCount() {

        return mSequenceStagesData.length;
    }

}
