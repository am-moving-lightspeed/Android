package com.tabatatimer.sqlite;


import android.database.Cursor;



public interface IFetching {

    void onFetchingComplete(Cursor cursor);

}
