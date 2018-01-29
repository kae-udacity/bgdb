package com.example.android.bgdb.view.activity;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.fragment.DetailFragment;
import com.example.android.bgdb.view.fragment.DetailFragment.OnDetailFragmentInteractionListener;
import com.example.android.bgdb.view.fragment.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements
        OnFragmentInteractionListener,
        OnDetailFragmentInteractionListener {

    private Menu menu;
    private int result = Activity.RESULT_CANCELED;
    private int drawableId = -1;
    private int colorId = -1;

    @BindView(R.id.detail_container)
    FrameLayout detailContainer;

    @BindView(R.id.detail_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment detailFragment = fragmentManager.findFragmentByTag(getString(R.string.detail_fragment));

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, detailFragment, getString(R.string.detail_fragment))
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.result_code), result);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(getString(R.string.result_code))) {
            result = savedInstanceState.getInt(getString(R.string.result_code));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        if (drawableId != -1 && colorId != -1) {
            updateFavouriteIcon(drawableId, colorId);
        }
        return true;
    }

    @Override
    public void setActionBarTitle(String name) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }
    }

    @Override
    public void onPreLoad() {
        detailContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        progressBar.setVisibility(View.GONE);
        if (boardGame == null) {
            displayEmptyView();
        } else {
            detailContainer.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            DetailFragment detailFragment = (DetailFragment)
                    fragmentManager.findFragmentByTag(getString(R.string.detail_fragment));

            if (detailFragment != null) {
                detailFragment.updateView(boardGame);
            }
        }
    }

    @Override
    public void updateFavouriteIcon(int drawableId, int colorId) {
        // If user rotates device while database is being queried, menu could be null.
        // If it is, invalidate the menu.
        if (menu == null) {
            this.drawableId = drawableId;
            this.colorId = colorId;
            invalidateOptionsMenu();
        } else {
            MenuItem menuItem = menu.findItem(R.id.action_favourite);
            menuItem.setIcon(ContextCompat.getDrawable(this, drawableId));
            Drawable drawable = menuItem.getIcon();
            if (drawable != null) {
                // If drawable is not mutated, then all drawables with this id will have a color
                // filter applied to it.
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(this, colorId), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public void onPostQuery() {
        detailContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateFavourite() {
        result = Activity.RESULT_OK;
    }

    @Override
    public void finish() {
        setResult(result);
        super.finish();
    }

    private void displayEmptyView() {

    }

    @Override
    public void onFragmentInteraction() {

    }
}
