package com.example.android.bgdb.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.view.fragment.BaseListViewImpl;
import com.example.android.bgdb.view.fragment.FavouriteListFragment;
import com.example.android.bgdb.view.fragment.PopularListFragment;

/**
 * Handles reselection events on bottom navigation items.
 */

public class BottomNavigationItemReselectedListener implements BottomNavigationView.OnNavigationItemReselectedListener {

    private Context context;
    private OnNavigationItemReselectedCallback callback;

    public BottomNavigationItemReselectedListener(Context context, OnNavigationItemReselectedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        BaseListViewImpl listFragment;
        switch (item.getItemId()) {
            case R.id.action_the_hotness:
                listFragment = PopularListFragment.newInstance(
                        SearchType.HOT,
                        R.string.the_hotness_board_game_tag);
                callback.updateCurrentFragment(
                        listFragment,
                        context.getString(R.string.the_hotness),
                        R.string.the_hotness_board_game_tag);
                break;
            case R.id.action_top_100:
                listFragment = PopularListFragment.newInstance(
                        SearchType.TOP,
                        R.string.top_100_board_game_tag);
                callback.updateCurrentFragment(
                        listFragment,
                        context.getString(R.string.top_100),
                        R.string.top_100_board_game_tag);
                break;
            case R.id.action_favorites:
                listFragment = FavouriteListFragment.newInstance(R.string.favourites_board_game_tag);
                callback.updateCurrentFragment(
                        listFragment,
                        context.getString(R.string.favourites),
                        R.string.favourites_board_game_tag);
                break;
        }
    }

    public interface OnNavigationItemReselectedCallback {
        void updateCurrentFragment(BaseListViewImpl listFragment, String listFragmentTag, int boardGameFragmentTagId);
    }
}
