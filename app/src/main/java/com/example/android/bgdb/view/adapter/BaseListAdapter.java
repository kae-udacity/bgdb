package com.example.android.bgdb.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;
import com.example.android.bgdb.view.adapter.viewholder.BaseViewHolder;

import java.util.List;

public abstract class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    Context context;
    List<BoardGame> boardGames;
    ListAdapter.BoardGameOnClickHandler clickHandler;
    int selectedIndex = -1;

    void bindView(BaseViewHolder holder, int position, BoardGame boardGame) {
        if(context.getResources().getBoolean(R.bool.tablet) && selectedIndex == position){
            holder.getLayoutListItem().setBackgroundColor(ContextCompat.getColor(context, R.color.gray_semi_transparent_44));
            holder.getTextViewName().setTextColor(ContextCompat.getColor(context, R.color.accent));
            holder.getTextViewYear().setTextColor(ContextCompat.getColor(context, R.color.accent));
        } else {
            holder.getLayoutListItem().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.getTextViewName().setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.getTextViewYear().setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
        holder.getTextViewName().setText(boardGame.getName());
        holder.getTextViewYear().setText(boardGame.getYear());
    }

    @Override
    public int getItemCount() {
        if (boardGames == null) {
            return 0;
        }
        return boardGames.size();
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    public void setBoardGames(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
    }

    public void clear() {
        if (boardGames == null) {
            return;
        }
        boardGames.clear();
    }

    public BoardGameOnClickHandler getClickHandler() {
        return clickHandler;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    /**
     * Must be implemented to handle the click events on a board game item in
     * the {@link RecyclerView}.
     */
    public interface BoardGameOnClickHandler {
        void onClick(BoardGame boardGame);
    }
}
