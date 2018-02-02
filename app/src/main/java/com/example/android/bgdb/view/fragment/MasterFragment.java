package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.BottomNavigationItemReselectedListener;
import com.example.android.bgdb.view.BottomNavigationItemReselectedListener.OnNavigationItemReselectedCallback;
import com.example.android.bgdb.view.BottomNavigationItemSelectedListener;
import com.example.android.bgdb.view.BottomNavigationItemSelectedListener.OnNavigationItemSelectedCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterFragmentListener} interface
 * to handle interaction events.
 * Use the {@link MasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterFragment extends Fragment implements
        OnNavigationItemSelectedCallback,
        OnNavigationItemReselectedCallback {

    private MasterFragmentListener listener;
    private String listFragmentTag;

    @BindView(R.id.list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public MasterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MasterFragment.
     */
    public static MasterFragment newInstance() {
        return new MasterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        ButterKnife.bind(this, view);
        listFragmentTag = getListFragmentTag(savedInstanceState);
        setUpBottomNavigationBar();
        listener.setUpMasterActionBar(toolbar);
        listener.setUpListFragment(listFragmentTag);
        return view;
    }

    private String getListFragmentTag(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getString
                    (getString(R.string.list_fragment_tag), getString(R.string.the_hotness));
        }
        return getString(R.string.the_hotness);
    }

    private void setUpBottomNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationItemSelectedListener(getContext(), this));
        bottomNavigationView.setOnNavigationItemReselectedListener
                (new BottomNavigationItemReselectedListener(getContext(), this));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.list_fragment_tag), listFragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MasterFragmentListener) {
            listener = (MasterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MasterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void updateListFragment(BaseListViewImpl listFragment, String listFragmentTag) {
        this.listFragmentTag = listFragmentTag;
        listener.updateListFragment(listFragment, listFragmentTag);
    }

    @Override
    public void updateCurrentFragment(BaseListViewImpl listFragment, String listFragmentTag, int boardGameFragmentTagId) {
        this.listFragmentTag = listFragmentTag;
        listener.updateCurrentFragment(listFragment, listFragmentTag, boardGameFragmentTagId);
    }
}
