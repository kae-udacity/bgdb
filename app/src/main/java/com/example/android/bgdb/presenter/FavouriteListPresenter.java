package com.example.android.bgdb.presenter;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

public interface FavouriteListPresenter {

    void createLoader(LoaderManager loaderManager);
    void onLoadFinished(Cursor cursor);
}
