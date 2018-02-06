package com.example.android.bgdb.model.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines supported URIs and columns for the tables of the board games database.
 */
final class BoardGameContract  {

    static final String AUTHORITY = "com.example.android.bgdb";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_FAVOURITES = "favourites";

    private BoardGameContract() {

    }

    static final class BoardGameEntry implements BaseColumns {
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();
        static final String TABLE_NAME = "favourites";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_API_ID = "api_id";
        static final String COLUMN_THUMBNAIL = "thumbnail";
        static final String COLUMN_YEAR = "year";
    }
}
