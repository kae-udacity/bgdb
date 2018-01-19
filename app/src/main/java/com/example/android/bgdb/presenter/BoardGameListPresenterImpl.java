package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.fragment.BoardGameListView;

import java.util.List;

/**
 * Executes a {@link BoardGameAsyncTask} to retrieve a {@link List} of {@link BoardGame} objects.
 * Sends the list onto the View when the list is retrieved.
 */

public class BoardGameListPresenterImpl implements BoardGameListPresenter {

    private BoardGameListView boardGameListView;

    public BoardGameListPresenterImpl(BoardGameListView boardGameListView) {
        this.boardGameListView = boardGameListView;
    }

    @Override
    public void load(SearchType searchType) {
        BoardGameAsyncTask boardGameTask = new BoardGameAsyncTask(this);
        boardGameTask.execute(searchType);
    }

    @Override
    public void onPreLoad() {
        boardGameListView.onPreLoad();
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        boardGameListView.onPostLoad(boardGames);
    }
}
