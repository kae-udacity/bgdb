package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

public interface DetailView {

    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
    void onPostQuery(boolean favourite);
    void onPostDelete(int rowsDeleted);
    void onPostInsert(boolean insertSuccessful);
}
