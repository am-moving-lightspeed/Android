package com.tabatatimer.ui.library;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.dialogs.DeleteDialogFragment;
import com.tabatatimer.managers.CrudButtonsManager;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.managers.IRecyclerViewItemManager;
import com.tabatatimer.misc.SequenceInfoStructure;
import com.tabatatimer.ui.library.adapters.LibraryRecyclerViewAdapter;
import com.tabatatimer.ui.library.dialogs.EditLibraryDialogFragment;
import com.tabatatimer.ui.library.managers.LibraryManager;



public class LibraryFragment extends Fragment {

    private LibraryRecyclerViewAdapter mAdapter;
    private ICrudButtonsManager        mCrudButtonsManager;
    private IRecyclerViewItemManager   mLibraryManager;

    private RecyclerView mRecyclerView;


    public LibraryFragment() {

        super(R.layout.fragment_library);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);

        mAdapter = new LibraryRecyclerViewAdapter(setSeed(), getContext());

        if (view != null) {
            mRecyclerView = view.findViewById(R.id.recyclerView_library_sequencesList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ImageView bAdd    = view.findViewById(R.id.imageView_library_btnAdd);
        ImageView bEdit   = view.findViewById(R.id.imageView_library_btnEdit);
        ImageView bDelete = view.findViewById(R.id.imageView_library_btnDelete);

        View editBtnFrame   = view.findViewById(R.id.frameLayout_library_btnDeleteFrame);
        View deleteBtnFrame = view.findViewById(R.id.frameLayout_library_btnEditFrame);

        mLibraryManager     = new LibraryManager(getContext(),
                                                 mRecyclerView.getLayoutManager());
        mCrudButtonsManager = new CrudButtonsManager(deleteBtnFrame, editBtnFrame);

        mAdapter.setItemManager(mLibraryManager);
        mAdapter.setCrudButtonsManager(mCrudButtonsManager);

        setEditButtonEvents(bEdit);
        setDeleteButtonEvents(bDelete);
    }


    // region Events
    private void setEditButtonEvents(View bEdit) {

        assert getActivity() != null;

        bEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment editDialogFragment =
                    new EditLibraryDialogFragment(mLibraryManager.getSelectedView(),
                                                  mLibraryManager);

                //noinspection ConstantConditions
                editDialogFragment.show(getActivity().getSupportFragmentManager(),
                                        "library_editSequence");
            }
        });
    }


    private void setDeleteButtonEvents(View bDelete) {

        bDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment deleteDialogFragment =
                    new DeleteDialogFragment(mLibraryManager,
                                             mCrudButtonsManager,
                                             mAdapter);

                //noinspection ConstantConditions
                deleteDialogFragment.show(getActivity().getSupportFragmentManager(),
                                          "library_deleteSequence");
            }
        });
    }
    // endregion


    // TODO: remove method
    private SequenceInfoStructure[] setSeed() {

        SequenceInfoStructure[] data = new SequenceInfoStructure[8];

        data[0]                  = new SequenceInfoStructure();
        data[0].header           = "Light training";
        data[0].description      = "Super light training with jazz just for soul.";
        data[0].totalTimeInfo    = "13:07";
        data[0].phasesAmountInfo = "4";

        data[1]                  = new SequenceInfoStructure();
        data[1].header           = "Medium training";
        data[1].description      = "Super medium training with no jazz just for souuuuuuuuul.";
        data[1].totalTimeInfo    = "14:52";
        data[1].phasesAmountInfo = "7";

        data[2]                  = new SequenceInfoStructure();
        data[2].header           = "Hard training";
        data[2].description      = "Super hard training to kill the one who does exercises/";
        data[2].totalTimeInfo    = "26:14";
        data[2].phasesAmountInfo = "13";

        data[3]                  = new SequenceInfoStructure();
        data[3].header           = "Super super light training";
        data[3].description      = "Super super super  training with jazz just for soul or maybe not yours.";
        data[3].totalTimeInfo    = "5:34";
        data[3].phasesAmountInfo = "3";

        data[4]                  = new SequenceInfoStructure();
        data[4].header           = "Light training x2";
        data[4].description      = "Super light training with jazz just for soul (twice).";
        data[4].totalTimeInfo    = "22:43";
        data[4].phasesAmountInfo = "8";

        data[5]                  = new SequenceInfoStructure();
        data[5].header           = "Light training x3";
        data[5].description      = "Super light training with jazz just for soul (twice).";
        data[5].totalTimeInfo    = "14:45";
        data[5].phasesAmountInfo = "12";

        data[6]                  = new SequenceInfoStructure();
        data[6].header           = "Training";
        data[6].description      = "Super light training with jazz just for soul (twice) (twice) (twice) (twice)." +
                                   " (twice) (twice) (twice) (twice) (twice) (twice) (twice)";
        data[6].totalTimeInfo    = "14:45";
        data[6].phasesAmountInfo = "12";

        data[7]                  = new SequenceInfoStructure();
        data[7].header           = "Training Training Training Training Training ";
        data[7].description      = "Super light training with jazz just for soul (twice) (twice) (twice) (twice).";
        data[7].totalTimeInfo    = "14:45";
        data[7].phasesAmountInfo = "12";

        return data;
    }

}