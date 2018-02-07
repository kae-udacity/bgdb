package com.example.android.bgdb.presenter;

import android.content.CursorLoader;
import android.support.v4.app.LoaderManager;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.QueryFavouriteListLoaderCallbacks;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.fragment.BaseView;
import com.example.android.bgdb.view.fragment.LoaderManagerView;

import java.util.List;

/**
 * Initialises a {@link CursorLoader} and sends the returned {@link List} of
 * {@link BoardGame} objects to the view.
 */
public class FavouriteListPresenterImpl implements FavouriteListPresenter {

    private static final int FAVOURITES_LOADER_ID = 201;

    private LoaderManagerView loaderManagerView;
    private BaseView baseListView;

    public FavouriteListPresenterImpl(LoaderManagerView loaderManagerView, BaseView baseListView) {
        this.loaderManagerView = loaderManagerView;
        this.baseListView = baseListView;
    }

    @Override
    public void load(ContextWrapper contextWrapper) {
        QueryFavouriteListLoaderCallbacks callbacks =
                new QueryFavouriteListLoaderCallbacks(contextWrapper, this);
        LoaderManager loaderManager = loaderManagerView.getSupportLoaderManager();
        if (loaderManager.getLoader(FAVOURITES_LOADER_ID) == null) {
            loaderManager.initLoader(FAVOURITES_LOADER_ID, null, callbacks);
        } else {
            loaderManager.restartLoader(FAVOURITES_LOADER_ID, null, callbacks);
        }
    }

    @Override
    public void onPreLoad() {
        baseListView.onPreLoad();
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        baseListView.onPostLoad(boardGames);
    }
}
