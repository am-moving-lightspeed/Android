package com.tabatatimer.viewholders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.ui.sequence.adapters.SequenceRecyclerViewAdapter;



public class SequenceStageViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvHeader;
    private final TextView mTvDescription;
    private final TextView mTvTime;
    private final TextView mTvTimeLeft;

    private SequenceRecyclerViewAdapter mAdapter;


    public SequenceStageViewHolder(@NonNull View view,
                                   SequenceRecyclerViewAdapter adapter) {

        super(view);
        mTvHeader      = view.findViewById(R.id.textView_sequenceStage_header);
        mTvDescription = view.findViewById(R.id.textView_sequenceStage_description);
        mTvTime        = view.findViewById(R.id.textView_sequenceStage_time);
        mTvTimeLeft    = view.findViewById(R.id.textView_sequenceStage_timeLeft);

        mAdapter = adapter;

        setViewOnClickEvents(view);
    }


    public SequenceStageViewHolder setHeader(String header) {

        mTvHeader.setText(header);
        return this;
    }


    public SequenceStageViewHolder setDescription(String description) {

        mTvDescription.setText(description);
        return this;
    }


    public SequenceStageViewHolder setTime(String time) {

        mTvTime.setText(time);
        return this;
    }


    @SuppressWarnings("UnusedReturnValue")
    public SequenceStageViewHolder setTimeLeft(String timeLeft) {

        mTvTimeLeft.setText(timeLeft);
        return this;
    }


    // region Events
    private void setViewOnClickEvents(View view) {

        view.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    mAdapter.resolveViewHolderClickEvent(getAdapterPosition());
                }
            }
        );
    }
    // endregion

}
