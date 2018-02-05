package com.example.android.bgdb.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.android.bgdb.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (getResources().getBoolean(R.bool.tablet)) {
            intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, MasterActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
