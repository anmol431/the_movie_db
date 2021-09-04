package com.themovie.db.app.room;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.themovie.db.app.constants.Constants;

public class GenericController extends SQLiteOpenHelper implements Constants {

    public static SQLiteDatabase database;
    private static GenericController instance = null;

    private GenericController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static GenericController getInstance(Context context) {
        if (instance == null) {
            instance = new GenericController(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        db.execSQL(MovieDB.CREATE_MOVIE_TABLE);
        db.execSQL(MovieDB.CREATE_CAST_TABLE);
        db.execSQL(MovieDB.CREATE_REVIEW_TABLE);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDB.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDB.TABLE_NAME);
        onCreate(db);
    }
}
