package com.digitalhorizons.indiamapapp.planner.viewmodel;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class TripDetailsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvCat;
    private AppCompatImageView ivAlbum;

    public TripDetailsViewHolder(View itemView){
        super(itemView);
        tvCat = itemView.findViewById(R.id.tv_cat);
        ivAlbum = itemView.findViewById(R.id.iv_album);
    }

    public AppCompatTextView getTvCat() {
        return tvCat;
    }

    public void setTvCat(AppCompatTextView tvCat) {
        this.tvCat = tvCat;
    }

    public AppCompatImageView getIvAlbum() {
        return ivAlbum;
    }

    public void setIvAlbum(AppCompatImageView ivAlbum) {
        this.ivAlbum = ivAlbum;
    }
}

