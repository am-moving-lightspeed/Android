package com.tabatatimer.ui.library;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.managers.CrudButtonsManager;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.managers.IRecyclerViewItemManager;
import com.tabatatimer.misc.SequenceInfoStructure;
import com.tabatatimer.sqlite.DbManager;
import com.tabatatimer.ui.library.adapters.LibraryRecyclerViewAdapter;
import com.tabatatimer.ui.library.dialogs.AddDialogFragment;
import com.tabatatimer.ui.library.dialogs.DeleteDialogFragment;
import com.tabatatimer.ui.library.dialogs.EditLibraryDialogFragment;
import com.tabatatimer.ui.library.managers.LibraryManager;
import com.tabatatimer.viewmodels.SharedDbViewModel;

import java.util.ArrayList;



public class LibraryFragment extends Fragment {

    private SequenceInfoStructure[] mSequences;

    private LibraryRecyclerViewAdapter mAdapter;
    private ICrudButtonsManager        mCrudButtonsManager;
    private IRecyclerViewItemManager   mLibraryManager;
    private SharedDbViewModel          mSharedDbVM;

    private Context mContext;

    private RecyclerView mRecyclerView;


    public LibraryFragment(Context context, SharedDbViewModel viewModel) {

        super(R.layout.fragment_library);
        mContext    = context;
        mSharedDbVM = viewModel;

        assert DbManager.getInstance() != null;

        DbManager.getInstance()
                 .fetchSequences(0);

        // Let fetcher do it's job.
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);

        mSequences = setContent();
        mAdapter   = new LibraryRecyclerViewAdapter(mSequences, this);

        if (view != null) {
            mRecyclerView = view.findViewById(R.id.recyclerView_library_sequencesList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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

        mLibraryManager     = new LibraryManager(mContext,
                                                 mRecyclerView.getLayoutManager());
        mCrudButtonsManager = new CrudButtonsManager(deleteBtnFrame, editBtnFrame);

        mAdapter.setItemManager(mLibraryManager);
        mAdapter.setCrudButtonsManager(mCrudButtonsManager);

        setAddButtonEvents(bAdd);
        setEditButtonEvents(bEdit);
        setDeleteButtonEvents(bDelete);
    }


    // region Events
    private void setAddButtonEvents(View bAdd) {

        assert getActivity() != null;

        bAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment addDialogFragment =
                    new AddDialogFragment(mLibraryManager.getSelectedView(),
                                          mLibraryManager);

                //noinspection ConstantConditions
                addDialogFragment.show(getActivity().getSupportFragmentManager(),
                                       "library_addSequence");
            }
        });
    }


    private void setEditButtonEvents(View bEdit) {

        assert getActivity() != null;

        bEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment editDialogFragment =
                    new EditLibraryDialogFragment(mLibraryManager.getSelectedView(),
                                                  mLibraryManager,
                                                  mSequences);

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
                                             mAdapter,
                                             mSequences);

                //noinspection ConstantConditions
                deleteDialogFragment.show(getActivity().getSupportFragmentManager(),
                                          "library_deleteSequence");
            }
        });
    }
    // endregion


    public void onSequenceChosen(Integer position) {

        mSharedDbVM.setLastFK(position);

//        getParentFragmentManager().beginTransaction()
//                                  .setReorderingAllowed(true)
//                                  .replace(R.id.main_navigationHostFragment, SequenceFragment.class, null)
//                                  .commit();

        Navigation.findNavController(requireActivity(), R.id.main_navigationHostFragment)
                  .navigate(R.id.fragment_sequence);
    }


    // TODO: fix bug
    private SequenceInfoStructure[] setContent() {

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<SequenceInfoStructure> list   = new ArrayList<>();
        Cursor                           cursor = mSharedDbVM.getSequenceCursor();

        while (cursor.moveToNext()) {
            SequenceInfoStructure data = new SequenceInfoStructure();

            data.header       = cursor.getString(1);
            data.description  = cursor.getString(2);
            data.totalTime    = cursor.getString(3);
            data.stagesAmount = cursor.getString(4);

            list.add(data);
        }

        return list.toArray(new SequenceInfoStructure[0]);
    }

}