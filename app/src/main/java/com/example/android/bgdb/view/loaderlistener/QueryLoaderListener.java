package com.example.android.bgdb.view.loaderlistener;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.fragment.DetailFragment.DetailFragmentListener;

public class QueryLoaderListener implements LoaderListener {

    private DetailFragmentListener listener;

    public QueryLoaderListener(DetailFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPreLoad() {
        listener.onPreLoad();
    }

    @Override
    public void onPostLoad(boolean favourite) {
        if (favourite) {
            listener.updateFavouriteIcon(R.drawable.ic_favorite_black, R.color.accent);
        } else {
            listener.updateFavouriteIcon(R.drawable.ic_favorite_border_black, R.color.white);
        }
        listener.onPostLoad();
    }
}
