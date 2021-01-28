package com.tabatatimer.sqlite;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;



public class SequenceStagesFetcherThread implements Runnable {

    private SQLiteDatabase mDb;
    private IFetching      mFetchingListener;
    private Thread         mThread;

    private int mFK;


    public SequenceStagesFetcherThread(SQLiteDatabase db, int fk) {

        super();
        mThread = new Thread(this, "SequenceStagesFetcherThread");
        mDb     = db;
        mFK     = fk;
    }


    public void setFetchingCompleteListener(IFetching listener) {

        mFetchingListener = listener;
    }


    @Override
    public void run() {

        Cursor cursor = mDb.rawQuery(String.format(Locale.US,
                                                   "SELECT * FROM %s WHERE %s = %d;",
                                                   DbHelper.TABLE_NAME_SEQUENCE_STAGE,
                                                   DbHelper.FK_COLUMN,
                                                   mFK),
                                     null);

        if (mFetchingListener != null) {
            mFetchingListener.onFetchingComplete(cursor);
        }
    }


    public void start() {

        mThread.start();
    }


    public void join() throws
                       InterruptedException {

        mThread.join();
    }

}
