package com.example.android.bgdb.view.fragment;


import android.support.v7.widget.Toolbar;

public interface MasterFragmentListener {

    void setToolbarTitle(Toolbar toolbar, String listFragmentTag);
    void setUpMasterActionBar(Toolbar toolbar);
    void setUpListFragment(String listFragmentTag);
    void updateListFragment(BaseListViewImpl listFragment, String listFragmentTag);
    void updateCurrentFragment(BaseListViewImpl listFragment, String listFragmentTag, int boardGameFragmentTagId);
}
