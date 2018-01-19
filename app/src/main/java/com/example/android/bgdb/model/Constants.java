package com.example.android.bgdb.model;

/**
 * Provides access to constant variables.
 */

public class Constants {

    public static final String BASE_URL = "https://www.boardgamegeek.com/";
    public static final String API = "xmlapi2";
    public static final String HOT = "hot";
    public static final String BROWSE = "browse";
    public static final String TYPE = "type";
    public static final String BOARD_GAME = "boardgame";
    private static final String COLLECTION_TABLE = ".collection_table ";
    private static final String ROWS = "tr[id=row_]";
    public static final String COLLECTION_TABLE_ROWS = COLLECTION_TABLE + ROWS;
    public static final String COLLECTION_OBJECT_NAME = ".collection_objectname a";
    public static final String COLLECTION_YEAR = ".collection_objectname span[class=smallerfont dull]";
    public static final String COLLECTION_THUMBNAIL = ".collection_thumbnail img[src]";
    public static final String COLLECTION_RANK = ".collection_rank";
    public static final String SRC = "src";
    public static final String THUMBNAIL_SUFFIX = "_mt";
    public static final String IMAGE_SUFFIX = "_t";
    public static final String HREF = "href";

}
