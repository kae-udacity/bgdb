package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.ContextWrapper;

public interface DetailPresenter {

    void load(BoardGame boardGame);
    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
    void query(ContextWrapper contextWrapper, String apiId);
    void onPostQuery(boolean favourite);
    void insert(ContextWrapper contextWrapper, BoardGame boardGame);
    void onPostInsert(boolean insertSuccessful);
    void delete(ContextWrapper contextWrapper, String apiId);
    void onPostDelete(int rowsDeleted);
}
