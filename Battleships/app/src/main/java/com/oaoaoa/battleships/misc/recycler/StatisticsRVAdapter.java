package com.oaoaoa.battleships.misc.recycler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.oaoaoa.battleships.R;

import java.util.ArrayList;
import java.util.Locale;



public class StatisticsRVAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<StatisticsStruct> mStatistics;
    private final Context                     mContext;


    public StatisticsRVAdapter(Context context, ArrayList<StatisticsStruct> statistics) {

        super();

        mStatistics = statistics;
        mContext    = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =
            (LayoutInflater) mContext.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE);

        return new ViewHolder(
            inflater.inflate(R.layout.viewholder_statistics, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StatisticsStruct struct = mStatistics.get(position);
        holder.setDateTime(struct.date);
        holder.setHost(struct.host);
        holder.setNonHost(struct.nonHost);
        holder.setHostScore(String.format(Locale.US, "%d", struct.hostScore));
        holder.setNonHostScore(String.format(Locale.US, "%d", struct.nonHostScore));

        if (struct.hostScore > struct.nonHostScore) {
            holder.setHeader(mContext.getString(R.string.statistics_vh_statusVictory));
        }
        else {
            holder.setHeader(mContext.getString(R.string.statistics_vh_statusDefeat));
        }
    }


    @Override
    public int getItemCount() {

        return mStatistics.size();
    }

}
