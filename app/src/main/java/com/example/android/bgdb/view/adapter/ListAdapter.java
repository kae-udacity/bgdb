package com.example.android.bgdb.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
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

    Context context;
    List<BoardGame> boardGames;
    private BoardGameOnClickHandler clickHandler;

    public ListAdapter(Context context, BoardGameOnClickHandler clickHandler) {
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
        holder.getTextViewName().setText(boardGame.getName());
        holder.getTextViewYear().setText(boardGame.getYear());

        Bitmap thumbnail = BitmapFactory.decodeByteArray(
                boardGame.getThumbnailBlob(),
                0,
                boardGame.getThumbnailBlob().length);
        holder.getImageViewThumbnail().setImageBitmap(thumbnail);
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

    @Override
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

    /**
     * Must be implemented to handle the click events on a board game item in
     * the {@link RecyclerView}.
     */
    public interface BoardGameOnClickHandler {
        void onClick(BoardGame boardGame);
    }
}
