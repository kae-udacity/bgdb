package com.example.android.bgdb.model.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;

/**
 * Manages access to the data stored in the favourites table of the database.
 */
public class BoardGameContentProvider extends ContentProvider {

    private static final String INVALID_CONTEXT_VALUE = "Invalid context value: context is null.";
    private static final int FAVOURITES = 100;
    private static final int FAVOURITE_WITH_API_ID = 101;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private BoardGameDatabaseHelper databaseHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(BoardGameContract.AUTHORITY, BoardGameContract.PATH_FAVOURITES, FAVOURITES);

        String apiIdPath = BoardGameContract.PATH_FAVOURITES + "/" + BoardGameEntry.COLUMN_API_ID + "/#";
        uriMatcher.addURI(BoardGameContract.AUTHORITY, apiIdPath, FAVOURITE_WITH_API_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new BoardGameDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Context context = getContext();
        if (context == null) {
            throw new NullPointerException(INVALID_CONTEXT_VALUE);
        }

        int match = URI_MATCHER.match(uri);
        Cursor cursor;
        switch (match) {
            case FAVOURITES:
                cursor = databaseHelper.getReadableDatabase().query(
                        BoardGameEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVOURITE_WITH_API_ID:
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = databaseHelper.getReadableDatabase().query(
                        BoardGameEntry.TABLE_NAME,
                        projection,
                        BoardGameEntry.COLUMN_API_ID + context.getString(R.string.parameter_placeholder),
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(context.getString(R.string.unknown_uri) + uri);
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Context context = getContext();
        if (context == null) {
            throw new NullPointerException(INVALID_CONTEXT_VALUE);
        }

        int match = URI_MATCHER.match(uri);
        Uri responseUri;
        switch (match) {
            case FAVOURITES:
                long id = databaseHelper.getWritableDatabase()
                        .insert(BoardGameEntry.TABLE_NAME, null, contentValues);

                if (id > 0) {
                    responseUri = ContentUris.withAppendedId(BoardGameEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(context.getString(R.string.insert_row_failed) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(context.getString(R.string.unknown_uri) + uri);
        }
        context.getContentResolver().notifyChange(responseUri, null);

        return responseUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
