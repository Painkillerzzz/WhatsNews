package com.example.myapplication.service;

import android.provider.BaseColumns;

public class NewsContract {

    private NewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public  static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_KEYWORDS = "keywords";
        public static final String COLUMN_PUBLISHER = "publisher";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_IMAGE = "image";


        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_CATEGORY + " TEXT" +
                        COLUMN_KEYWORDS + " TEXT," +
                        COLUMN_PUBLISHER + " TEXT," +
                        COLUMN_DATE + " TEXT," +
                        COLUMN_IMAGE + " TEXT," +
                        COLUMN_VIDEO + " TEXT," +
                        COLUMN_CONTENT + " TEXT)";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}