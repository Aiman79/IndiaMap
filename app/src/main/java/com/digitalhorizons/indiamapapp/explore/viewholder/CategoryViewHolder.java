package com.digitalhorizons.indiamapapp.explore.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvCat;
//    private AppCompatImageView ivAlbum;

    public CategoryViewHolder(View itemView){
        super(itemView);
        tvCat = itemView.findViewById(R.id.tv_cat);
//        tvCat = itemView.findViewById(R.id.iv_album);
    }

    public AppCompatTextView getTvCat() {
        return tvCat;
    }

    public void setTvCat(AppCompatTextView tvCat) {
        this.tvCat = tvCat;
    }
}

