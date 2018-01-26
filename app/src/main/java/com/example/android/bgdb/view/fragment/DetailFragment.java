package com.example.android.bgdb.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
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
public class DetailFragment extends Fragment {

    private OnFragmentInteractionListener listener;

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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float positiveOffSet = verticalOffset * -1;
                positiveOffSet /= appBarLayout.getTotalScrollRange();
                detailRatingContainer.setAlpha(1.0f - (positiveOffSet * 2));
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Activity activity = getActivity();
                if (activity != null) {
                    activity.onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void updateViews(BoardGame boardGame) {
        Context context = getContext();

        if (context != null) {
            Glide.with(getContext()).load(boardGame.getBannerUrl()).into(detailBannerImage);
        }
        toolbar.setTitle(boardGame.getName());
        detailYear.setText(boardGame.getYear());
        detailRanks.setText(boardGame.getRanks());
        detailDescription.setText(boardGame.getDescription());
        detailRating.setText(boardGame.getRating());
    }
}
