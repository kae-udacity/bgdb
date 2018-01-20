package com.example.android.bgdb.presenter;

import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.LoaderManager;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.BoardGameContract.BoardGameEntry;
import com.example.android.bgdb.presenter.database.FavouriteListCursorLoader;
import com.example.android.bgdb.view.fragment.FavouriteListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialises a {@link CursorLoader} and creates a {@link List} of {@link BoardGame} objects
 * using the data returned in the {@link Cursor}.
 */

public class FavouriteListPresenterImpl implements FavouriteListPresenter {

    private static final int FAVOURITES_LOADER_ID = 201;

    private FavouriteListView favouriteListView;

    public FavouriteListPresenterImpl(FavouriteListView favouriteListView) {
        this.favouriteListView = favouriteListView;
    }

    @Override
    public void createLoader(LoaderManager loaderManager) {
        loaderManager.initLoader(FAVOURITES_LOADER_ID, null, new FavouriteListCursorLoader(this));
    }

    @Override
    public void onLoadFinished(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            favouriteListView.onLoadFinished(null);
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
            Bitmap thumbnail = BitmapFactory.decodeByteArray(thumbnailBlob, 0, thumbnailBlob.length);
            boardGame.setThumbnail(thumbnail);

            boardGames.add(boardGame);
        }
        cursor.close();
        favouriteListView.onLoadFinished(boardGames);
    }
}
