package com.tabatatimer.ui.sequence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.StagesRecyclerViewAdapter;
import com.tabatatimer.misc.SequenceStageInfoStructure;



public class SequenceFragment extends Fragment {

    private SequenceViewModel sequenceViewModel;

    private Button mBPLay;
    private Button mBStop;

    private RecyclerView              mStagesRecyclerView;
    private StagesRecyclerViewAdapter mStagesRecyclerViewAdapter;


    public SequenceFragment() {

        super(R.layout.fragment_sequence);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            mStagesRecyclerView        = view.findViewById(R.id.stagesRecyclerView);
            mStagesRecyclerViewAdapter = new StagesRecyclerViewAdapter(setSeed());
            mStagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mStagesRecyclerView.setAdapter(mStagesRecyclerViewAdapter);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mBPLay = view.findViewById(R.id.actionBtn_play);
        mBStop = view.findViewById(R.id.actionBtn_stop);

        mBPLay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view_) {

                cancelStyleActive();

                mStagesRecyclerView.smoothScrollToPosition(
                    mStagesRecyclerViewAdapter.getCurrentPosition()
                );

                applyStyleActive();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }


    //region Apply styles
    public void applyStyleActive() {

        /*
         * This value may be > mStagesRecyclerViewAdapter.getItemCount().
         * That means a playback has just ended.
         */
        int position = mStagesRecyclerViewAdapter.getCurrentPosition();
        View view = mStagesRecyclerView.getLayoutManager()
                                       .findViewByPosition(position);

        try {
            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.shapes_sequence_stage_active,
                                                getContext().getTheme())
                );
            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.sequenceStageTimeLeft)
                .setVisibility(View.VISIBLE);
            view.findViewById(R.id.sequenceStageProgressBar)
                .setVisibility(View.VISIBLE);
        }
        catch (NullPointerException exception) {
            if (position < 0 ||
                position >= mStagesRecyclerViewAdapter.getItemCount()) {

                return;
            }
            else {
                mStagesRecyclerView.addOnChildAttachStateChangeListener(
                    new RecyclerView.OnChildAttachStateChangeListener() {

                        @Override
                        public void onChildViewAttachedToWindow(@NonNull View view) {

                            applyStyleActive();
                        }


                        @Override
                        public void onChildViewDetachedFromWindow(@NonNull View view) {}
                    }
                );
            }
        }
    }


    public void cancelStyleActive() {

        /*
         * This value may be -1.
         * That means a playback hasn't been started yet.
         */
        int position = mStagesRecyclerViewAdapter.getCurrentPosition();
        View view = mStagesRecyclerView.getLayoutManager()
                                       .findViewByPosition(position);

        try {

            view.findViewById(R.id.sequenceStageBackground)
                .setBackground(
                    ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.shapes_sequence,
                                                getContext().getTheme())
                );
            view.findViewById(R.id.sequenceStageDescription)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageTimeLeft)
                .setVisibility(View.GONE);
            view.findViewById(R.id.sequenceStageProgressBar)
                .setVisibility(View.GONE);
        }
        catch (NullPointerException exception) {
            if (position < 0 ||
                position >= mStagesRecyclerViewAdapter.getItemCount()) {

                return;
            }

            throw exception;
        }
        finally {
            mStagesRecyclerViewAdapter.setCurrentPosition(position + 1);
        }
    }
    //endregion


    //    TODO: Remove the method
    public SequenceStageInfoStructure[] setSeed() {

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