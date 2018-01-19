package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.presenter.SearchType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavBarFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public NavBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavBarFragment.
     */
    public static NavBarFragment newInstance() {
        return new NavBarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_bar, container, false);
        ButterKnife.bind(this, view);
        initialise();
        return view;
    }

    private void initialise() {
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            setUpActionBar(activity);

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            setUpBottomNavigationBar(fragmentManager);

            //Manually displaying the first fragment - one time only
            replaceFragment(ListFragment.newInstance(getContext(), SearchType.HOT), fragmentManager);
        }
    }

    private void setUpActionBar(AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setUpBottomNavigationBar(final FragmentManager fragmentManager) {
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_the_hotness:
                                selectedFragment = ListFragment.newInstance(getContext(), SearchType.HOT);
                                break;
                            case R.id.action_top_100:
                                selectedFragment = ListFragment.newInstance(getContext(), SearchType.TOP);
                                break;
                            case R.id.action_favorites:
                                selectedFragment = ListFragment.newInstance(getContext(), SearchType.TOP);
                                break;
                        }
                        replaceFragment(selectedFragment, fragmentManager);
                        return true;
                    }
                });
    }

    private void replaceFragment(Fragment selectedFragment, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_container, selectedFragment);
        transaction.commit();
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
