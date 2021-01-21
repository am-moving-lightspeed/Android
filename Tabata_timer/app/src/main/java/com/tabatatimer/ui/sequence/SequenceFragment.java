package com.tabatatimer.ui.sequence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.ui.sequence.adapters.SequenceRecyclerViewAdapter;
import com.tabatatimer.managers.CrudButtonsManager;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.dialogs.DeleteDialogFragment;
import com.tabatatimer.ui.sequence.dialogs.EditSequenceStageDialogFragment;
import com.tabatatimer.ui.sequence.managers.ISequenceRecyclerViewManager;
import com.tabatatimer.ui.sequence.managers.SequenceManager;



public class SequenceFragment extends Fragment {

    private ISequenceRecyclerViewManager mSequenceManager;
    private ICrudButtonsManager          mCrudButtonsManager;

    private RecyclerView                mRecyclerView;
    private SequenceRecyclerViewAdapter mAdapter;


    public SequenceFragment() {

        super(R.layout.fragment_sequence);

        mAdapter = new SequenceRecyclerViewAdapter(setSeed());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            mRecyclerView = view.findViewById(R.id.recyclerView_sequence_stagesList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        View bPlay   = view.findViewById(R.id.imageView_sequence_btnPlay);
        View bEdit   = view.findViewById(R.id.imageView_sequence_btnEdit);
        View bDelete = view.findViewById(R.id.imageView_sequence_btnDelete);

        View editBtnFrame   = view.findViewById(R.id.frameLayout_sequence_btnEditFrame);
        View deleteBtnFrame = view.findViewById(R.id.frameLayout_sequence_btnDeleteFrame);

        mCrudButtonsManager = new CrudButtonsManager(editBtnFrame, deleteBtnFrame);
        mSequenceManager    = new SequenceManager(getContext(),
                                                  mRecyclerView,
                                                  mRecyclerView.getLayoutManager(),
                                                  mCrudButtonsManager);

        mAdapter.setItemManager(mSequenceManager);
        mAdapter.setCrudButtonsManager(mCrudButtonsManager);

        setPlayButtonEvents(bPlay);
        setEditButtonEvents(bEdit);
        setDeleteButtonEvents(bDelete);

        super.onViewCreated(view, savedInstanceState);
    }


    // region Events
    private void setPlayButtonEvents(View bPlay) {

        // TODO: replace event
        bPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view_) {

                mSequenceManager.cancelStyleActive();

                mSequenceManager.setActiveIndex(
                    mSequenceManager.getActiveIndex() + 1
                );

                mSequenceManager.smoothScrollToActivePosition();

                mSequenceManager.applyStyleActive();
            }
        });
    }


    private void setEditButtonEvents(View bEdit) {

        bEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                assert getActivity() != null;

                DialogFragment editDialog =
                    new EditSequenceStageDialogFragment(mSequenceManager.getSelectedView(),
                                                        mSequenceManager);
                editDialog.show(getActivity().getSupportFragmentManager(),
                                "dialog_editSequenceStage");
            }
        });
    }


    private void setDeleteButtonEvents(View bDelete) {

        bDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                assert getActivity() != null;

                DialogFragment deleteDialog =
                    new DeleteDialogFragment(mSequenceManager,
                                             mCrudButtonsManager,
                                             mAdapter);
                deleteDialog.show(getActivity().getSupportFragmentManager(),
                                  "dialog_deleteSequenceStage");
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