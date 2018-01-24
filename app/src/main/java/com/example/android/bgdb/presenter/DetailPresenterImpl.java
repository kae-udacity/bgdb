package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.task.DetailAsyncTask;
import com.example.android.bgdb.view.fragment.DetailView;

/**
 * Executes a {@link DetailAsyncTask} to retrieve the board game data.
 * Sends the board game with the data to the view.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private DetailView detailView;

    public DetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void load(BoardGame boardGame) {
        new DetailAsyncTask(this).execute(boardGame);
    }

    @Override
    public void onPreLoad() {
        detailView.onPreLoad();
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        detailView.onPostLoad(boardGame);
    }
}
