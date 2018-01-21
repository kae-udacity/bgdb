package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.ContextWrapper;

import java.util.List;

public interface FavouriteListPresenter {

    void createLoader(ContextWrapper contextWrapper);
    void onLoadFinished(List<BoardGame> boardGames);
}
