package com.example.android.bgdb.presenter;

import com.example.android.bgdb.model.SearchType;

public interface PopularListPresenter extends BasePresenter {

    void load(SearchType searchType);
}
