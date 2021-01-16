package com.tabatatimer.viewholders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.StagesRecyclerViewAdapter;
import com.tabatatimer.layoutmanagers.StagesRecyclerViewLayoutManager;



public class SequenceStageViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvHeader;
    private final TextView mTvDescription;
    private final TextView mTvTime;
    private final TextView mTvTimeLeft;

    private StagesRecyclerViewAdapter       mAdapter;
    private StagesRecyclerViewLayoutManager mLayoutManager;


    public SequenceStageViewHolder(@NonNull View view) {

        super(view);
        mTvHeader      = view.findViewById(R.id.sequenceStageHeader);
        mTvDescription = view.findViewById(R.id.sequenceStageDescription);
        mTvTime        = view.findViewById(R.id.sequenceStageTime);
        mTvTimeLeft    = view.findViewById(R.id.sequenceStageTimeLeft);


        // region Events
        view.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (position != mAdapter.getActivePosition()) {

                        if (position != mAdapter.getSelectedPosition()) {
                            if (mLayoutManager.areCrudButtonsHidden()) {
                                // TODO: add style change
                                mLayoutManager.performFabAppearingAnimation();
                                mLayoutManager.toggleCrudButtons();
                            }

                            mAdapter.setSelectedPosition(position);
                        }
                        else {
                            // TODO: add style change
                            mLayoutManager.performFabDisappearingAnimation();
                            mAdapter.setSelectedPosition(mAdapter.NO_SELECTED);
                            mLayoutManager.toggleCrudButtons();
                        }
                    }
                }
            }
        );
        // endregion
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


    public SequenceStageViewHolder setTimeLeft(String timeLeft) {

        mTvTimeLeft.setText(timeLeft);
        return this;
    }


    public SequenceStageViewHolder setAdapter(RecyclerView.Adapter<SequenceStageViewHolder> adapter) {

        mAdapter = (StagesRecyclerViewAdapter) adapter;
        return this;
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {

        mLayoutManager = (StagesRecyclerViewLayoutManager) layoutManager;
    }

}
