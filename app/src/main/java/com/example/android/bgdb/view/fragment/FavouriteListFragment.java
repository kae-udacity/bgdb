package com.example.android.bgdb.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.FavouriteListPresenter;
import com.example.android.bgdb.presenter.FavouriteListPresenterImpl;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.activity.DetailActivity;
import com.example.android.bgdb.view.adapter.ListAdapter;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragmentListener} interface to handle interaction events.
 * Use the {@link FavouriteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteListFragment extends BaseListViewImpl implements LoaderManagerView {

    private FavouriteListPresenter presenter;

    public FavouriteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavouriteListFragment.
     */
    public static FavouriteListFragment newInstance(int boardGameTagId) {
        FavouriteListFragment fragment = new FavouriteListFragment();
        Bundle args = new Bundle();
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

        // Initialise presenter to communicate with model.
        presenter = new FavouriteListPresenterImpl(
                FavouriteListFragment.this,
                FavouriteListFragment.this);
        presenter.load(new ContextWrapper(getContext()));

        setAdapter(new ListAdapter(getContext(), this));
        onCreate();
        return view;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If user clicked on favourites icon in detail screen.
        if (requestCode == getResources().getInteger(R.integer.request_code)
                && resultCode == Activity.RESULT_OK) {
            // Reload master list to refresh view.
            presenter.load(new ContextWrapper(getContext()));
        }
    }

    @Override
    public LoaderManager getSupportLoaderManager() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity == null) {
            return null;
        }
        return activity.getSupportLoaderManager();
    }

    @Override
    public void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        getListener().showEmptyView(getString(R.string.add_some_favourites));
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
                startActivityForResult(intent, getResources().getInteger(R.integer.request_code));
            }
        }
    }
}
