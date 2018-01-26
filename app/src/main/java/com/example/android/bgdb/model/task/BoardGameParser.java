package com.example.android.bgdb.model.task;

import android.support.annotation.NonNull;

import com.example.android.bgdb.model.BoardGame;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the {@link Document} and extracts a {@link List} of {@link BoardGame} objects.
 * Parses XML and HTML documents.
 */
class BoardGameParser {

    private static final String ITEM = "item";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String RANK = "rank";
    private static final String YEAR_PUBLISHED = "yearpublished";
    private static final String THUMBNAIL = "thumbnail";
    private static final String IMAGE = "image";
    private static final String VALUE = "value";
    private static final String NOT_RANKED = "Not Ranked";
    private static final String NAME_FRIENDLY = "friendlyname";
    private static final String RANK_BOARD_GAME = "Board Game Rank";
    private static final String RANK_OVERALL = "Overall Rank";
    private static final String AVERAGE = "average";
    private static final String DESCRIPTION = "description";
    private static final String COLLECTION_OBJECT_NAME = ".collection_objectname a";
    private static final String COLLECTION_YEAR = ".collection_objectname span[class=smallerfont dull]";
    private static final String COLLECTION_THUMBNAIL = ".collection_thumbnail img[src]";
    private static final String COLLECTION_RANK = ".collection_rank";
    private static final String SRC = "src";
    private static final String THUMBNAIL_SUFFIX = "_mt";
    private static final String IMAGE_SUFFIX = "_t";
    private static final String HREF = "href";
    private static final String COLLECTION_TABLE = ".collection_table ";
    private static final String ROWS = "tr[id=row_]";
    private static final String COLLECTION_TABLE_ROWS = COLLECTION_TABLE + ROWS;

    static List<BoardGame> getListFromXml(Document document) {
        List<BoardGame> boardGames = new ArrayList<>();
        Elements elements = document.select(ITEM);

        BoardGame boardGame;
        for (Element element : elements) {
            boardGame = new BoardGame();
            boardGame.setId(element.attr(ID));
            boardGame.setName(element.select(NAME).val());
            boardGame.setRank(element.attr(RANK));
            boardGame.setYear(element.select(YEAR_PUBLISHED).val());
            boardGame.setThumbnailUrl(element.select(THUMBNAIL).val());
            boardGames.add(boardGame);
        }
        return boardGames;
    }

    static List<BoardGame> getListFromHtml(Document document) {
        List<BoardGame> boardGames = new ArrayList<>();
        Elements elements = document.select(COLLECTION_TABLE_ROWS);

        BoardGame boardGame;
        for (Element element : elements) {
            boardGame = getBoardGameFromHtml(element);
            boardGames.add(boardGame);
        }
        return boardGames;
    }

    static void getBoardGameDetailFromXml(BoardGame boardGame, Document document) {
        Element element = document.select(ITEM).first();

        if (element != null) {
            boardGame.setBannerUrl(element.select(IMAGE).text());
            boardGame.setRanks(getRanks(element));
            boardGame.setRating(element.select(AVERAGE).val());
            boardGame.setDescription(getDescription(element));
        }
    }

    private static String getRanks(Element element) {
        // Parses the document for the elements with the rank tag.
        Elements elements = element.select(RANK);
        StringBuilder builder = new StringBuilder();
        for (Element rankElement : elements) {
            String value = rankElement.attr(VALUE);
            // If the value of the rank is "Not Ranked", add it.
            if (value.equals(NOT_RANKED)) {
                builder.append(value);
            } else {    // Else add the name of the rank and the value.
                builder.append(rankElement.attr(NAME_FRIENDLY))
                        .append(": ")
                        .append(value);
            }

            // If element is not the last one, add a new line.
            if (rankElement != elements.last()) {
                builder.append("\n");
            }
        }

        // Replace Board Game Rank with Overall Rank.
        return builder.toString().replace(RANK_BOARD_GAME, RANK_OVERALL);
    }

    private static String getDescription(Element element) {
        String description = element.select(DESCRIPTION).text();
        description = Parser.unescapeEntities(description, false);
        return description;
    }

    @NonNull
    private static BoardGame getBoardGameFromHtml(Element element) {
        BoardGame boardGame = new BoardGame();
        Elements name = element.select(COLLECTION_OBJECT_NAME);
        boardGame.setId(getId(name));
        boardGame.setName(name.text());
        boardGame.setYear(getYear(element));
        boardGame.setThumbnailUrl(getThumbnailUrl(element));
        boardGame.setRank(element.select(COLLECTION_RANK).text());
        return boardGame;
    }

    private static String getYear(Element element) {
        return element.select(COLLECTION_YEAR).text().replaceAll("[()]", "");
    }

    private static String getThumbnailUrl(Element element) {
        String src = element.select(COLLECTION_THUMBNAIL).attr(SRC);
        return src.replace(THUMBNAIL_SUFFIX, IMAGE_SUFFIX);
    }

    private static String getId(Elements name) {
        String s = name.attr(HREF);
        String[] segments = s.split("/");
        return segments[segments.length - 2];
    }
}
