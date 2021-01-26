package com.tabatatimer.ui.library.viewholders;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.RecyclerViewAdapterAbstract;



public class SequenceViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvHeader;
    private TextView mTvDescription;
    private TextView mTvTotalTime;
    private TextView mTvStagesAmount;

    private Context                                                        mContext;
    private RecyclerViewAdapterAbstract<? extends RecyclerView.ViewHolder> mAdapter;


    public SequenceViewHolder(@NonNull View view,
                              Context context,
                              RecyclerViewAdapterAbstract<? extends RecyclerView.ViewHolder> adapter) {

        super(view);
        mTvHeader       = view.findViewById(R.id.textView_sequence_header);
        mTvDescription  = view.findViewById(R.id.textView_sequence_description);
        mTvTotalTime    = view.findViewById(R.id.textView_sequence_totalTime);
        mTvStagesAmount = view.findViewById(R.id.textView_sequence_stagesAmount);
        mAdapter        = adapter;
        mContext        = context;

        setViewOnClickEvents(view);
        setViewOnLongClickEvents(view);
    }


    public SequenceViewHolder setHeader(String text) {

        mTvHeader.setText(text);
        return this;
    }


    public SequenceViewHolder setDescription(String text) {

        mTvDescription.setText(text);
        return this;
    }


    public SequenceViewHolder setTotalTime(String text) {

        mTvTotalTime.setText(
            mContext.getResources()
                    .getString(R.string.all_totalTimePlaceholder, text)
        );
        return this;
    }


    @SuppressWarnings("UnusedReturnValue")
    public SequenceViewHolder setStagesAmount(String text) {

        mTvStagesAmount.setText(
            mContext.getResources()
                    .getString(R.string.all_stagesAmountPlaceholder, text)
        );
        return this;
    }


    // region Events
    private void setViewOnClickEvents(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mAdapter.resolveItemClickEvent(getAdapterPosition());
            }
        });
    }


    private void setViewOnLongClickEvents(View view) {

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                mAdapter.resolveItemLongClickEvent(getAdapterPosition());
                return true;
            }
        });
    }
    // endregion

}
