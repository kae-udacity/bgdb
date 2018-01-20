package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.SearchType;

import java.util.List;

public interface PopularListPresenter {

    void load(SearchType searchType);
    void onPreLoad();
    void onPostLoad(List<BoardGame> boardGames);
}
