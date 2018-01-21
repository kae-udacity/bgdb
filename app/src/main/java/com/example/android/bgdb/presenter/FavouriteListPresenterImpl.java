package com.example.android.bgdb.presenter;

import android.content.CursorLoader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.FavouriteListLoaderCallbacks;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.fragment.FavouriteListView;

import java.util.List;

/**
 * Initialises a {@link CursorLoader} and sends the returned {@link List} of
 * {@link BoardGame} objects to the view.
 */
public class FavouriteListPresenterImpl implements FavouriteListPresenter {

    private static final int FAVOURITES_LOADER_ID = 201;

    private FavouriteListView favouriteListView;

    public FavouriteListPresenterImpl(FavouriteListView favouriteListView) {
        this.favouriteListView = favouriteListView;
    }

    @Override
    public void createLoader(ContextWrapper contextWrapper) {
        favouriteListView.getSupportLoaderManager()
                .initLoader(FAVOURITES_LOADER_ID, null, new FavouriteListLoaderCallbacks(this, contextWrapper));
    }

    @Override
    public void onLoadFinished(List<BoardGame> boardGames) {
        favouriteListView.onPostLoad(boardGames);
    }
}
