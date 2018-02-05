package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;

import java.util.List;

public interface BasePresenter {

    void onPreLoad();
    void onPostLoad(List<BoardGame> boardGames);
}
