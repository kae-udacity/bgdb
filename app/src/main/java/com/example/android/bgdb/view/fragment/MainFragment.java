package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.view.BottomNavigationItemReselectedListener;
import com.example.android.bgdb.view.BottomNavigationItemReselectedListener.OnNavigationItemReselectedCallback;
import com.example.android.bgdb.view.BottomNavigationItemSelectedListener;
import com.example.android.bgdb.view.BottomNavigationItemSelectedListener.OnNavigationItemSelectedCallback;

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
public class MainFragment extends Fragment implements
        OnNavigationItemSelectedCallback,
        OnNavigationItemReselectedCallback {

    private OnFragmentInteractionListener listener;
    private String listFragmentTag;

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
        listFragmentTag = getListFragmentTag(savedInstanceState);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            setUpActionBar(activity);
            setUpBottomNavigationBar();
            setUpListFragment(activity);
        }
        return view;
    }

    private String getListFragmentTag(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getString
                    (getString(R.string.list_fragment_tag), getString(R.string.the_hotness));
        }
        return getString(R.string.the_hotness);
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
                (new BottomNavigationItemSelectedListener(getContext(), this));
        bottomNavigationView.setOnNavigationItemReselectedListener
                (new BottomNavigationItemReselectedListener(getContext(), this));
    }

    private void setUpListFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment selectedFragment = fragmentManager.findFragmentByTag(listFragmentTag);
        if (selectedFragment == null) {
            //Manually displaying the first fragment - one time only
            selectedFragment = PopularListFragment.newInstance
                    (SearchType.HOT, R.string.the_hotness_board_game_tag);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.list_container, selectedFragment, listFragmentTag)
                .commit();
        selectedFragment.setTargetFragment(this, 0);
    }

    @Nullable
    private FragmentManager getSupportFragmentManager() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        FragmentManager fragmentManager = null;
        if (activity != null) {
            fragmentManager = activity.getSupportFragmentManager();
        }
        return fragmentManager;
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

    @Override
    public void updateListFragment(String listFragmentTag, BaseListViewImpl listFragment) {
        this.listFragmentTag = listFragmentTag;
        updateListFragment(listFragment);
    }

    private void updateListFragment(BaseListViewImpl listFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            BaseListViewImpl selectedFragment =
                    (BaseListViewImpl) fragmentManager.findFragmentByTag(listFragmentTag);
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

    @Override
    public void updateCurrentFragment(String listFragmentTag, BaseListViewImpl listFragment, int boardGameFragmentTagId) {
        this.listFragmentTag = listFragmentTag;
        updateCurrentFragment(listFragment, boardGameFragmentTagId);
    }

    private void updateCurrentFragment(BaseListViewImpl listFragment, int boardGameFragmentTagId) {
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
                    boardGameFragment.setTargetFragment(listFragment, 1);
                }
                listFragment.setTargetFragment(MainFragment.this, 0);
                fragmentManager.beginTransaction()
                        .replace(R.id.list_container, listFragment, listFragmentTag)
                        .commit();
            }
        }
    }
}
