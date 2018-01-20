package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.presenter.task.BoardGameAsyncTask;
import com.example.android.bgdb.view.fragment.PopularListView;

import java.util.List;

/**
 * Executes a {@link BoardGameAsyncTask} to retrieve a {@link List} of {@link BoardGame} objects.
 * Sends the list onto the View when the list is retrieved.
 */

public class PopularListPresenterImpl implements PopularListPresenter {

    private PopularListView popularListView;

    public PopularListPresenterImpl(PopularListView popularListView) {
        this.popularListView = popularListView;
    }

    @Override
    public void load(SearchType searchType) {
        BoardGameAsyncTask boardGameTask = new BoardGameAsyncTask(this);
        boardGameTask.execute(searchType);
    }

    @Override
    public void onPreLoad() {
        popularListView.onPreLoad();
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        popularListView.onPostLoad(boardGames);
    }
}
