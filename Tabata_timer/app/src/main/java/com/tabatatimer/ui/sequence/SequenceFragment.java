package com.tabatatimer.ui.sequence;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.misc.SequenceStageInfoStructure;
import com.tabatatimer.services.ServiceDataProvider;
import com.tabatatimer.services.TimerService;
import com.tabatatimer.sqlite.DbManager;
import com.tabatatimer.ui.library.dialogs.AddDialogFragment;
import com.tabatatimer.ui.sequence.adapters.SequenceRecyclerViewAdapter;
import com.tabatatimer.managers.CrudButtonsManager;
import com.tabatatimer.managers.ICrudButtonsManager;
import com.tabatatimer.ui.sequence.dialogs.AddSequenceStageDialogFragment;
import com.tabatatimer.ui.sequence.dialogs.DeleteDialogFragment;
import com.tabatatimer.ui.sequence.dialogs.EditSequenceStageDialogFragment;
import com.tabatatimer.ui.sequence.managers.ISequenceRecyclerViewManager;
import com.tabatatimer.ui.sequence.managers.SequenceManager;
import com.tabatatimer.viewmodels.SharedDbViewModel;

import java.util.ArrayList;



@SuppressWarnings("Convert2Lambda")
public class SequenceFragment extends Fragment {

    private SequenceStageInfoStructure[] mSequenceStagesData;

    private ISequenceRecyclerViewManager mSequenceManager;
    private ICrudButtonsManager          mCrudButtonsManager;
    private SharedDbViewModel            mSharedDbVM;

    private Context mContext;

    private RecyclerView                mRecyclerView;
    private SequenceRecyclerViewAdapter mAdapter;

    private Intent            mServiceIntent;
    private TimerService      mTimerService;
    private ServiceConnection mServiceConnection;


    public SequenceFragment(Context context,
                            SharedDbViewModel viewModel) {

        super(R.layout.fragment_sequence);

        mContext    = context;
        mSharedDbVM = viewModel;

        mServiceIntent     = new Intent(mContext, TimerService.class);
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {

                mTimerService = ((TimerService.TimeServiceBinder) binder).getService();

                setOnTimerTickRunnable();
                setOnTimerFinishRunnable();
                setOnTimerRewindRunnable();
                setOnTimerForwardRunnable();
                setOnTimerStopRunnable();
            }


            @Override
            public void onServiceDisconnected(ComponentName name) {

                mTimerService.pauseTimer();
            }
        };

        assert DbManager.getInstance() != null;

        DbManager.getInstance()
                 .fetchSequenceStages(mSharedDbVM.getLastFK());

        // Let fetcher do it's job.
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        mSequenceStagesData = setContent();
        mAdapter            = new SequenceRecyclerViewAdapter(mSequenceStagesData);

        if (mSequenceStagesData.length != 0) {
            ServiceDataProvider.instantiate(mSequenceStagesData);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requireActivity().startService(mServiceIntent);
        requireActivity().bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            mRecyclerView = view.findViewById(R.id.recyclerView_sequence_stagesList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(mAdapter);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (mSequenceStagesData.length != 0) {
            View bPlay    = view.findViewById(R.id.imageView_sequence_btnPlay);
            View bRewind  = view.findViewById(R.id.imageView_sequence_btnFastRewind);
            View bForward = view.findViewById(R.id.imageView_sequence_btnFastForward);

            setPlayButtonEvents(bPlay);
            setRewindButtonEvents(bRewind);
            setForwardButtonEvents(bForward);
        }

        View bAdd    = view.findViewById(R.id.imageView_sequence_btnAdd);
        View bEdit   = view.findViewById(R.id.imageView_sequence_btnEdit);
        View bDelete = view.findViewById(R.id.imageView_sequence_btnDelete);

        View editBtnFrame   = view.findViewById(R.id.frameLayout_sequence_btnEditFrame);
        View deleteBtnFrame = view.findViewById(R.id.frameLayout_sequence_btnDeleteFrame);

        setAddButtonEvents(bAdd);
        setEditButtonEvents(bEdit);
        setDeleteButtonEvents(bDelete);

        mCrudButtonsManager = new CrudButtonsManager(editBtnFrame, deleteBtnFrame);
        mSequenceManager    = new SequenceManager(mContext,
                                                  mRecyclerView,
                                                  mRecyclerView.getLayoutManager(),
                                                  mCrudButtonsManager);

        mAdapter.setItemManager(mSequenceManager);
        mAdapter.setCrudButtonsManager(mCrudButtonsManager);

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();

        requireActivity().unbindService(mServiceConnection);
        requireActivity().stopService(mServiceIntent);
    }


    // region Events
    private void setOnTimerTickRunnable() {

        mTimerService.setOnTimerTickRunnable(new Runnable() {

            @Override
            public void run() {

                ServiceDataProvider data = ServiceDataProvider.getInstance();
                TextView timeLeft = mSequenceManager.getActiveView()
                                                    .findViewById(R.id.textView_sequenceStage_timeLeft);
                ProgressBar bar = mSequenceManager.getActiveView()
                                                  .findViewById(R.id.progressBar_sequenceStage_progressBar);

                timeLeft.setText(
                    ServiceDataProvider.parseTime(data.getMillisecondsLeft(), true)
                );

                bar.setProgress(data.getPercentage());
            }
        });
    }


    private void setOnTimerFinishRunnable() {

        mTimerService.setOnTimerFinishedRunnable(new Runnable() {

            @Override
            public void run() {

                mSequenceManager.cancelStyleActive();

                mSequenceManager.setActiveIndex(
                    mSequenceManager.getActiveIndex() + 1
                );

                mSequenceManager.smoothScrollToActivePosition();

                mSequenceManager.applyStyleActive();
            }
        });
    }


    private void setOnTimerRewindRunnable() {

        mTimerService.setOnTimerRewindRunnable(new Runnable() {

            @Override
            public void run() {

                ServiceDataProvider data = ServiceDataProvider.getInstance();

                if (mSequenceManager.getActiveIndex() != -1) {

                    int secondsPassed = ServiceDataProvider.parseTime(data.getFullTime(),
                                                                      data.getMillisecondsLeft());
                    TextView timeLeft = mSequenceManager.getActiveView()
                                                        .findViewById(R.id.textView_sequenceStage_timeLeft);
                    ProgressBar bar = mSequenceManager.getActiveView()
                                                      .findViewById(R.id.progressBar_sequenceStage_progressBar);

                    timeLeft.setText(data.getFullTime());
                    bar.setProgress(0);
                    data.resetStage();

                    if (data.getCurrentStageIndex() != 0 && secondsPassed <= 10) {
                        mSequenceManager.cancelStyleActive();
                        mSequenceManager.setActiveIndex(
                            mSequenceManager.getActiveIndex() - 1
                        );
                        mSequenceManager.smoothScrollToActivePosition();
                        mSequenceManager.applyStyleActive();

                        data.decrementCurrentStageIndex();
                    }
                }
            }
        });
    }


    private void setOnTimerForwardRunnable() {

        mTimerService.setOnTimerForwardRunnable(new Runnable() {

            @Override
            public void run() {

                ServiceDataProvider data = ServiceDataProvider.getInstance();

                if (mSequenceManager.getActiveIndex() != -1) {

                    TextView timeLeft = mSequenceManager.getActiveView()
                                                        .findViewById(R.id.textView_sequenceStage_timeLeft);
                    ProgressBar bar = mSequenceManager.getActiveView()
                                                      .findViewById(R.id.progressBar_sequenceStage_progressBar);

                    timeLeft.setText(data.getFullTime());
                    bar.setProgress(0);
                    data.incrementCurrentStageIndex();

                    if (data.getCurrentStageIndex() == data.getAmount()) {
                        mTimerService.stopTimer();
                    }

                    mSequenceManager.cancelStyleActive();
                    mSequenceManager.setActiveIndex(
                        mSequenceManager.getActiveIndex() + 1
                    );
                    mSequenceManager.smoothScrollToActivePosition();
                    mSequenceManager.applyStyleActive();
                }
            }
        });
    }


    private void setOnTimerStopRunnable() {

        mTimerService.setOnTimerStopRunnable(new Runnable() {

            @Override
            public void run() {

                mSequenceManager.cancelStyleActive();
                mSequenceManager.setActiveIndex(-1);

                ServiceDataProvider.getInstance()
                                   .resetSequence();
            }
        });
    }


    private void setPlayButtonEvents(View bPlay) {

        bPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!mTimerService.isRunning()) {
                    if (mSequenceManager.getActiveIndex() == -1) {
                        mSequenceManager.setActiveIndex(
                            mSequenceManager.getActiveIndex() + 1
                        );
                        mSequenceManager.applyStyleActive();
                    }

                    mTimerService.startTimer();
                }
                else {
                    mTimerService.pauseTimer();
                }
            }
        });
    }


    private void setRewindButtonEvents(View bRewind) {

        bRewind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTimerService.rewindTimer();
            }
        });

        bRewind.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                mTimerService.stopTimer();
                return true;
            }
        });
    }


    private void setForwardButtonEvents(View bForward) {

        bForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTimerService.forwardTimer();
            }
        });
    }


    private void setAddButtonEvents(View bAdd) {

        assert getActivity() != null;

        bAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment addDialogFragment =
                    new AddSequenceStageDialogFragment(mSequenceManager.getSelectedView(),
                                                       mSequenceManager);

                //noinspection ConstantConditions
                addDialogFragment.show(getActivity().getSupportFragmentManager(),
                                       "library_addSequence");
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
                                                        mSequenceManager,
                                                        mSequenceStagesData);
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
                                             mAdapter,
                                             mSequenceStagesData);
                deleteDialog.show(getActivity().getSupportFragmentManager(),
                                  "dialog_deleteSequenceStage");
            }
        });
    }
    // endregion


    private SequenceStageInfoStructure[] setContent() {

        ArrayList<SequenceStageInfoStructure> list   = new ArrayList<>();
        Cursor                                cursor = mSharedDbVM.getSequenceStageCursor();

        while (cursor.moveToNext()) {
            SequenceStageInfoStructure data = new SequenceStageInfoStructure();

            data.id          = cursor.getInt(0);
            data.header      = cursor.getString(1);
            data.description = cursor.getString(2);
            data.time        = cursor.getString(3);
            data.foreignKey  = cursor.getInt(4);

            list.add(data);
        }

        return list.toArray(new SequenceStageInfoStructure[0]);
    }

}