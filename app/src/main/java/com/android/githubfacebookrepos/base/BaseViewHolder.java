package com.android.githubfacebookrepos.base;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private View itemView;
    private int viewType;
    private ItemClickListener itemClickListener;

    public BaseViewHolder(View itemView, int viewType) {
        super(itemView);
        this.itemView = itemView;
        this.viewType = viewType;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public View getItemView() {
        return itemView;
    }

    public int getViewType() {
        return viewType;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
