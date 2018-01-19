package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

import java.util.List;

public interface BoardGameListView {

    void onPreLoad();
    void onPostLoad(List<BoardGame> boardGames);
}
