package com.tabatatimer.sqlite;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class SequencesFetcherThread implements Runnable {

    private SQLiteDatabase mDb;
    private IFetching      mFetchingListener;
    private Thread         mThread;

    private int mDelay;


    public SequencesFetcherThread(SQLiteDatabase db, int delay) {

        super();
        mThread = new Thread(this, "SequencesFetcherThread");
        mDb     = db;
        mDelay  = delay;
    }


    public void setFetchingCompleteListener(IFetching listener) {

        mFetchingListener = listener;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(mDelay);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        Cursor cursor = mDb.rawQuery(String.format("SELECT * FROM %s;", DbHelper.TABLE_NAME_SEQUENCE),
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
