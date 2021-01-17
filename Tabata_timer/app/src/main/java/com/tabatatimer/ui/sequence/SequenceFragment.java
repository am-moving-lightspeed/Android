package com.tabatatimer.ui.sequence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.StagesRecyclerViewAdapter;
import com.tabatatimer.layoutmanagers.StagesRecyclerViewLayoutManager;
import com.tabatatimer.misc.SequenceStageInfoStructure;



public class SequenceFragment extends Fragment {

    // TODO: implement usage
    private boolean isSequenceRunning;

    private SequenceViewModel sequenceViewModel;

    private View mEditButtonFrame;
    private View mDeleteButtonFrame;

    private RecyclerView                    mStagesRecyclerView;
    private StagesRecyclerViewAdapter       mStagesRecyclerViewAdapter;
    private StagesRecyclerViewLayoutManager mStagesRecyclerViewLayoutManager;


    public SequenceFragment() {

        super(R.layout.fragment_sequence);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        mStagesRecyclerViewAdapter       = new StagesRecyclerViewAdapter(setSeed());
        mStagesRecyclerViewLayoutManager = new StagesRecyclerViewLayoutManager(this,
                                                                               mStagesRecyclerViewAdapter,
                                                                               getContext());
        mStagesRecyclerViewAdapter.setLayoutManager(mStagesRecyclerViewLayoutManager);

        if (view != null) {
            mStagesRecyclerView = view.findViewById(R.id.stagesRecyclerView);
            mStagesRecyclerView.setLayoutManager(mStagesRecyclerViewLayoutManager);
            mStagesRecyclerView.setAdapter(mStagesRecyclerViewAdapter);

            mStagesRecyclerViewLayoutManager.setRecyclerView(mStagesRecyclerView);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        View bPlay = view.findViewById(R.id.abPlay);

        mEditButtonFrame   = view.findViewById(R.id.fabEditFrame);
        mDeleteButtonFrame = view.findViewById(R.id.fabDeleteFrame);

        setPlayButtonEvents(bPlay);

        super.onViewCreated(view, savedInstanceState);
    }


    public boolean isSequenceRunning() {

        return isSequenceRunning;
    }


    public View[] getCrudButtonsFrames() {

        return new View[] {mEditButtonFrame, mDeleteButtonFrame};
    }


    // region Events
    private void setPlayButtonEvents(View bPlay) {

        // TODO: replace event
        bPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view_) {

                mStagesRecyclerViewLayoutManager.cancelStyleActive();

                // TODO: create wrapper
                mStagesRecyclerViewAdapter.setActivePosition(
                    mStagesRecyclerViewAdapter.getActivePosition() + 1
                );

                mStagesRecyclerView.smoothScrollToPosition(
                    mStagesRecyclerViewAdapter.getActivePosition()
                );

                mStagesRecyclerViewLayoutManager.applyStyleActive();
            }
        });
    }
    // endregion


    //    TODO: Remove the method
    private SequenceStageInfoStructure[] setSeed() {

        SequenceStageInfoStructure[] arr = new SequenceStageInfoStructure[8];

        arr[0]             = new SequenceStageInfoStructure();
        arr[0].header      = new String("Warm up");
        arr[0].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[0].time        = new String("00:30");
        arr[0].timeLeft    = new String("00:30");

        arr[1]             = new SequenceStageInfoStructure();
        arr[1].header      = new String("Warm up1");
        arr[1].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[1].time        = "00:30";
        arr[1].timeLeft    = new String("00:30");

        arr[2]             = new SequenceStageInfoStructure();
        arr[2].header      = new String("Warm up2");
        arr[2].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[2].time        = new String("00:30");
        arr[2].timeLeft    = new String("00:30");

        arr[3]             = new SequenceStageInfoStructure();
        arr[3].header      = new String("Warm up3");
        arr[3].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[3].time        = new String("00:30");
        arr[3].timeLeft    = new String("00:30");

        arr[4]             = new SequenceStageInfoStructure();
        arr[4].header      = new String("Warm up4");
        arr[4].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[4].time        = new String("00:30");
        arr[4].timeLeft    = new String("00:30");

        arr[5]             = new SequenceStageInfoStructure();
        arr[5].header      = new String("Warm up5");
        arr[5].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[5].time        = new String("00:30");
        arr[5].timeLeft    = new String("00:30");

        arr[6]             = new SequenceStageInfoStructure();
        arr[6].header      = new String("Warm up6");
        arr[6].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[6].time        = new String("00:30");
        arr[6].timeLeft    = new String("00:30");

        arr[7]             = new SequenceStageInfoStructure();
        arr[7].header      = new String("Warm up7");
        arr[7].description = new String("Warm upWarm upWarm upWarm upWarm upWarm upWWarm upWarm upWarm up");
        arr[7].time        = new String("00:30");
        arr[7].timeLeft    = new String("00:30");

        return arr;
    }

}