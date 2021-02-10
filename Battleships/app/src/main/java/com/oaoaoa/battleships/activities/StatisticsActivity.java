package com.oaoaoa.battleships.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.AnimationsProvider;
import com.oaoaoa.battleships.misc.Constants;
import com.oaoaoa.battleships.misc.recycler.StatisticsRVAdapter;
import com.oaoaoa.battleships.misc.recycler.StatisticsStruct;

import java.util.ArrayList;
import java.util.Iterator;



public class StatisticsActivity extends AppCompatActivity {

    private RecyclerView                mRecyclerView;
    private ArrayList<StatisticsStruct> mStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_statistics_container)
        );

        mRecyclerView = findViewById(R.id.recyclerView_statistics);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mStatistics = new ArrayList<>();

        FirebaseDatabase.getInstance()
                        .getReference(Constants.FirebaseConstants.STATISTICS)
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                    for (DataSnapshot child : children) {

                                        Iterable<DataSnapshot> stats  = child.getChildren();
                                        Iterator<DataSnapshot> iter   = stats.iterator();
                                        StatisticsStruct       struct = new StatisticsStruct();
                                        struct.date         = iter.next().getValue(String.class);
                                        struct.host         = iter.next().getValue(String.class);
                                        struct.hostScore    = iter.next().getValue(Integer.class);
                                        struct.nonHost      = iter.next().getValue(String.class);
                                        struct.nonHostScore = iter.next().getValue(Integer.class);

                                        mStatistics.add(struct);
                                    }

                                    mRecyclerView.setAdapter(new StatisticsRVAdapter(getApplicationContext(),
                                                                                     mStatistics));

                                    ProgressBar progressBar = findViewById(R.id.progressBar_statistics);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
    }

}
