package com.example.android.bgdb.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;

/**
 * Inserts a {@link BoardGame} into the favourites.
 */
public class InsertFavouriteLoader extends AsyncTaskLoader<Uri> {

    private ContentValues contentValues;
    private Uri uri;

    InsertFavouriteLoader(Context context, ContentValues contentValues) {
        super(context);
        this.contentValues = contentValues;
    }

    @Override
    protected void onStartLoading() {
        if (uri == null) {
            forceLoad();
        } else {
            deliverResult(uri);
        }
    }

    @Override
    public Uri loadInBackground() {
        return getContext().getContentResolver().insert(BoardGameEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void deliverResult(@Nullable Uri uri) {
        this.uri = uri;
        super.deliverResult(uri);
    }
}