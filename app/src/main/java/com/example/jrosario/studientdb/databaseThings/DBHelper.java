package com.example.jrosario.studientdb.databaseThings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "studentDb.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUM_TWO_NAME_TITLE + " TEXT,"+
                    FeedEntry.COLUM_THREE_NAME_TITLE+ " TEXT,"+
                    FeedEntry.COLUM_FOUR_NAME_TITLE + " TEXT,"+
                    FeedEntry.COLUM_FIVE_NAME_TITLE + " BOOLEAN)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Estudiantes";
        public static final String COLUM_ONE_NAME_TITLE = "_id";
        public static final String COLUM_TWO_NAME_TITLE = "nombre";
        public static final String COLUM_THREE_NAME_TITLE = "materia";
        public static final String COLUM_FOUR_NAME_TITLE = "calificacion";
        public static final String COLUM_FIVE_NAME_TITLE = "favorito";

    }

}
