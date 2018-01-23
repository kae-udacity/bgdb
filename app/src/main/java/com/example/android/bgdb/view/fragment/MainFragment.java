package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.SearchType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private String listFragmentTag;
    private BaseListViewImpl listFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            setUpActionBar(activity);
            setUpBottomNavigationBar();
            if (savedInstanceState != null
                    && savedInstanceState.containsKey(getString(R.string.list_fragment_tag))) {
                listFragmentTag = savedInstanceState.getString(getString(R.string.list_fragment_tag));
            } else {
                listFragmentTag = getString(R.string.the_hotness);
            }

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment selectedFragment = fragmentManager.findFragmentByTag(listFragmentTag);
            if (selectedFragment == null) {
                //Manually displaying the first fragment - one time only
                selectedFragment = PopularListFragment.newInstance(
                        getContext(),
                        SearchType.HOT,
                        R.string.the_hotness_board_game_tag);
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.list_container, selectedFragment, listFragmentTag)
                    .commit();
            selectedFragment.setTargetFragment(this, 0);
        }
        return view;
    }

    private void setUpActionBar(AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setUpBottomNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int selectedItemId = bottomNavigationView.getSelectedItemId();
                        int itemId = item.getItemId();
                        switch (item.getItemId()) {
                            case R.id.action_the_hotness:
                                listFragmentTag = getString(R.string.the_hotness);
                                listFragment = PopularListFragment.newInstance(
                                        getContext(),
                                        SearchType.HOT,
                                        R.string.the_hotness_board_game_tag);
                                updateListFragment(selectedItemId, itemId, R.string.the_hotness_board_game_tag);
                                break;
                            case R.id.action_top_100:
                                listFragmentTag = getString(R.string.top_100);
                                listFragment = PopularListFragment.newInstance(
                                        getContext(),
                                        SearchType.TOP,
                                        R.string.top_100_board_game_tag);
                                updateListFragment(selectedItemId, itemId, R.string.top_100_board_game_tag);
                                break;
                            case R.id.action_favorites:
                                listFragmentTag = getString(R.string.top_100);
                                listFragment = PopularListFragment.newInstance(
                                        getContext(),
                                        SearchType.TOP,
                                        R.string.top_100_board_game_tag);
                                updateListFragment(selectedItemId, itemId, R.string.top_100_board_game_tag);
                                break;
                        }
                        return true;
                    }
                });
    }

    private void updateListFragment(int selectedItemId, int itemId, int boardGameFragmentTagId) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        FragmentManager fragmentManager = null;
        if (activity != null) {
            fragmentManager = activity.getSupportFragmentManager();
        }
        if (fragmentManager != null) {
            BaseListViewImpl selectedFragment =
                    (BaseListViewImpl) fragmentManager.findFragmentByTag(listFragmentTag);
            BoardGameFragment boardGameFragment;
            if (selectedItemId == itemId) {
                if (selectedFragment.getScrollOffset() > 0) {
                    selectedFragment.resetScrollPosition();
                } else {
                    selectedFragment = listFragment;
                    boardGameFragment = (BoardGameFragment) fragmentManager
                            .findFragmentByTag(getString(boardGameFragmentTagId));
                    if (boardGameFragment != null) {
                        boardGameFragment.clearBoardGames();
                        boardGameFragment.setTargetFragment(selectedFragment, 1);
                    }
                    selectedFragment.setTargetFragment(MainFragment.this, 0);
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_container, selectedFragment, listFragmentTag)
                            .commit();
                }
            } else {
                if (selectedFragment == null) {
                    selectedFragment = listFragment;
                }
                // Tell it who it is working with.
                selectedFragment.setTargetFragment(MainFragment.this, 0);
                fragmentManager.beginTransaction()
                        .replace(R.id.list_container, selectedFragment, listFragmentTag)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.list_fragment_tag), listFragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
