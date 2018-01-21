package com.example.android.bgdb.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.ListAdapter;
import com.example.android.bgdb.view.adapter.ListAdapter.BoardGameOnClickHandler;

import java.util.List;

import butterknife.BindView;

/**
 * Provides the base view for a list fragment.
 */
public abstract class BaseViewImpl extends Fragment implements
        BaseView,
        BoardGameOnClickHandler {

    private ListAdapter adapter;

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    public void onCreateView(ListAdapter adapter) {
        this.adapter = adapter;

        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
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
        adapter.setBoardGames(boardGames);
        adapter.notifyDataSetChanged();
    }

    private void displayEmptyView() {

    }

    @Override
    public void onClick(BoardGame boardGame) {

    }
}
