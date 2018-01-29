package com.example.android.bgdb.model.database;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;
import com.example.android.bgdb.presenter.FavouriteListPresenter;
import com.example.android.bgdb.view.ContextWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a loader to query the {@link ContentResolver} and receives a {@link Cursor} with data
 * from the board game favourites database.
 */
public class QueryFavouriteListLoaderCallbacks implements LoaderCallbacks<Cursor> {

    private ContextWrapper contextWrapper;
    private FavouriteListPresenter presenter;

    public QueryFavouriteListLoaderCallbacks(ContextWrapper contextWrapper, FavouriteListPresenter presenter) {
        this.contextWrapper = contextWrapper;
        this.presenter = presenter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        presenter.onPreLoad();
        return new CursorLoader(
                contextWrapper.getContext(),
                BoardGameEntry.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            presenter.onPostLoad(null);
            return;
        }

        List<BoardGame> boardGames = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            BoardGame boardGame = new BoardGame();

            String apiId = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_API_ID));
            boardGame.setId(apiId);

            String name = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_NAME));
            boardGame.setName(name);

            String year = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_YEAR));
            boardGame.setYear(year);

            byte[] thumbnailBlob = cursor.getBlob(cursor.getColumnIndex(BoardGameEntry.COLUMN_THUMBNAIL));
            boardGame.setThumbnailBlob(thumbnailBlob);

            boardGames.add(boardGame);
        }
        cursor.close();
        presenter.onPostLoad(boardGames);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
