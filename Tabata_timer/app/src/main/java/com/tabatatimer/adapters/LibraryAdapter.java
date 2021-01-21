package com.tabatatimer.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.viewholders.SequenceViewHolder;
import com.tabatatimer.misc.SequenceInfoStructure;



public class LibraryAdapter extends RecyclerView.Adapter<SequenceViewHolder> {

    private SequenceInfoStructure[] mSequencesData;


    public LibraryAdapter(SequenceInfoStructure[] data) {

        mSequencesData = data;
    }


    @NonNull
    @Override
    public SequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull SequenceViewHolder holder, int position) {

        holder.setHeader(mSequencesData[position].header)
              .setDescription(mSequencesData[position].description)
              .setPhasesAmountInfo(mSequencesData[position].phasesAmountInfo)
              .setTotalTimeInfo(mSequencesData[position].totalTimeInfo);
    }


    @Override
    public int getItemCount() {

        return mSequencesData.length;
    }

}
