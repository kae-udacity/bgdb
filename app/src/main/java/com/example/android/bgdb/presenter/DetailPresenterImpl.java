package com.example.android.bgdb.presenter;

import android.support.v4.app.LoaderManager;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.DeleteFavouriteLoaderCallbacks;
import com.example.android.bgdb.model.database.InsertFavouriteLoaderCallbacks;
import com.example.android.bgdb.model.database.QueryFavouriteLoaderCallbacks;
import com.example.android.bgdb.model.task.DetailAsyncTask;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.fragment.DetailView;
import com.example.android.bgdb.view.fragment.LoaderManagerView;

/**
 * Executes a {@link DetailAsyncTask} to retrieve the board game data.
 * Sends the board game with the data to the view.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private static final int DELETE_FAVOURITE_LOADER_ID = 202;
    private static final int INSERT_FAVOURITE_LOADER_ID = 203;
    private static final int QUERY_FAVOURITE_LOADER_ID = 204;

    private LoaderManagerView loaderManagerView;
    private DetailView detailView;

    public DetailPresenterImpl(LoaderManagerView loaderManagerView, DetailView detailView) {
        this.loaderManagerView = loaderManagerView;
        this.detailView = detailView;
    }

    @Override
    public void load(BoardGame boardGame) {
        new DetailAsyncTask(this).execute(boardGame);
    }

    @Override
    public void onPreLoad() {
        detailView.onPreLoad();
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        detailView.onPostLoad(boardGame);
    }

    @Override
    public void query(ContextWrapper contextWrapper, String apiId) {
        QueryFavouriteLoaderCallbacks callbacks =
                new QueryFavouriteLoaderCallbacks(contextWrapper, this, apiId);
        LoaderManager loaderManager = loaderManagerView.getSupportLoaderManager();
        if (loaderManager.getLoader(QUERY_FAVOURITE_LOADER_ID) == null) {
            loaderManager.initLoader(QUERY_FAVOURITE_LOADER_ID, null, callbacks);
        } else {
            loaderManager.restartLoader(QUERY_FAVOURITE_LOADER_ID, null, callbacks);
        }
    }

    @Override
    public void onPostQuery(boolean favourite) {
        detailView.onPostQuery(favourite);
    }

    @Override
    public void insert(ContextWrapper contextWrapper, BoardGame boardGame) {
        InsertFavouriteLoaderCallbacks callbacks =
                new InsertFavouriteLoaderCallbacks(contextWrapper, this, boardGame);
        LoaderManager loaderManager = loaderManagerView.getSupportLoaderManager();
        if (loaderManager.getLoader(INSERT_FAVOURITE_LOADER_ID) == null) {
            loaderManager.initLoader(INSERT_FAVOURITE_LOADER_ID, null, callbacks);
        } else {
            loaderManager.restartLoader(INSERT_FAVOURITE_LOADER_ID, null, callbacks);
        }
    }

    @Override
    public void onPostInsert(boolean insertSuccessful) {
        detailView.onPostInsert(insertSuccessful);
    }

    @Override
    public void delete(ContextWrapper contextWrapper, String apiId) {
        DeleteFavouriteLoaderCallbacks callbacks =
                new DeleteFavouriteLoaderCallbacks(contextWrapper, this, apiId);
        LoaderManager loaderManager = loaderManagerView.getSupportLoaderManager();
        if (loaderManager.getLoader(DELETE_FAVOURITE_LOADER_ID) == null) {
            loaderManager.initLoader(DELETE_FAVOURITE_LOADER_ID, null, callbacks);
        } else {
            loaderManager.restartLoader(DELETE_FAVOURITE_LOADER_ID, null, callbacks);
        }
    }

    @Override
    public void onPostDelete(int rowsDeleted) {
        detailView.onPostDelete(rowsDeleted);
    }
}
