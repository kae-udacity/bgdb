package com.example.android.bgdb.model.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * Retrieves board game data from URL and returns it.
 */

public class DetailAsyncTask extends AsyncTask<BoardGame, BoardGame, BoardGame> {

    private static final String TAG = DetailAsyncTask.class.getSimpleName();
    private DetailPresenter presenter;

    public DetailAsyncTask(DetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPreExecute() {
        presenter.onPreLoad();
    }

    @Override
    protected BoardGame doInBackground(BoardGame... boardGames) {
        BoardGame boardGame = boardGames[0];
        URL url = UrlBuilder.getDetailUrl(boardGame.getId());
        try {
            Document document = Jsoup.parse(url, 30000);
            Parser.getBoardGameDetailFromXml(boardGame, document);
            return boardGame;
        } catch(IOException e) {
            Log.e(TAG, "Error retrieving data.", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(BoardGame boardGame) {
        presenter.onPostLoad(boardGame);
    }
}
