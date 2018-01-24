package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;

public interface DetailPresenter {

    void load(BoardGame boardGame);
    void onPreLoad();
    void onPostLoad(BoardGame boardGame);
}
