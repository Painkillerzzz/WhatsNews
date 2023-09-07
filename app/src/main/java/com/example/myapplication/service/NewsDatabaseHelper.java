package com.example.myapplication.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "whatsnews.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "news";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";

    public NewsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewsContract.NewsEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NewsContract.NewsEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
