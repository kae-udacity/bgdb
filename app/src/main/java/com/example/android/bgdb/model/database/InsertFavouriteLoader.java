package com.example.android.bgdb.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;

/**
 * Inserts a {@link BoardGame} into the favourites.
 */
public class InsertFavouriteLoader extends AsyncTaskLoader<Uri> {

    private ContentValues contentValues;

    InsertFavouriteLoader(Context context, ContentValues contentValues) {
        super(context);
        this.contentValues = contentValues;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Uri loadInBackground() {
        return getContext().getContentResolver().insert(BoardGameEntry.CONTENT_URI, contentValues);
    }
}