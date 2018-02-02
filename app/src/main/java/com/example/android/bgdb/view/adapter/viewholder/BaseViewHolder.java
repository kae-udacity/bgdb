package com.example.android.bgdb.view.adapter.viewholder;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.adapter.ListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ListAdapter adapter;

    @BindView(R.id.list_item)
    ConstraintLayout layoutListItem;

    @BindView(R.id.text_view_name)
    TextView textViewName;

    @BindView(R.id.text_view_year)
    TextView textViewYear;

    @Nullable
    @BindView(R.id.image_view_thumbnail)
    ImageView imageViewThumbnail;

    public BaseViewHolder(ListAdapter adapter, View itemView) {
        super(itemView);
        this.adapter = adapter;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public ConstraintLayout getLayoutListItem() {
        return layoutListItem;
    }

    public TextView getTextViewName() {
        return textViewName;
    }

    public TextView getTextViewYear() {
        return textViewYear;
    }

    @Nullable
    public ImageView getImageViewThumbnail() {
        return imageViewThumbnail;
    }

    @Override
    public void onClick(View view) {
        int index = getAdapterPosition();
        adapter.setSelectedIndex(index);
        adapter.getClickHandler().onClick(adapter.getBoardGames().get(index));
        adapter.notifyDataSetChanged();
    }
}
