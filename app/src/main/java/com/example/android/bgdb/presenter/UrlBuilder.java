package com.example.android.bgdb.presenter;

import android.net.Uri;
import android.util.Log;

import com.example.android.bgdb.model.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Builds the {@link URL} using the provided {@link SearchType}.
 */

class UrlBuilder {

    private static final String TAG = UrlBuilder.class.getSimpleName();

    private UrlBuilder() {

    }

    static URL getUrl(SearchType searchType) {
        Uri uri = getUri(searchType);
        if (uri == null) {
            return null;
        }
        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error getting url.", e);
            return null;
        }
        return url;
    }

    private static Uri getUri(SearchType searchType) {
        Uri uri = Uri.parse(Constants.BASE_URL);
        switch (searchType) {
            case HOT:
                uri = uri.buildUpon()
                        .appendPath(Constants.API)
                        .appendPath(Constants.HOT)
                        .appendQueryParameter(Constants.TYPE, Constants.BOARD_GAME)
                        .build();
                break;
            case TOP:
                uri = uri.buildUpon()
                        .appendPath(Constants.BROWSE)
                        .appendPath(Constants.BOARD_GAME)
                        .build();
                break;
            default:
                return null;
        }
        return uri;
    }
}
