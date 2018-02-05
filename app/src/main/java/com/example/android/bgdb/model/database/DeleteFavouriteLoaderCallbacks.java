package com.example.android.bgdb.model.database;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.view.ContextWrapper;

/**
 * Creates a loader to delete a {@link BoardGame} from the favourites using the
 * {@link ContentResolver}.
 */
public class DeleteFavouriteLoaderCallbacks implements LoaderManager.LoaderCallbacks<Integer> {

    private ContextWrapper contextWrapper;
    private DetailPresenter presenter;
    private String apiId;

    public DeleteFavouriteLoaderCallbacks(
            ContextWrapper contextWrapper,
            DetailPresenter presenter,
            String apiId) {
        this.contextWrapper = contextWrapper;
        this.presenter = presenter;
        this.apiId = apiId;
    }

    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle bundle) {
        return new DeleteFavouriteLoader(contextWrapper.getContext(), apiId);
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer rowsDeleted) {
        presenter.onPostDelete(rowsDeleted);
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }
}
