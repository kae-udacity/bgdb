package com.example.android.bgdb.view.loaderlistener;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.presenter.DetailPresenter;
import com.example.android.bgdb.view.ContextWrapper;
import com.example.android.bgdb.view.fragment.DetailFragment.DetailFragmentListener;

import java.io.ByteArrayOutputStream;

public class DeleteLoaderListener implements LoaderListener {

    private Context context;
    private DetailFragmentListener listener;
    private DetailPresenter presenter;
    private LoaderListener insertListener;

    public DeleteLoaderListener(Context context, DetailFragmentListener listener,
                         DetailPresenter presenter, LoaderListener insertListener) {
        this.context = context;
        this.listener = listener;
        this.presenter = presenter;
        this.insertListener = insertListener;
    }

    @Override
    public void onPreLoad() {

    }

    @Override
    public void onPostLoad(boolean successful) {
        if (successful) {
            listener.updateFavourite();
            listener.updateFavouriteIcon(R.drawable.ic_favorite_border_black, R.color.white);
            listener.updateWidget();
        } else {
            final BoardGame boardGame = listener.getBoardGame();
            if (boardGame.getThumbnailBlob() == null) {
                Glide.with(context)
                        .asBitmap()
                        .load(boardGame.getThumbnailUrl())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap thumbnail, Transition<? super Bitmap> transition) {
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                                byte[] thumbnailBlob = outputStream.toByteArray();
                                boardGame.setThumbnailBlob(thumbnailBlob);
                                presenter.insert(new ContextWrapper(context), insertListener, boardGame);
                            }
                        });
            } else {
                presenter.insert(new ContextWrapper(context), insertListener, boardGame);
            }
        }
    }
}
