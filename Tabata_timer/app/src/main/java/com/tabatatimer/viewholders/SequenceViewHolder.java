package com.tabatatimer.viewholders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.ui.sequence.adapters.SequenceRecyclerViewAdapter;



public class SequenceViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvHeader;
    private final TextView mTvDescription;
    private final TextView mTvPhasesAmountInfo;
    private final TextView mTvTotalTimeInfo;


    public SequenceViewHolder(@NonNull View view,
                              SequenceRecyclerViewAdapter adapter) {

        super(view);
        mTvHeader           = view.findViewById(R.id.textView_sequence_header);
        mTvDescription      = view.findViewById(R.id.textView_sequence_description);
        mTvPhasesAmountInfo = view.findViewById(R.id.textView_sequence_stagesAmount);
        mTvTotalTimeInfo    = view.findViewById(R.id.textView_sequence_totalTime);
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


    @SuppressWarnings("UnusedReturnValue")
    public SequenceViewHolder setTotalTimeInfo(String totalTimeInfo) {

        mTvTotalTimeInfo.setText(totalTimeInfo);
        return this;
    }


    // region Events
    private void setViewOnClickEvent(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
    }
    // endregion

}
