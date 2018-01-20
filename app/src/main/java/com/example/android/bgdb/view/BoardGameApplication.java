package com.example.android.bgdb.view;

import android.app.Application;

public class BoardGameApplication extends Application {

    private static BoardGameApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static BoardGameApplication getApplication() {
        return application;
    }
}
