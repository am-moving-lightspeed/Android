package com.oaoaoa.battleships.misc.recycler;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oaoaoa.battleships.R;



public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mHeader;
    private final TextView mDateTime;
    private final TextView mHost;
    private final TextView mHostScore;
    private final TextView mNonHost;
    private final TextView mNonHostScore;


    public ViewHolder(@NonNull View itemView) {

        super(itemView);

        mHeader       = itemView.findViewById(R.id.textView_statistics_status);
        mDateTime     = itemView.findViewById(R.id.textView_statistics_dateTime);
        mHost         = itemView.findViewById(R.id.textView_statistics_hostName);
        mHostScore    = itemView.findViewById(R.id.textView_statistics_hostScore);
        mNonHost      = itemView.findViewById(R.id.textView_statistics_nonHostName);
        mNonHostScore = itemView.findViewById(R.id.textView_statistics_nonHostScore);
    }


    public void setHeader(String value) {

        mHeader.setText(value);
    }


    public void setDateTime(String value) {

        mDateTime.setText(value);
    }


    public void setHost(String value) {

        mHost.setText(value);
    }


    public void setHostScore(String value) {

        mHostScore.setText(value);
    }


    public void setNonHost(String value) {

        mNonHost.setText(value);
    }


    public void setNonHostScore(String value) {

        mNonHostScore.setText(value);
    }

}
