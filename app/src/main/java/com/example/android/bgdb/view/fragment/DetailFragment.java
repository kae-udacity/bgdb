package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
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
import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.presenter.DetailPresenterImpl;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.loaderlistener.DeleteLoaderListener;
import com.example.android.bgdb.view.loaderlistener.InsertLoaderListener;
import com.example.android.bgdb.view.loaderlistener.LoaderListener;
import com.example.android.bgdb.view.loaderlistener.QueryLoaderListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements LoaderManagerView {

    private DetailFragmentListener listener;
    private DetailPresenter presenter;
    private LoaderListener queryListener;
    private LoaderListener deleteListener;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.detail_toolbar)
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
        listener.setUpActionBar(toolbar);

        // Initialise presenter to communicate with model.
        presenter = new DetailPresenterImpl(this, listener);

        // Initialise database loader listeners.
        LoaderListener insertListener = new InsertLoaderListener(listener);
        queryListener = new QueryLoaderListener(listener);
        deleteListener =
                new DeleteLoaderListener(getContext(), listener, presenter, insertListener);

        BoardGame boardGame = listener.getBoardGame();
        if (boardGame != null) {
            if (boardGame.getDescription() == null) {
                presenter.load(boardGame);
            } else {
                updateView(boardGame);
            }
        }

        // Calculate the alpha of the rating container and set it.
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float positiveOffSet = verticalOffset * -1;
                positiveOffSet /= appBarLayout.getTotalScrollRange();
                detailRatingContainer.setAlpha(1.0f - (positiveOffSet * 2));
            }
        });
        setHasOptionsMenu(true);
        return view;
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
                    BoardGame boardGame = listener.getBoardGame();
                    // First, try deleting board game.
                    presenter.delete(new ContextWrapper(getContext()), deleteListener, boardGame.getId());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentListener) {
            listener = (DetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DetailFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
        presenter.query(new ContextWrapper(getContext()), queryListener, boardGame.getId());

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

    public interface DetailFragmentListener extends DetailView {
        void setUpActionBar(Toolbar toolbar);
        void setActionBarTitle(String name);
        BoardGame getBoardGame();
        void setBoardGame(BoardGame boardGame);
        void onPostLoad();
        void updateFavouriteIcon(int drawableId, int colorId);
        void updateFavourite();
        void updateWidget();
    }
}
