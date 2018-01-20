package com.example.android.bgdb.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.android.bgdb.model.BoardGame;

/**
 * Must be implemented to handle the click events on a board game item in the {@link RecyclerView}.
 */

public interface BoardGameOnClickHandler {
    void onClick(BoardGame boardGame);
}
