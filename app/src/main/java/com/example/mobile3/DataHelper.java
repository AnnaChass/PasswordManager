package com.example.mobile3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dataDB";
    public static final String TABLE_NAME = "data";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_TABLE_NAME = "login";

    public static final String KEY_ID = "id";
    public static final String KEY_RESOURCE = "resource";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NOTE = "note";
    public static final String KEY_EXIST = "exist";
    public static final String KEY_KEY = "_key";


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_RESOURCE + " TEXT, "
                + KEY_LOGIN + " TEXT, "
                + KEY_PASSWORD + " TEXT, "
                + KEY_NOTE + " TEXT);");

        db.execSQL("CREATE TABLE " + KEY_TABLE_NAME + " ("
                + KEY_EXIST + " TEXT, "
                + KEY_KEY + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
}
