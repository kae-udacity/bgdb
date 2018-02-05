package com.example.android.bgdb.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.viewholder.BaseViewHolder;
import com.example.android.bgdb.view.adapter.viewholder.PopularViewHolder;

public class PopularListAdapter extends ListAdapter {

    public PopularListAdapter(Context context, BoardGameOnClickHandler clickHandler) {
        super(context, clickHandler);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item_ranked, parent, false);
        return new PopularViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (boardGames == null) {
            return;
        }
        PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
        if(context.getResources().getBoolean(R.bool.tablet) && selectedIndex == position){
            popularViewHolder.getTextViewRank().setTextColor(ContextCompat.getColor(context, R.color.accent));
        } else {
            popularViewHolder.getTextViewRank().setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        BoardGame boardGame = boardGames.get(position);
        bindView(holder, position, boardGame);
        popularViewHolder.getTextViewRank().setText(boardGame.getRank());
        if (popularViewHolder.getImageViewThumbnail() != null) {
            Glide.with(context).load(boardGame.getThumbnailUrl()).into(popularViewHolder.getImageViewThumbnail());
        }
    }
}
