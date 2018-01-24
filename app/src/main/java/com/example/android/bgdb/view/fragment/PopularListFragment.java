package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.SearchType;
import com.example.android.bgdb.presenter.PopularListPresenter;
import com.example.android.bgdb.presenter.PopularListPresenterImpl;
import com.example.android.bgdb.view.adapter.PopularListAdapter;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopularListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularListFragment extends BaseListViewImpl {

    private static final String SEARCH_TYPE = "searchType";
    private static final String BOARD_GAME_TAG_ID = "boardGameTagId";

    private OnFragmentInteractionListener listener;
    private BoardGameFragment boardGameFragment;

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

        PopularListAdapter adapter = new PopularListAdapter(getContext(), this);
        onCreate(adapter);

        Bundle args = getArguments();
        int boardGameTagId = getBoardGameTagId(args);
        String boardGameFragmentTag = getString(boardGameTagId);
        setUpBoardGameFragment(boardGameFragmentTag);
        if (boardGameFragment.getBoardGames() == null
                || boardGameFragment.getBoardGames().isEmpty()) {
            SearchType searchType = getSearchType(args);
            PopularListPresenter popularListPresenter = new PopularListPresenterImpl(this, boardGameFragment);
            popularListPresenter.load(searchType);
        } else {
            onPostLoad(boardGameFragment.getBoardGames());
        }
        return view;
    }

    private void setUpBoardGameFragment(String boardGameFragmentTag) {
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            // Check to see if we have retained the worker fragment.
            boardGameFragment = (BoardGameFragment)
                    fragmentManager.findFragmentByTag(boardGameFragmentTag);
            // If not retained (or first time running), we need to create it.
            if (boardGameFragment == null) {
                boardGameFragment = BoardGameFragment.newInstance();
                // Tell it who it is working with.
                fragmentManager.beginTransaction()
                        .add(boardGameFragment, boardGameFragmentTag)
                        .commit();
            }
            boardGameFragment.setTargetFragment(this, 1);
        }
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
        boardGameFragment.setTargetFragment(null, 1);
    }
}
