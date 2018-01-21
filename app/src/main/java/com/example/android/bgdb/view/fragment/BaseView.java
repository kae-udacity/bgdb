package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.ListAdapter;

import java.util.List;

/**
 * Must be implemented to communicate with presenter.
 */
public interface BaseView {

    void onCreateView(ListAdapter adapter);
    void onPreLoad();
    void onPostLoad(List<BoardGame> boardGames);
}
