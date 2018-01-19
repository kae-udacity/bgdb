package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.BoardGameListPresenter;
import com.example.android.bgdb.presenter.BoardGameListPresenterImpl;
import com.example.android.bgdb.presenter.SearchType;
import com.example.android.bgdb.view.adapter.BoardGameAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopularListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopularListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularListFragment extends Fragment implements
        BoardGameListView,
        BoardGameAdapter.BoardGameOnClickHandler {

    private OnFragmentInteractionListener listener;
    private BoardGameAdapter boardGameAdapter;

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public PopularListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PopularListFragment.
     */
    public static PopularListFragment newInstance(Context context, SearchType searchType) {
        PopularListFragment fragment = new PopularListFragment();
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.search_type), searchType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        SearchType searchType = null;
        if (args != null && args.containsKey(getString(R.string.search_type))) {
            searchType = (SearchType) args.get(getString(R.string.search_type));
        }
        BoardGameListPresenter boardGameListPresenter = new BoardGameListPresenterImpl(this);
        boardGameListPresenter.load(searchType);

        int layoutId = searchType == SearchType.FAVOURITE ?
                R.layout.layout_list_item : R.layout.layout_list_item_ranked;
        boardGameAdapter = new BoardGameAdapter(getContext(), this, layoutId);
        recyclerView.setAdapter(boardGameAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
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
    public void onPreLoad() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        progressBar.setVisibility(View.GONE);
        if (boardGames == null || boardGames.isEmpty()) {
            displayEmptyView();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            updateAdapter(boardGames);
        }
    }

    private void updateAdapter(List<BoardGame> boardGames) {
        boardGameAdapter.setBoardGames(boardGames);
        boardGameAdapter.notifyDataSetChanged();
    }

    private void displayEmptyView() {

    }

    @Override
    public void onClick(BoardGame boardGame) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
