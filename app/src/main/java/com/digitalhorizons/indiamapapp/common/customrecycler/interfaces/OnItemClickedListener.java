package com.digitalhorizons.indiamapapp.common.customrecycler.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickedListener {
    public void OnItemClicked(RecyclerView.ViewHolder holder);
    public void OnItemClicked(int pos);
}
