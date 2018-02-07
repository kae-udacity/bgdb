package com.example.android.bgdb.model.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;
import com.example.android.bgdb.model.database.BoardGameDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles requests from the remote adapter for RemoteViews.
 */
public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<BoardGame> boardGames;

    ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        BoardGameDatabaseHelper helper = new BoardGameDatabaseHelper(context);
        Cursor cursor = helper.getReadableDatabase().query(
                BoardGameEntry.TABLE_NAME, null, null, null, null, null, null);

        if (cursor == null) {
            return;
        }

        boardGames = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            BoardGame boardGame = new BoardGame();

            String apiId = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_API_ID));
            boardGame.setId(apiId);

            String name = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_NAME));
            boardGame.setName(name);

            String year = cursor.getString(cursor.getColumnIndex(BoardGameEntry.COLUMN_YEAR));
            boardGame.setYear(year);

            byte[] thumbnailBlob = cursor.getBlob(cursor.getColumnIndex(BoardGameEntry.COLUMN_THUMBNAIL));
            boardGame.setThumbnailBlob(thumbnailBlob);

            boardGames.add(boardGame);
        }
        cursor.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (boardGames == null) {
            return 0;
        }
        return boardGames.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (boardGames == null || boardGames.size() == 0) {
            return null;
        }

        BoardGame boardGame = boardGames.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_list_item);
        views.setTextViewText(R.id.widget_text_view_name, boardGame.getName());
        views.setTextViewText(R.id.widget_text_view_year, boardGame.getYear());

        Bitmap thumbnail = BitmapFactory.decodeByteArray(
                boardGame.getThumbnailBlob(),
                0,
                boardGame.getThumbnailBlob().length);
        views.setImageViewBitmap(R.id.widget_image_view_thumbnail, thumbnail);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(context.getString(R.string.board_game), boardGames.get(position));
        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
