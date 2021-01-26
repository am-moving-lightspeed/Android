package com.tabatatimer.services;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.tabatatimer.R;



public class TimerService extends Service {

    public class TimeServiceBinder extends Binder {

        public TimerService getService() {

            return TimerService.this;
        }

    }



    private final TimeServiceBinder mBinder = new TimeServiceBinder();

    private static final long SECOND = 1000L;

    private boolean isRunning = false;

    private ServiceDataProvider mData;
    private CountDownTimer      mCountDownTimer;
    private MediaPlayer         mMediaPlayer;

    private Runnable mTimerTickRunnable;
    private Runnable mTimerFinishedRunnable;
    private Runnable mTimerRewindRunnable;
    private Runnable mTimerForwardRunnable;
    private Runnable mTimerStopRunnable;

    private Handler mHandler;


    public void startTimer() {

        mCountDownTimer = new CountDownTimer(mData.getMillisecondsLeft(), SECOND) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / SECOND;
                if (seconds == 10) {
                    mMediaPlayer.start();
                }

                mData.decrementMillisecondsLeft();

                if (mTimerTickRunnable != null) {
                    mHandler.post(mTimerTickRunnable);
                }
            }


            @Override
            public void onFinish() {

                mData.incrementCurrentStageIndex();

                if (mData.getCurrentStageIndex() >= mData.getAmount()) {
                    cancel();
                    mData.resetSequence();

                }
                else {
                    start();
                }

                if (mTimerFinishedRunnable != null) {
                    mHandler.post(mTimerFinishedRunnable);
                }
            }
        }.start();

        toggleRunningState();
    }


    public void pauseTimer() {

        mCountDownTimer.cancel();
        toggleRunningState();
    }


    public void stopTimer() {

        mCountDownTimer.cancel();
        mHandler.post(mTimerStopRunnable);
        if (isRunning) {
            toggleRunningState();
        }
    }


    public void rewindTimer() {

        mHandler.post(mTimerRewindRunnable);
    }


    public void forwardTimer() {

        mHandler.post(mTimerForwardRunnable);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }


    @Override
    public void onCreate() {

        super.onCreate();

        mData        = ServiceDataProvider.getInstance();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mHandler     = new Handler(Looper.getMainLooper());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    public boolean isRunning() {

        return isRunning;
    }


    public void toggleRunningState() {

        isRunning = !isRunning;
    }


    public void setOnTimerTickRunnable(Runnable runnable) {

        mTimerTickRunnable = runnable;
    }


    public void setOnTimerFinishedRunnable(Runnable runnable) {

        mTimerFinishedRunnable = runnable;
    }


    public void setOnTimerRewindRunnable(Runnable runnable) {

        mTimerRewindRunnable = runnable;
    }


    public void setOnTimerForwardRunnable(Runnable runnable) {

        mTimerForwardRunnable = runnable;
    }


    public void setOnTimerStopRunnable(Runnable runnable) {

        mTimerStopRunnable = runnable;
    }

}