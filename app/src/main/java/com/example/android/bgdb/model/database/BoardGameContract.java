package com.example.android.bgdb.model.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines supported URIs and columns for the tables of the board games database.
 */
public final class BoardGameContract  {

    static final String AUTHORITY = "com.example.android.bgdb";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_FAVOURITES = "favourites";

    private BoardGameContract() {

    }

    public static final class BoardGameEntry implements BaseColumns {
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_API_ID = "api_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_YEAR = "year";
    }
}
