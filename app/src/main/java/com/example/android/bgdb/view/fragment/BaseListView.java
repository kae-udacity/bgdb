package com.example.android.bgdb.view.fragment;

import com.example.android.bgdb.view.adapter.ListAdapter;

/**
 * Must be implemented to communicate with presenter.
 */
public interface BaseListView extends BaseView {

    void onCreate(ListAdapter adapter);
}
