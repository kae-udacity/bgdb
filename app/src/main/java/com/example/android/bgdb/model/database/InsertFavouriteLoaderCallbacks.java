package com.example.android.bgdb.model.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.view.ContextWrapper;

/**
 * Creates a loader to insert a {@link BoardGame} into the favourites using the
 * {@link ContentResolver}.
 */
public class InsertFavouriteLoaderCallbacks implements LoaderManager.LoaderCallbacks<Uri> {

    private ContextWrapper contextWrapper;
    private DetailPresenter presenter;
    private BoardGame boardGame;

    public InsertFavouriteLoaderCallbacks(
            ContextWrapper contextWrapper,
            DetailPresenter presenter,
            BoardGame boardGame) {
        this.contextWrapper = contextWrapper;
        this.presenter = presenter;
        this.boardGame = boardGame;
    }

    @Override
    public Loader<Uri> onCreateLoader(int id, Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BoardGameEntry.COLUMN_NAME, boardGame.getName());
        contentValues.put(BoardGameEntry.COLUMN_API_ID, boardGame.getId());
        contentValues.put(BoardGameEntry.COLUMN_THUMBNAIL, boardGame.getThumbnailBlob());
        contentValues.put(BoardGameEntry.COLUMN_YEAR, boardGame.getYear());
        return new InsertFavouriteLoader(contextWrapper.getContext(), contentValues);
    }

    @Override
    public void onLoadFinished(Loader<Uri> loader, Uri uri) {
        boolean insertSuccessful = uri != null;
        presenter.onPostInsert(insertSuccessful);
    }

    @Override
    public void onLoaderReset(Loader<Uri> loader) {

    }
}
