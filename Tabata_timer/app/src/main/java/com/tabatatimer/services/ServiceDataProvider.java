package com.tabatatimer.services;


import androidx.annotation.NonNull;

import com.tabatatimer.misc.SequenceStageInfoStructure;

import java.util.Locale;



public class ServiceDataProvider {

    private static ServiceDataProvider mServiceDataProvider;

    private SequenceStageInfoStructure[] mStages;
    private int                          mCurrentStageIndex;
    private int                          mAmount;
    private int                          mMillisecondsLeft;


    public static ServiceDataProvider getInstance() {

        return mServiceDataProvider;
    }


    public static void instantiate(@NonNull SequenceStageInfoStructure[] data) {

        mServiceDataProvider = new ServiceDataProvider();

        mServiceDataProvider.mStages            = data;
        mServiceDataProvider.mCurrentStageIndex = 0;
        mServiceDataProvider.mAmount            = data.length;
        mServiceDataProvider.mMillisecondsLeft  = parseTime(data[0].time);
    }


    public int getCurrentStageIndex() {

        return mCurrentStageIndex;
    }


    public void incrementCurrentStageIndex() {

        mCurrentStageIndex++;

        if (mCurrentStageIndex < mAmount) {
            mMillisecondsLeft = parseTime(
                mStages[mCurrentStageIndex].time
            );
        }
    }


    public void decrementCurrentStageIndex() {

        mCurrentStageIndex--;

        if (mCurrentStageIndex >= 0) {
            mMillisecondsLeft = parseTime(
                mStages[mCurrentStageIndex].time
            );
        }
    }


    public String getFullTime() {

        return mStages[mCurrentStageIndex].time;
    }


    public int getMillisecondsLeft() {

        return mMillisecondsLeft;
    }


    public void decrementMillisecondsLeft() {

        mMillisecondsLeft -= 1000;
    }


    public int getAmount() {

        return mAmount;
    }


    public void resetSequence() {

        mCurrentStageIndex = 0;
        mMillisecondsLeft  = parseTime(mStages[mCurrentStageIndex].time);
    }


    public void resetStage() {

        mMillisecondsLeft = parseTime(mStages[mCurrentStageIndex].time);
    }


    public static int parseTime(String time) {

        final int M = 60000;
        final int S = 1000;

        String[] apart = time.split(":");

        return Integer.parseInt(apart[0]) * M +
               Integer.parseInt(apart[1]) * S;
    }


    // Returns seconds.
    public static int parseTime(String time, int timeInteger) {

        int timeParsed = parseTime(time);
        return (timeParsed - timeInteger) / 1000;
    }


    public static String parseTime(int time, boolean negative) {

        final int M = 60000;
        final int S = 1000;

        int minutes = time / M;
        int seconds = (time - minutes * M) / S;

        if (negative) {
            return String.format(Locale.US, "-%d:%02d", minutes, seconds);
        }
        else {
            return String.format(Locale.US, "%d:%02d", minutes, seconds);
        }
    }


    public int getPercentage() {

        return (int) (100.0 - mMillisecondsLeft * 100.0 / parseTime(mStages[mCurrentStageIndex].time));
    }

}
