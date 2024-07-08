package com.digitalhorizons.indiamapapp.explore.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class ItemsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvText;
    private AppCompatImageView ivThumbnail;

    public ItemsViewHolder(View itemView){
        super(itemView);
        tvText = itemView.findViewById(R.id.tv_text);
        ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
    }

    public AppCompatTextView getTvText() {
        return tvText;
    }

    public void setTvText(AppCompatTextView tvText) {
        this.tvText = tvText;
    }

    public AppCompatImageView getIvThumbnail() {
        return ivThumbnail;
    }

    public void setIvThumbnail(AppCompatImageView ivThumbnail) {
        this.ivThumbnail = ivThumbnail;
    }
}

