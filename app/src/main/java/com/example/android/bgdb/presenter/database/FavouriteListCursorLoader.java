package com.example.android.bgdb.presenter.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.android.bgdb.model.BoardGameContract.BoardGameEntry;
import com.example.android.bgdb.presenter.FavouriteListPresenter;
import com.example.android.bgdb.view.BoardGameApplication;

/**
 * Queries the {@link ContentResolver} and returns a {@link Cursor} with data from the
 * board game favourites database.
 */

public class FavouriteListCursorLoader implements LoaderCallbacks<Cursor> {

    private FavouriteListPresenter presenter;

    public FavouriteListCursorLoader(FavouriteListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Context context = BoardGameApplication.getApplication();
        return new CursorLoader(context, BoardGameEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        presenter.onLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
