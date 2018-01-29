package com.example.android.bgdb.view.fragment;

import android.support.v4.app.LoaderManager;

/**
 * Must be implemented to communicate with presenter.
 */
public interface LoaderManagerView {

    LoaderManager getSupportLoaderManager();
}
