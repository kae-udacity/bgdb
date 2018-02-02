package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

public interface DetailView {

    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
}
