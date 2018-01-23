package com.example.android.bgdb.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.fragment.MainFragment;
import com.example.android.bgdb.view.fragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mainFragment = fragmentManager.findFragmentByTag(getString(R.string.main_fragment));

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.main_container, mainFragment, getString(R.string.main_fragment))
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
