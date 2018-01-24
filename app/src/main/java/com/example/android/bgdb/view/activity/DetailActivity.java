package com.example.android.bgdb.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.presenter.DetailPresenterImpl;
import com.example.android.bgdb.view.fragment.DetailFragment;
import com.example.android.bgdb.view.fragment.DetailView;
import com.example.android.bgdb.view.fragment.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements
        DetailView,
        OnFragmentInteractionListener {

    @BindView(R.id.detail_container)
    FrameLayout detailContainer;

    @BindView(R.id.detail_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment detailFragment = fragmentManager.findFragmentByTag(getString(R.string.detail_fragment));

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, detailFragment, getString(R.string.detail_fragment))
                .commit();

        Intent intent = getIntent();
        BoardGame boardGame = intent.getParcelableExtra(getString(R.string.board_game));

        DetailPresenter presenter = new DetailPresenterImpl(this);
        presenter.load(boardGame);
    }

    @Override
    public void onPreLoad() {
        detailContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        progressBar.setVisibility(View.GONE);
        if (boardGame == null) {
            displayEmptyView();
        } else {
            detailContainer.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            DetailFragment detailFragment = (DetailFragment)
                    fragmentManager.findFragmentByTag(getString(R.string.detail_fragment));

            if (detailFragment != null) {
                detailFragment.updateViews(boardGame);
            }
        }
    }

    private void displayEmptyView() {

    }

    @Override
    public void onFragmentInteraction() {

    }
}
