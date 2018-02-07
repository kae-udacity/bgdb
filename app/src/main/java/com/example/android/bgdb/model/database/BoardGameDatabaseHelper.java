package com.example.android.bgdb.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;

/**
 * Manages database creation and version management.
 */
public class BoardGameDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "boardgames.db";
    private static final int DATABASE_VERSION = 1;

    public BoardGameDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateDatabase = "CREATE TABLE " +
                BoardGameEntry.TABLE_NAME + " (" +
                BoardGameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BoardGameEntry.COLUMN_API_ID + " INTEGER NOT NULL, " +
                BoardGameEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BoardGameEntry.COLUMN_THUMBNAIL + " BLOB NOT NULL, " +
                BoardGameEntry.COLUMN_YEAR + " TEXT NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(sqlCreateDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
