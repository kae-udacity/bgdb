package com.example.android.bgdb.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.viewholder.BaseViewHolder;

import java.util.List;

abstract class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    abstract void setBoardGames(List<BoardGame> boardGames);
}
