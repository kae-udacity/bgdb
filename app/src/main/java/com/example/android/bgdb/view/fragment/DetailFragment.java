package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.presenter.DetailPresenterImpl;
import com.example.android.bgdb.view.ContextWrapper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements
        DetailView,
        LoaderManagerView {

    private OnDetailFragmentInteractionListener listener;
    private BoardGameFragment boardGameFragment;
    private DetailPresenter presenter;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detail_banner_image)
    ImageView detailBannerImage;

    @BindView(R.id.detail_scroll_view)
    NestedScrollView detailScrollView;

    @BindView(R.id.detail_year)
    TextView detailYear;

    @BindView(R.id.detail_ranks)
    TextView detailRanks;

    @BindView(R.id.detail_description)
    TextView detailDescription;

    @BindView(R.id.detail_rating_container)
    CardView detailRatingContainer;

    @BindView(R.id.detail_rating)
    TextView detailRating;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            setUpBoardGameFragment(activity);

            Intent intent = activity.getIntent();
            BoardGame boardGame = intent.getParcelableExtra(getString(R.string.board_game));

            presenter = new DetailPresenterImpl(this, this);
            if (boardGameFragment.getBoardGames() == null
                    || boardGameFragment.getBoardGames().isEmpty()) {
                presenter.load(boardGame);
            } else {
                boardGame = boardGameFragment.getBoardGames().get(0);
                updateView(boardGame);
            }
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float positiveOffSet = verticalOffset * -1;
                positiveOffSet /= appBarLayout.getTotalScrollRange();
                detailRatingContainer.setAlpha(1.0f - (positiveOffSet * 2));
            }
        });
    }

    private void setUpBoardGameFragment(AppCompatActivity activity) {
        if (activity != null) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            String boardGameFragmentTag = getString(R.string.detail_board_game_fragment_tag);
            boardGameFragment = (BoardGameFragment) fragmentManager
                    .findFragmentByTag(boardGameFragmentTag);

            if (boardGameFragment == null) {
                boardGameFragment = new BoardGameFragment();

                fragmentManager.beginTransaction()
                        .add(boardGameFragment, boardGameFragmentTag)
                        .commit();
                boardGameFragment.setTargetFragment(this, 1);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (activity != null) {
                    activity.onBackPressed();
                }
                return true;
            case R.id.action_favourite:
                if (activity != null) {
                    BoardGame boardGame = boardGameFragment.getBoardGames().get(0);
                    presenter.delete(new ContextWrapper(getContext()), boardGame.getId());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            listener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        boardGameFragment.setTargetFragment(null, 1);
    }

    @Override
    public void onPreLoad() {
        listener.onPreLoad();
    }

    @Override
    public void onPostLoad(BoardGame boardGame) {
        listener.onPostLoad(boardGame);
    }

    @Override
    public void onPostQuery(boolean favourite) {
        if (favourite) {
            listener.updateFavouriteIcon(R.drawable.ic_favorite_black, R.color.accent);
        }
        listener.onPostQuery();
    }

    @Override
    public void onPostDelete(int rowsDeleted) {
        if (rowsDeleted > 0) {
            listener.onUpdateFavourite();
            listener.updateFavouriteIcon(R.drawable.ic_favorite_border_black, R.color.white);
        } else {
            final BoardGame boardGame = boardGameFragment.getBoardGames().get(0);

            if (boardGame.getThumbnailBlob() == null) {
                Glide.with(this)
                        .asBitmap()
                        .load(boardGame.getThumbnailUrl())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap thumbnail, Transition<? super Bitmap> transition) {
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                                byte[] thumbnailBlob = outputStream.toByteArray();
                                boardGame.setThumbnailBlob(thumbnailBlob);
                                presenter.insert(new ContextWrapper(getContext()), boardGame);
                            }
                        });
            } else {
                presenter.insert(new ContextWrapper(getContext()), boardGame);
            }
        }
    }

    @Override
    public void onPostInsert(boolean insertSuccessful) {
        if (insertSuccessful) {
            listener.onUpdateFavourite();
            listener.updateFavouriteIcon(R.drawable.ic_favorite_black, R.color.accent);
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

    public void updateView(BoardGame boardGame) {
        presenter.query(new ContextWrapper(getContext()), boardGame.getId());
        List<BoardGame> boardGames = new ArrayList<>();
        boardGames.add(boardGame);
        boardGameFragment.setBoardGames(boardGames);

        Context context = getContext();
        if (context != null) {
            Glide.with(getContext()).load(boardGame.getBannerUrl()).into(detailBannerImage);
        }
        listener.setActionBarTitle(boardGame.getName());
        detailYear.setText(boardGame.getYear());
        detailRanks.setText(boardGame.getRanks());
        detailDescription.setText(boardGame.getDescription());
        detailRating.setText(boardGame.getRating());
    }

    public interface OnDetailFragmentInteractionListener {
        void setActionBarTitle(String name);
        void onPreLoad();
        void onPostLoad(BoardGame boardGame);
        void updateFavouriteIcon(int drawableId, int colorId);
        void onUpdateFavourite();
        void onPostQuery();
    }
}
