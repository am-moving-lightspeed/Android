package com.tabatatimer.sqlite;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



@SuppressWarnings("FieldCanBeLocal")
public class DbManager implements IDbManager {

    private static DbManager mInstance;

    private SQLiteDatabase mDb;
    private Cursor         mSequenceCursor;
    private Cursor         mSequenceStageCursor;

    private IFetching mFetchingListener;


    private DbManager(Context context) {

        DbHelper mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }


    public static IDbManager getInstance() {

        try {
            if (mInstance == null) {
                throw new NullPointerException("Initialize IDbManager instance first.");
            }
            else {
                return mInstance;
            }
        }
        catch (NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
    }


    public static void instantiate(Context context) {

        if (mInstance == null) {
            mInstance = new DbManager(context);
        }
    }


    @Override
    public synchronized void fetchSequences(int delay) {

        if (mSequenceCursor != null && !mSequenceCursor.isClosed()) {
            mSequenceCursor.close();
        }

        final SequencesFetcherThread thread = new SequencesFetcherThread(mDb, delay);
        thread.setFetchingCompleteListener(new IFetching() {

            @Override
            public void onFetchingComplete(Cursor cursor) {

                mSequenceCursor = cursor;
                if (mFetchingListener != null) {
                    mFetchingListener.onFetchingComplete(cursor);
                }

                try {
                    thread.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    @Override
    public synchronized void fetchSequenceStages(int fk) {

        if (mSequenceStageCursor != null && !mSequenceStageCursor.isClosed()) {
            mSequenceStageCursor.close();
        }

        final SequenceStagesFetcherThread thread = new SequenceStagesFetcherThread(mDb, fk);
        thread.setFetchingCompleteListener(new IFetching() {

            @Override
            public void onFetchingComplete(Cursor cursor) {

                mSequenceStageCursor = cursor;
                if (mFetchingListener != null) {
                    mFetchingListener.onFetchingComplete(cursor);
                }

                try {
                    thread.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    @Override
    public synchronized Cursor getSequences() {

        return mSequenceCursor;
    }


    @Override
    public synchronized Cursor getSequenceStages() {

        return mSequenceStageCursor;
    }


    @Override
    public void setFetchingListener(IFetching listener) {

        mFetchingListener = listener;
    }


    @Override
    public void removeFetchingListener() {

        mFetchingListener = null;
    }


    @Override
    public void dropDatabase() {

        mDb.execSQL(String.format("DROP TABLE IF EXISTS %s;", DbHelper.TABLE_NAME_SEQUENCE_STAGE));
        mDb.execSQL(String.format("DROP TABLE IF EXISTS %s;", DbHelper.TABLE_NAME_SEQUENCE));
    }


    @Override
    public synchronized void closeConnection() {

        if (!mSequenceCursor.isClosed()) {
            mSequenceCursor.close();
        }

        if (!mSequenceStageCursor.isClosed()) {
            mSequenceStageCursor.close();
        }

        mDb.close();
    }

}
