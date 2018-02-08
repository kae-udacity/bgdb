package com.example.android.bgdb.view.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.widget.WidgetProvider;
import com.example.android.bgdb.view.NetworkUtil;
import com.example.android.bgdb.view.fragment.BoardGameFragment;
import com.example.android.bgdb.view.fragment.DetailFragment;
import com.example.android.bgdb.view.fragment.DetailFragment.DetailFragmentListener;
import com.example.android.bgdb.view.fragment.FragmentListener;
import com.example.android.bgdb.view.fragment.MasterFragment;
import com.example.android.bgdb.view.fragment.ListFragmentListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements
        FragmentListener,
        ListFragmentListener,
        DetailFragmentListener {

    private Menu menu;
    private int result = Activity.RESULT_CANCELED;
    private int drawableId = -1;
    private int colorId = -1;
    private BoardGame selectedBoardGame;
    private BoardGameFragment boardGameFragment;

    @BindView(R.id.detail_container)
    FrameLayout detailContainer;

    @BindView(R.id.detail_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.detail_empty_view)
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Trace trace = null;
        // If Google Play Services is available.
        if (isGooglePlayServicesAvailable()) {
            // Create trace for Firebase Performance and start it.
            trace = FirebasePerformance.getInstance().newTrace("DetailActivity_onCreateTrace");
            trace.start();
        }
        String boardGameFragmentTag = getString(R.string.detail_board_game_fragment_tag);
        setUpDetailBoardGameFragment(boardGameFragmentTag);
        Intent intent = getIntent();
        BoardGame boardGame = null;
        if (intent.hasExtra(getString(R.string.board_game))) {
            boardGame = intent.getParcelableExtra(getString(R.string.board_game));
            setBoardGame(boardGame);
        }

        if (getResources().getBoolean(R.bool.tablet)) {
            setUpMasterFragment();
        }

        if (NetworkUtil.isOnline(this)) {
            if (boardGame != null) {
                setUpDetailFragment();
            } else {
                detailContainer.setVisibility(View.GONE);
                displayEmptyView(getString(R.string.select_board_game));
            }
        } else {
            detailContainer.setVisibility(View.GONE);
            displayEmptyView(getString(R.string.no_network_connection));
        }

        // If trace is not null, stop it.
        if (trace != null) {
            trace.stop();
        }
    }

    public boolean isGooglePlayServicesAvailable(){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        return resultCode == ConnectionResult.SUCCESS;
    }

    private void setUpDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment detailFragment = fragmentManager.findFragmentByTag(getString(R.string.detail_fragment));

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, detailFragment, getString(R.string.detail_fragment))
                .commit();
    }

    private void setUpMasterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment masterFragment = fragmentManager.findFragmentByTag(getString(R.string.master_fragment));

        if (masterFragment == null) {
            masterFragment = MasterFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.master_container, masterFragment, getString(R.string.master_fragment))
                .commit();
    }

    private void setUpDetailBoardGameFragment(String boardGameFragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        boardGameFragment = (BoardGameFragment) fragmentManager
                .findFragmentByTag(boardGameFragmentTag);

        if (boardGameFragment == null) {
            boardGameFragment = new BoardGameFragment();

            fragmentManager.beginTransaction()
                    .add(boardGameFragment, boardGameFragmentTag)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.result_code), result);
        outState.putParcelable(getString(R.string.selected_board_game), selectedBoardGame);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(getString(R.string.result_code))) {
            result = savedInstanceState.getInt(getString(R.string.result_code));
        }

        if (savedInstanceState.containsKey(getString(R.string.selected_board_game))) {
            selectedBoardGame = savedInstanceState.getParcelable(getString(R.string.selected_board_game));
        }
    }

    @Override
    public void onBackPressed() {
        // If launched via widget on tablet.
        if (getResources().getBoolean(R.bool.tablet)) {
            // App should exit.
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
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
    public void setUpActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        boolean enabled = !getResources().getBoolean(R.bool.tablet);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    @Override
    public void setActionBarTitle(String name) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }
    }

    @Override
    public BoardGame getBoardGame() {
        if (boardGameFragment.getBoardGames() == null
                || boardGameFragment.getBoardGames().isEmpty()) {
            return null;
        }
        return boardGameFragment.getBoardGames().get(0);
    }

    @Override
    public void setBoardGame(BoardGame boardGame) {
        List<BoardGame> boardGames = new ArrayList<>();
        boardGames.add(boardGame);
        boardGameFragment.setBoardGames(boardGames);
    }

    @Override
    public void onPreLoad() {
        detailContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostLoad() {
        detailContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        progressBar.setVisibility(View.GONE);
        if (boardGame == null) {
            if (NetworkUtil.isOnline(this)) {
                displayEmptyView(getString(R.string.please_try_again));
            } else {
                displayEmptyView(getString(R.string.no_network_connection));
            }
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
    public void updateFavourite() {
        // Update result so that favourites list can be updated in master list view.
        result = Activity.RESULT_OK;
        if (getResources().getBoolean(R.bool.tablet)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(getString(R.string.favourites));

            if (fragment != null) {
                fragment.onActivityResult(getResources().getInteger(R.integer.request_code), result, null);
            }
        }
    }

    @Override
    public void updateWidget() {
        // Update widget so that favourites list can be updated in master list view.
        Intent intent = new Intent(this, WidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public void finish() {
        setResult(result);
        super.finish();
    }

    @Override
    public void displayEmptyView(String message) {
        detailContainer.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(message);
    }

    @Override
    public void showDetailFragment(BoardGame boardGame) {
        selectedBoardGame = boardGame;
        setBoardGame(boardGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, DetailFragment.newInstance(), getString(R.string.detail_fragment))
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
