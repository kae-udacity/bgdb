package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.FavouriteListPresenter;
import com.example.android.bgdb.presenter.FavouriteListPresenterImpl;
import com.example.android.bgdb.view.adapter.BoardGameOnClickHandler;
import com.example.android.bgdb.view.adapter.FavouriteListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteListFragment extends Fragment implements
        FavouriteListView,
        BoardGameOnClickHandler {

    private OnFragmentInteractionListener listener;
    private FavouriteListAdapter adapter;

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    public FavouriteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavouriteListFragment.
     */
    public static FavouriteListFragment newInstance() {
        return new FavouriteListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            FavouriteListPresenter presenter = new FavouriteListPresenterImpl(this);
            presenter.createLoader(activity.getSupportLoaderManager());
        }

        adapter = new FavouriteListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
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
    public void onLoadFinished(List<BoardGame> boardGames) {
        if (boardGames == null || boardGames.isEmpty()) {
            displayEmptyView();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            updateAdapter(boardGames);
        }
    }

    private void updateAdapter(List<BoardGame> boardGames) {
        adapter.setBoardGames(boardGames);
        adapter.notifyDataSetChanged();
    }

    private void displayEmptyView() {

    }

    @Override
    public void onClick(BoardGame boardGame) {

    }
}
