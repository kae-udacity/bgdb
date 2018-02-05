package com.example.android.bgdb.model.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.PopularListPresenter;
import com.example.android.bgdb.model.SearchType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Retrieves board game data from URL and returns a {@link List} of {@link BoardGame} objects.
 */
public class ListAsyncTask extends AsyncTask<SearchType, Void, List<BoardGame>> {

    private static final String TAG = ListAsyncTask.class.getSimpleName();
    private PopularListPresenter popularListPresenter;

    public ListAsyncTask(PopularListPresenter popularListPresenter) {
        this.popularListPresenter = popularListPresenter;
    }

    @Override
    protected void onPreExecute() {
        popularListPresenter.onPreLoad();
    }

    @Override
    protected List<BoardGame> doInBackground(SearchType... searchTypes) {
        SearchType searchType = searchTypes[0];
        URL url = UrlBuilder.getListUrl(searchType);
        try {
            // get url and open HTTP connection
            Document document = Jsoup.parse(url, 30000);
            List<BoardGame> boardGames = null;
            if (searchType == SearchType.HOT) {
                boardGames = BoardGameParser.getListFromXml(document);
            } else if (searchType == SearchType.TOP) {
                boardGames = BoardGameParser.getListFromHtml(document);
            }
            return boardGames;
        } catch(IOException e) {
            Log.e(TAG, "Error retrieving data.", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BoardGame> boardGames) {
        popularListPresenter.onPostLoad(boardGames);
    }
}
