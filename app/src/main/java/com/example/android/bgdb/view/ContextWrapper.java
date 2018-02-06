package com.example.android.bgdb.view;

import android.content.Context;

/**
 * Provides access to {@link Context}.
 */

public class ContextWrapper {

    private Context context;

    public ContextWrapper(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
