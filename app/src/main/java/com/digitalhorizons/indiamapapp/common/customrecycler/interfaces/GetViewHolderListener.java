package com.digitalhorizons.indiamapapp.common.customrecycler.interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface GetViewHolderListener {
    public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent);
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType);
}
