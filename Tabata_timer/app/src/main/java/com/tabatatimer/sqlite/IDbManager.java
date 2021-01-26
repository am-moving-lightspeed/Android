package com.tabatatimer.sqlite;


import android.database.Cursor;



public interface IDbManager {

    void fetchSequences(int delay);
    void fetchSequenceStages(int foreignKey);
    Cursor getSequences();
    Cursor getSequenceStages();

    void setFetchingListener(IFetching listener);
    void removeFetchingListener();

    void dropDatabase();

    void closeConnection();

}
