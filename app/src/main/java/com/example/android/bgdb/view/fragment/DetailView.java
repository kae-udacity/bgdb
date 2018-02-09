package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

public interface DetailView {

    void showEmptyView();
    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
}
