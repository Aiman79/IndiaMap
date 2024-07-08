package com.digitalhorizons.indiamapapp.search.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvTitle;
    private AppCompatTextView tvDes;
    private AppCompatImageView ivThumbnail;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDes = itemView.findViewById(R.id.tv_description);
        ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
    }

    public AppCompatTextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(AppCompatTextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public AppCompatTextView getTvDes() {
        return tvDes;
    }

    public void setTvDes(AppCompatTextView tvDes) {
        this.tvDes = tvDes;
    }

    public AppCompatImageView getIvThumbnail() {
        return ivThumbnail;
    }

    public void setIvThumbnail(AppCompatImageView ivThumbnail) {
        this.ivThumbnail = ivThumbnail;
    }
}
