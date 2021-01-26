package com.tabatatimer.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME                   = "tabata_timer_content.db";
    public static final String TABLE_NAME_SEQUENCE       = "sequence";
    public static final String TABLE_NAME_SEQUENCE_STAGE = "sequence_stage";

    public static final String ID_COLUMN            = "pk";
    public static final String FK_COLUMN            = "fk";
    public static final String HEADER_COLUMN        = "header";
    public static final String DESCRIPTION_COLUMN   = "description";
    public static final String TIME_COLUMN          = "time";
    public static final String TOTAL_TIME_COLUMN    = "total_time";
    public static final String STAGES_AMOUNT_COLUMN = "stages_amount";
    public static final String COLOUR_COLUMN        = "color_hex";

    public static final int VERSION = 4;


    public DbHelper(@Nullable Context context) {

        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);
        addRows(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME_SEQUENCE_STAGE));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME_SEQUENCE));
        onCreate(db);
    }


    private void createTables(SQLiteDatabase db) {

        db.execSQL(
            String.format("CREATE TABLE %s ( ", TABLE_NAME_SEQUENCE) +
            String.format("%s INTEGER NOT NULL PRIMARY KEY, ", ID_COLUMN) +
            String.format("%s TEXT NOT NULL, ", HEADER_COLUMN) +
            String.format("%s TEXT NOT NULL, ", DESCRIPTION_COLUMN) +
            String.format("%s TEXT NOT NULL, ", TOTAL_TIME_COLUMN) +
            String.format("%s TEXT NOT NULL, ", STAGES_AMOUNT_COLUMN) +
            String.format("%s TEXT);", COLOUR_COLUMN)
        );

        db.execSQL(
            String.format("CREATE TABLE %s ( ", TABLE_NAME_SEQUENCE_STAGE) +
            String.format("%s INTEGER NOT NULL PRIMARY KEY, ", ID_COLUMN) +
            String.format("%s TEXT NOT NULL, ", HEADER_COLUMN) +
            String.format("%s TEXT NOT NULL, ", DESCRIPTION_COLUMN) +
            String.format("%s TEXT NOT NULL, ", TIME_COLUMN) +
            String.format("%s INTEGER NOT NULL, ", FK_COLUMN) +
            String.format("FOREIGN KEY (%s) REFERENCES %s (%s));", FK_COLUMN, TABLE_NAME_SEQUENCE, ID_COLUMN)
        );
    }


    private void addRows(SQLiteDatabase db) {

        // region Sequences
        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TOTAL_TIME_COLUMN,
                          STAGES_AMOUNT_COLUMN,
                          "'Light training'",
                          "'Light morning or, maybe, evening training for your body and for example.'",
                          "'12:30'",
                          "'4'")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TOTAL_TIME_COLUMN,
                          STAGES_AMOUNT_COLUMN,
                          "'Medium training'",
                          "'Medium morning or, maybe, evening training for your body and for example.'",
                          "'16:45'",
                          "'3'")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TOTAL_TIME_COLUMN,
                          STAGES_AMOUNT_COLUMN,
                          "'Hard training'",
                          "'Hard morning or, maybe, evening training for your body and for example.'",
                          "'25:10'",
                          "'4'")
        );
        // endregion

        // region Sequences' stages
        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Warm up'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'4:30'",
                          "1")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Jogging'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'3:00'",
                          "1")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Rest peacefully'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'2:30'",
                          "1")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Exercise'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'2:30'",
                          "1")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Warm up'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'3:00'",
                          "2")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Rest peacefully'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'2:30'",
                          "2")
        );

        db.execSQL(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, %s, %s, %s);",
                          TABLE_NAME_SEQUENCE_STAGE,
                          HEADER_COLUMN,
                          DESCRIPTION_COLUMN,
                          TIME_COLUMN,
                          FK_COLUMN,
                          "'Exercise'",
                          "'Warm up is one of the most important things. Do not forget that.'",
                          "'11:45'",
                          "2")
        );
        // endregion
    }

}
