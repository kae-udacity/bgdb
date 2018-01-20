package com.example.android.bgdb.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.model.BoardGame;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creates and binds the {@link ViewHolder} objects using a {@link List} of {@link BoardGame}
 * objects.
 */

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.ViewHolder> {

    private Context context;
    private List<BoardGame> boardGames;
    private BoardGameOnClickHandler clickHandler;

    public FavouriteListAdapter(Context context, BoardGameOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (boardGames == null) {
            return;
        }
        BoardGame boardGame = boardGames.get(position);
        holder.textViewName.setText(boardGame.getName());
        holder.textViewYear.setText(boardGame.getYear());
        holder.imageViewThumbnail.setImageBitmap(boardGame.getThumbnail());
    }

    @Override
    public int getItemCount() {
        if (boardGames == null) {
            return 0;
        }
        return boardGames.size();
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_view_name)
        TextView textViewName;

        @BindView(R.id.text_view_year)
        TextView textViewYear;

        @BindView(R.id.image_view_thumbnail)
        ImageView imageViewThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            clickHandler.onClick(boardGames.get(index));
        }
    }
}
