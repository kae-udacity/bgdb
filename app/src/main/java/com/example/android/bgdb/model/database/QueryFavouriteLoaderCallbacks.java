package com.example.android.bgdb.model.database;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.view.ContextWrapper;

/**
 * Creates a loader to query the {@link ContentResolver} and receives a {@link Cursor} with data
 * from the board game favourites database.
 */
public class QueryFavouriteLoaderCallbacks implements LoaderCallbacks<Cursor> {

    private ContextWrapper contextWrapper;
    private DetailPresenter presenter;
    private String apiId;

    public QueryFavouriteLoaderCallbacks(
            ContextWrapper contextWrapper,
            DetailPresenter presenter,
            String apiId) {
        this.contextWrapper = contextWrapper;
        this.presenter = presenter;
        this.apiId = apiId;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        presenter.onPreLoad();
        Uri uri = BoardGameContract.BoardGameEntry.CONTENT_URI
                .buildUpon()
                .appendPath(BoardGameContract.BoardGameEntry.COLUMN_API_ID)
                .appendPath(apiId)
                .build();

        return new CursorLoader(contextWrapper.getContext(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        boolean favourite = cursor != null && cursor.getCount() != 0;
        presenter.onPostQuery(favourite);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
