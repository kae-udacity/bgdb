package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.model.task.ListAsyncTask;
import com.example.android.bgdb.view.fragment.BaseListView;
import com.example.android.bgdb.view.fragment.BoardGameView;

import java.util.List;

/**
 * Executes a {@link ListAsyncTask} to retrieve a {@link List} of {@link BoardGame} objects.
 * Sends the list onto the View when the list is retrieved.
 */
public class PopularListPresenterImpl implements PopularListPresenter {

    private BaseListView popularListView;
    private BoardGameView boardGameView;

    public PopularListPresenterImpl(BaseListView popularListView, BoardGameView boardGameView) {
        this.popularListView = popularListView;
        this.boardGameView = boardGameView;
    }

    @Override
    public void load(SearchType searchType) {
        new ListAsyncTask(this).execute(searchType);
    }

    @Override
    public void onPreLoad() {
        popularListView.onPreLoad();
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        boardGameView.setBoardGames(boardGames);
        popularListView.onPostLoad(boardGames);
    }
}
