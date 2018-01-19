package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;

import java.util.List;

public interface BoardGameListPresenter {

    void load(SearchType searchType);
    void onPreLoad();
    void onPostLoad(List<BoardGame> boardGames);
}
