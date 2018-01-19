package com.example.android.bgdb.presenter;

import android.support.annotation.NonNull;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.Constants;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the {@link Document} and extracts a {@link List} of {@link BoardGame} objects.
 * Parses XML and HTML documents.
 */

class Parser {

    static List<BoardGame> getListFromXml(Document document) {
        List<BoardGame> boardGames = new ArrayList<>();
        Elements elements = document.select("item");

        BoardGame boardGame;
        for (Element element : elements) {
            boardGame = new BoardGame();
            boardGame.setId(element.attr("id"));
            boardGame.setName(element.select("name").val());
            boardGame.setRank(element.attr("rank"));
            boardGame.setYear(element.select("yearpublished").val());
            boardGame.setThumbnailUrl(element.select("thumbnail").val());
            boardGames.add(boardGame);
        }
        return boardGames;
    }

    static List<BoardGame> getListFromHtml(Document document) {
        List<BoardGame> boardGames = new ArrayList<>();
        Elements elements = document.select(Constants.COLLECTION_TABLE_ROWS);

        BoardGame boardGame;
        for (Element element : elements) {
            boardGame = getBoardGame(element);
            boardGames.add(boardGame);
        }
        return boardGames;
    }

    @NonNull
    private static BoardGame getBoardGame(Element element) {
        BoardGame boardGame = new BoardGame();
        Elements name = element.select(Constants.COLLECTION_OBJECT_NAME);
        boardGame.setId(getId(name));
        boardGame.setName(name.text());
        boardGame.setYear(getYear(element));
        boardGame.setThumbnailUrl(getThumbnailUrl(element));
        boardGame.setRank(element.select(Constants.COLLECTION_RANK).text());
        return boardGame;
    }

    private static String getYear(Element element) {
        return element.select(Constants.COLLECTION_YEAR).text().replaceAll("[()]", "");
    }

    private static String getThumbnailUrl(Element element) {
        String src = element.select(Constants.COLLECTION_THUMBNAIL).attr(Constants.SRC);
        return src.replace(Constants.THUMBNAIL_SUFFIX, Constants.IMAGE_SUFFIX);
    }

    private static String getId(Elements name) {
        String s = name.attr(Constants.HREF);
        String[] segments = s.split("/");
        return segments[segments.length - 2];
    }
}
