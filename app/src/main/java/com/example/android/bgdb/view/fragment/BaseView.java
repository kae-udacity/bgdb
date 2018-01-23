package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

import java.util.List;

/**
 * Must be implemented to communicate with presenter.
 */
public interface BaseView {

    void onPostLoad(List<BoardGame> boardGames);
}
