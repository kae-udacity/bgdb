package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.presenter.FavouriteListPresenter;
import com.example.android.bgdb.presenter.FavouriteListPresenterImpl;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.adapter.ListAdapter;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteListFragment extends BaseListViewImpl implements FavouriteListView {

    private OnFragmentInteractionListener listener;

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

        FavouriteListPresenter presenter = new FavouriteListPresenterImpl(FavouriteListFragment.this);
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        presenter.createLoader(contextWrapper);

        ListAdapter adapter = new ListAdapter(getContext(), this);
        onCreateView(adapter);
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
    public LoaderManager getSupportLoaderManager() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity == null) {
            return null;
        }
        return activity.getSupportLoaderManager();
    }
}
