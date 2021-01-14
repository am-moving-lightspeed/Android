package com.tabatatimer.ui.sequence;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.activities.MainActivity;
import com.tabatatimer.adapters.SequenceStageAdapter;
import com.tabatatimer.misc.SequenceStageInfoStructure;



public class SequenceFragment extends Fragment {

    private SequenceViewModel sequenceViewModel;


    public SequenceFragment() {

        super(R.layout.fragment_sequence);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View         view = super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView a    = view.findViewById(R.id.stagesRecyclerView);
        a.setLayoutManager(new LinearLayoutManager(getContext()));
        a.setAdapter(new SequenceStageAdapter(setSeed()));


        return view;
    }


    //    TODO: Remove the method
    public SequenceStageInfoStructure[] setSeed() {

        SequenceStageInfoStructure[] arr = new SequenceStageInfoStructure[4];

        arr[0]             = new SequenceStageInfoStructure();
        arr[0].header      = new String("Warm up");
        arr[0].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[0].time        = new String("00:30");
        arr[0].timeLeft    = new String("00:30");

        arr[1]             = new SequenceStageInfoStructure();
        arr[1].header      = new String("Phase 1");
        arr[1].description = new String("Phase 1Phase 1Phase 1Phase 1Phase 1 1Phase 1Phase 1Phase 1Phase 1");
        arr[1].time        = new String("01:30");
        arr[1].timeLeft    = new String("01:30");

        arr[2]             = new SequenceStageInfoStructure();
        arr[2].header      = new String("Warm up");
        arr[2].description = new String("Warm upWarm upWarm upWarm upWarm  upWarm upWarm upWarm up");
        arr[2].time        = new String("00:30");
        arr[2].timeLeft    = new String("00:30");

        arr[3]             = new SequenceStageInfoStructure();
        arr[3].header      = new String("Phase 2");
        arr[3].description = new String("Phase 2Phase 2Phase 2Phase 2Phase 2Phase 2Phe 2Phase 2Phase2Phase");
        arr[3].time        = new String("02:30");
        arr[3].timeLeft    = new String("02:30");

        return arr;
    }

}