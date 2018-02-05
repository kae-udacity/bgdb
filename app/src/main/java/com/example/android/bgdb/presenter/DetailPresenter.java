package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.loaderlistener.LoaderListener;

public interface DetailPresenter {

    void load(BoardGame boardGame);
    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
    void query(ContextWrapper contextWrapper, LoaderListener listener, String apiId);
    void onPreQuery();
    void onPostQuery(boolean favourite);
    void insert(ContextWrapper contextWrapper, LoaderListener listener, BoardGame boardGame);
    void onPostInsert(boolean successful);
    void delete(ContextWrapper contextWrapper, LoaderListener listener, String apiId);
    void onPostDelete(int rowsDeleted);
}
