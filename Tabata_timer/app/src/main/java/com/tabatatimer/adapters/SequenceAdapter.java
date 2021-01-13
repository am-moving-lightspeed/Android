package com.tabatatimer.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.viewholders.SequenceViewHolder;
import com.tabatatimer.misc.SequenceInfoStructure;



public class SequenceAdapter extends RecyclerView.Adapter<SequenceViewHolder> {

    private SequenceInfoStructure[] mSequencesInfo;


    public SequenceAdapter(SequenceInfoStructure[] info) {

        mSequencesInfo = info;
    }


    @NonNull
    @Override
    public SequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SequenceViewHolder(
            LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.sequence, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull SequenceViewHolder holder, int position) {

        holder.setHeader(mSequencesInfo[position].header)
              .setDescription(mSequencesInfo[position].description)
              .setPhasesAmountInfo(mSequencesInfo[position].phasesAmountInfo)
              .setTotalTimeInfo(mSequencesInfo[position].totalTimeInfo);
    }


    @Override
    public int getItemCount() {

        return mSequencesInfo.length;
    }

}
