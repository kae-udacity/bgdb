package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.presenter.PopularListPresenter;
import com.example.android.bgdb.presenter.PopularListPresenterImpl;
import com.example.android.bgdb.model.SearchType;
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
public class PopularListFragment extends BaseViewImpl {

    private OnFragmentInteractionListener listener;

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
        PopularListPresenter popularListPresenter = new PopularListPresenterImpl(this);
        popularListPresenter.load(searchType);

        PopularListAdapter adapter = new PopularListAdapter(getContext(), this);
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
}
