package com.example.android.bgdb.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.view.fragment.BaseListViewImpl;
import com.example.android.bgdb.view.fragment.BoardGameFragment;
import com.example.android.bgdb.view.fragment.ListFragmentListener;
import com.example.android.bgdb.view.fragment.MasterFragmentListener;
import com.example.android.bgdb.view.fragment.PopularListFragment;

public abstract class BaseActivity extends AppCompatActivity implements
        ListFragmentListener,
        MasterFragmentListener {

    @Override
    public void setToolbarTitle(Toolbar toolbar, String listFragmentTag) {
        if (getResources().getBoolean(R.bool.tablet)) {
            toolbar.setTitle(listFragmentTag);
        }
    }

    @Override
    public void setUpMasterActionBar(Toolbar toolbar) {
        if (getResources().getBoolean(R.bool.tablet)) {
            return;
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setUpListFragment(String listFragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment selectedFragment = fragmentManager.findFragmentByTag(listFragmentTag);
        if (selectedFragment == null) {
            //Manually displaying the first fragment - one time only
            selectedFragment = PopularListFragment.newInstance
                    (SearchType.HOT, R.string.the_hotness_board_game_tag);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.list_container, selectedFragment, listFragmentTag)
                .commit();
    }


    @Override
    public void updateListFragment(BaseListViewImpl listFragment, String listFragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.list_container, listFragment, listFragmentTag)
                    .commit();
        }
    }

    @Override
    public void updateCurrentFragment(BaseListViewImpl listFragment, String listFragmentTag, int boardGameFragmentTagId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            BaseListViewImpl selectedFragment =
                    (BaseListViewImpl) fragmentManager.findFragmentByTag(listFragmentTag);
            if (selectedFragment.getScrollOffset() > 0) {
                selectedFragment.resetScrollPosition();
            } else {
                BoardGameFragment boardGameFragment = (BoardGameFragment) fragmentManager
                        .findFragmentByTag(getString(boardGameFragmentTagId));
                if (boardGameFragment != null) {
                    boardGameFragment.clearBoardGames();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.list_container, listFragment, listFragmentTag)
                        .commit();
            }
        }
    }

    @Override
    public BoardGameFragment getBoardGameFragment(String boardGameFragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if we have retained the worker fragment.
        BoardGameFragment boardGameFragment = (BoardGameFragment)
                fragmentManager.findFragmentByTag(boardGameFragmentTag);
        // If not retained (or first time running), we need to create it.
        if (boardGameFragment == null) {
            boardGameFragment = BoardGameFragment.newInstance();
            // Tell it who it is working with.
            fragmentManager.beginTransaction()
                    .add(boardGameFragment, boardGameFragmentTag)
                    .commit();
        }
        return boardGameFragment;
    }
}
