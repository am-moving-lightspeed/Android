package com.tabatatimer.ui.sequence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.adapters.StagesRecyclerViewAdapter;
import com.tabatatimer.layoutmanagers.StagesRecyclerViewLayoutManager;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.ui.sequence.deletedialog.DeleteSequenceStageDialogFragment;
import com.tabatatimer.ui.sequence.editdialog.EditSequenceStageDialogFragment;



public class SequenceFragment extends Fragment implements
                                               EditSequenceStageDialogFragment.EditDialogButtonClickListener,
                                               DeleteSequenceStageDialogFragment.DeleteDialogButtonClickListener {

    private View mEditButtonFrame;
    private View mDeleteButtonFrame;

    private RecyclerView                    mStagesRecyclerView;
    private StagesRecyclerViewAdapter       mStagesRecyclerViewAdapter;
    private StagesRecyclerViewLayoutManager mStagesRecyclerViewLayoutManager;


    public SequenceFragment() {

        super(R.layout.fragment_sequence);

        mStagesRecyclerViewAdapter       = new StagesRecyclerViewAdapter(setSeed());
        mStagesRecyclerViewLayoutManager = new StagesRecyclerViewLayoutManager(this,
                                                                               mStagesRecyclerViewAdapter,
                                                                               getContext());
        mStagesRecyclerViewAdapter.setLayoutManager(mStagesRecyclerViewLayoutManager);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            mStagesRecyclerView = view.findViewById(R.id.recyclerView_sequence_stagesList);
            mStagesRecyclerView.setLayoutManager(mStagesRecyclerViewLayoutManager);
            mStagesRecyclerView.setAdapter(mStagesRecyclerViewAdapter);

            mStagesRecyclerViewLayoutManager.setRecyclerView(mStagesRecyclerView);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        View bPlay   = view.findViewById(R.id.imageView_sequence_btnPlay);
        View bEdit   = view.findViewById(R.id.imageView_sequence_btnEdit);
        View bDelete = view.findViewById(R.id.imageView_sequence_btnDelete);

        mEditButtonFrame   = view.findViewById(R.id.frameLayout_sequence_btnEditFrame);
        mDeleteButtonFrame = view.findViewById(R.id.frameLayout_sequence_btnDeleteFrame);

        setPlayButtonEvents(bPlay);
        setEditButtonEvents(bEdit);
        setDeleteButtonEvents(bDelete);

        super.onViewCreated(view, savedInstanceState);
    }


    public View[] getCrudButtonsFrames() {

        return new View[] {mEditButtonFrame, mDeleteButtonFrame};
    }


    // region Events
    @Override
    public void onPositiveClick(EditSequenceStageDialogFragment dialogFragment) {

        // TODO: implement database usage
        View view = mStagesRecyclerViewLayoutManager.getSelectedView();

        String header      = dialogFragment.getHeaderText();
        String description = dialogFragment.getDescriptionText();
        String time        = dialogFragment.getMinutesText() + ":" + dialogFragment.getSecondsText();

        ((TextView) view.findViewById(R.id.textView_sequenceStage_header)).setText(header);
        ((TextView) view.findViewById(R.id.textView_sequenceStage_description)).setText(description);
        ((TextView) view.findViewById(R.id.textView_sequenceStage_time)).setText(time);
    }


    @Override
    public void onPositiveClick(DeleteSequenceStageDialogFragment dialogFragment) {

        mStagesRecyclerViewAdapter.removeAt(mStagesRecyclerViewAdapter.getSelectedPosition());
    }


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


    private void setEditButtonEvents(View bEdit) {

        bEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                assert getActivity() != null;

                DialogFragment editDialog =
                    new EditSequenceStageDialogFragment(SequenceFragment.this,
                                                        mStagesRecyclerViewLayoutManager.getSelectedView());
                editDialog.show(getActivity().getSupportFragmentManager(),
                                "EditSequenceStageDialog");
            }
        });
    }


    private void setDeleteButtonEvents(View bDelete) {

        bDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                assert getActivity() != null;

                DialogFragment deleteDialog =
                    new DeleteSequenceStageDialogFragment(SequenceFragment.this);
                deleteDialog.show(getActivity().getSupportFragmentManager(),
                                  "DeleteSequenceStageDialog");
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