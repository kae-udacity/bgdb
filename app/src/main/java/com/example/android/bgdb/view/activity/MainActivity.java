package com.example.android.bgdb.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.fragment.NavBarFragment;
import com.example.android.bgdb.view.fragment.ListFragment;

public class MainActivity extends AppCompatActivity implements
        NavBarFragment.OnFragmentInteractionListener,
        ListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction() {

    }
}
