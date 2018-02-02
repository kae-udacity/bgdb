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
 * Handles selection events on bottom navigation items.
 */

public class BottomNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private OnNavigationItemSelectedCallback callback;

    public BottomNavigationItemSelectedListener(Context context, OnNavigationItemSelectedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BaseListViewImpl listFragment;
        switch (item.getItemId()) {
            case R.id.action_the_hotness:
                listFragment = PopularListFragment.newInstance(
                        SearchType.HOT,
                        R.string.the_hotness_board_game_tag);
                callback.updateListFragment(listFragment, context.getString(R.string.the_hotness));
                break;
            case R.id.action_top_100:
                listFragment = PopularListFragment.newInstance(
                        SearchType.TOP,
                        R.string.top_100_board_game_tag);
                callback.updateListFragment(listFragment, context.getString(R.string.top_100));
                break;
            case R.id.action_favorites:
                listFragment = FavouriteListFragment.newInstance(R.string.favourites_board_game_tag);
                callback.updateListFragment(listFragment, context.getString(R.string.favourites));
                break;
        }
        return true;
    }

    public interface OnNavigationItemSelectedCallback {
        void updateListFragment(BaseListViewImpl listFragment, String listFragmentTag);
    }
}
