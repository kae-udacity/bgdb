package com.example.android.bgdb.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.fragment.MasterFragment;
import com.example.android.bgdb.view.fragment.FragmentListener;
import com.example.android.bgdb.view.fragment.ListFragmentListener;

public class MasterActivity extends BaseActivity implements
        FragmentListener,
        ListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment masterFragment = fragmentManager.findFragmentByTag(getString(R.string.master_fragment));

        if (masterFragment == null) {
            masterFragment = MasterFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.master_container, masterFragment, getString(R.string.master_fragment))
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void displayEmptyView(String message) {
        RecyclerView listRecyclerView = findViewById(R.id.list_recycler_view);
        listRecyclerView.setVisibility(View.GONE);

        TextView listEmptyView = findViewById(R.id.list_empty_view);
        listEmptyView.setVisibility(View.VISIBLE);
        listEmptyView.setText(message);
    }

    @Override
    public void showDetailFragment(BoardGame boardGame) {

    }
}
