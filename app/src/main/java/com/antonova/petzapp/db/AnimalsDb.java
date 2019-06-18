package com.antonova.petzapp.db;

import android.provider.BaseColumns;

public final class AnimalsDb {
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + AnimalsEntry.TABLE_NAME + " (" +
                    AnimalsEntry.COLUMN_ID_RECORD+" INTEGER PRIMARY KEY ,"+
                    AnimalsEntry.COLUMN_ID + " INTEGER ," +
                    AnimalsEntry.COLUMN_NAME + " TEXT," +
                    AnimalsEntry.COLUMN_AGE + " FLOAT,"+
            AnimalsEntry.COLUMN_SEX+"TEXT,"+
            AnimalsEntry.COLUMN_TYPE+"TEXT"+
            AnimalsEntry.COLUMN_IMG+"TEXT"+
            AnimalsEntry.COLUMN_INFO+"TEXT"+
            AnimalsEntry.COLUMN_OWNER+"TEXT)";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + AnimalsEntry.TABLE_NAME;
    private AnimalsDb() {}

    public static class AnimalsEntry implements BaseColumns {
        public static final String TABLE_NAME = "animals";
        public static final String COLUMN_ID_RECORD="record_id";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_IMG = "image";
        public static final String COLUMN_INFO = "info";
        public static final String COLUMN_OWNER = "owner";
    }
}
