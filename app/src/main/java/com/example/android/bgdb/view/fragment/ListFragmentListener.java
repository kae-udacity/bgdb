package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.model.BoardGame;

public interface ListFragmentListener {

    void showDetailFragment(BoardGame boardGame);
    BoardGameFragment getBoardGameFragment(String boardGameFragmentTag);
}
