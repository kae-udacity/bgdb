package com.example.android.bgdb.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.viewholder.BaseViewHolder;

import java.util.List;

/**
 * Creates and binds the {@link BaseViewHolder} objects using a {@link List} of {@link BoardGame}
 * objects.
 */
public class ListAdapter extends BaseListAdapter {

    public ListAdapter(Context context, ListAdapter.BoardGameOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
        return new BaseViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (boardGames == null) {
            return;
        }
        BoardGame boardGame = boardGames.get(position);
        bindView(holder, position, boardGame);

        Bitmap thumbnail = BitmapFactory.decodeByteArray(
                boardGame.getThumbnailBlob(),
                0,
                boardGame.getThumbnailBlob().length);
        if (holder.getImageViewThumbnail() != null) {
            holder.getImageViewThumbnail().setImageBitmap(thumbnail);
        }
    }
}
