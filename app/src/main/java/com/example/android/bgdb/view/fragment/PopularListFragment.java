package com.example.android.bgdb.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.presenter.PopularListPresenter;
import com.example.android.bgdb.presenter.PopularListPresenterImpl;
import com.example.android.bgdb.view.NetworkUtil;
import com.example.android.bgdb.view.activity.DetailActivity;
import com.example.android.bgdb.view.adapter.PopularListAdapter;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragmentListener} interface
 * to handle interaction events.
 * Use the {@link PopularListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularListFragment extends BaseListViewImpl {

    public PopularListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PopularListFragment.
     */
    public static PopularListFragment newInstance(SearchType searchType, int boardGameTagId) {
        PopularListFragment fragment = new PopularListFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEARCH_TYPE, searchType);
        args.putInt(BOARD_GAME_TAG_ID, boardGameTagId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        setAdapter(new PopularListAdapter(getContext(), this));
        onCreate();

        Bundle args = getArguments();
        int boardGameTagId = getBoardGameTagId(args);
        String boardGameFragmentTag = getString(boardGameTagId);
        BoardGameFragment boardGameFragment = getListener().getBoardGameFragment(boardGameFragmentTag);
        if (boardGameFragment == null) {
            return view;
        }

        if (boardGameFragment.getBoardGames() == null
                || boardGameFragment.getBoardGames().isEmpty()) {
            SearchType searchType = getSearchType(args);
            // Initialise presenter to communicate with the model and load the list.
            PopularListPresenter popularListPresenter = new PopularListPresenterImpl(this, boardGameFragment);
            popularListPresenter.load(searchType);
        } else {
            onPostLoad(boardGameFragment.getBoardGames());
        }
        return view;
    }

    private int getBoardGameTagId(Bundle args) {
        int boardGameTagId = -1;
        if (args != null) {
            if (args.containsKey(BOARD_GAME_TAG_ID)) {
                boardGameTagId = args.getInt(BOARD_GAME_TAG_ID);
            }
        }
        return boardGameTagId;
    }

    private SearchType getSearchType(Bundle args) {
        if (args != null && args.containsKey(SEARCH_TYPE)) {
            return (SearchType) args.get(SEARCH_TYPE);
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener) {
            setListener((ListFragmentListener) context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setListener(null);
    }

    @Override
    public void displayEmptyView() {
        progressBar.setVisibility(View.GONE);
        if (NetworkUtil.isOnline(getContext())) {
            getListener().displayEmptyView(getString(R.string.please_try_again));
        } else {
            getListener().displayEmptyView(getString(R.string.no_network_connection));
        }
    }

    @Override
    public void onClick(BoardGame boardGame) {
        Activity activity = getActivity();
        if (activity != null) {
            if (getActivity().getResources().getBoolean(R.bool.tablet)) {
                getListener().showDetailFragment(boardGame);
            } else {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(getString(R.string.board_game), boardGame);
                startActivity(intent);
            }
        }
    }
}
