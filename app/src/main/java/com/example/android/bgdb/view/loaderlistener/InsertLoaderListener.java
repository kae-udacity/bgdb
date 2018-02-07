package com.example.android.bgdb.view.loaderlistener;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.fragment.DetailFragment.DetailFragmentListener;

public class InsertLoaderListener implements LoaderListener {

    private DetailFragmentListener listener;

    public InsertLoaderListener(DetailFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPreLoad() {

    }

    @Override
    public void onPostLoad(boolean successful) {
        if (successful) {
            listener.updateFavourite();
            listener.updateFavouriteIcon(R.drawable.ic_favorite_black, R.color.accent);
            listener.updateWidget();
        }
    }
}
