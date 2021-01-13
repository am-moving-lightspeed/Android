package com.tabatatimer.viewholders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;



public class SequenceViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvHeader;
    private final TextView mTvDescription;
    private final TextView mTvPhasesAmountInfo;
    private final TextView mTvTotalTimeInfo;


    public SequenceViewHolder(@NonNull View view) {

        super(view);
        mTvHeader           = view.findViewById(R.id.sequenceHeader);
        mTvDescription      = view.findViewById(R.id.sequenceDescription);
        mTvPhasesAmountInfo = view.findViewById(R.id.sequencePhasesAmount);
        mTvTotalTimeInfo    = view.findViewById(R.id.sequenceTotalTime);
    }


    public SequenceViewHolder setHeader(String header) {

        mTvHeader.setText(header);
        return this;
    }


    public SequenceViewHolder setDescription(String description) {

        mTvDescription.setText(description);
        return this;
    }


    public SequenceViewHolder setPhasesAmountInfo(String phasesAmountInfo) {

        mTvPhasesAmountInfo.setText(phasesAmountInfo);
        return this;
    }


    public SequenceViewHolder setTotalTimeInfo(String totalTimeInfo) {

        mTvTotalTimeInfo.setText(totalTimeInfo);
        return this;
    }

}
