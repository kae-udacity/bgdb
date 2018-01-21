package com.example.android.bgdb.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.adapter.ListAdapter;

import butterknife.BindView;

public class PopularViewHolder extends BaseViewHolder {

    @BindView(R.id.text_view_rank)
    TextView textViewRank;

    public PopularViewHolder(ListAdapter adapter, View itemView) {
        super(adapter, itemView);
    }

    public TextView getTextViewRank() {
        return textViewRank;
    }
}
