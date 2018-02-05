package com.example.android.bgdb.model.database;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.database.BoardGameContract.BoardGameEntry;

/**
 * Deletes a {@link BoardGame} from the favourites.
 */
public class DeleteFavouriteLoader extends AsyncTaskLoader<Integer> {

    private String apiId;
    private Integer rowsDeleted;

    DeleteFavouriteLoader(Context context, String apiId) {
        super(context);
        this.apiId = apiId;
    }

    @Override
    protected void onStartLoading() {
        if (rowsDeleted == null) {
            forceLoad();
        } else {
            deliverResult(rowsDeleted);
        }
    }

    @Override
    public Integer loadInBackground() {
        Uri uri = BoardGameEntry.CONTENT_URI
                .buildUpon()
                .appendPath(BoardGameEntry.COLUMN_API_ID)
                .appendPath(apiId)
                .build();

        return getContext().getContentResolver().delete(uri, null, null);
    }

    @Override
    public void deliverResult(@Nullable Integer rowsDeleted) {
        this.rowsDeleted = rowsDeleted;
        super.deliverResult(rowsDeleted);
    }
}
