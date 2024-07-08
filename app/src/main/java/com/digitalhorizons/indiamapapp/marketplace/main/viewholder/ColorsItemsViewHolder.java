package com.digitalhorizons.indiamapapp.marketplace.main.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class ColorsItemsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatImageView ivColor, ivDone;

    public ColorsItemsViewHolder(View itemView) {
        super(itemView);
        ivColor = itemView.findViewById(R.id.iv_color);
        ivDone = itemView.findViewById(R.id.iv_done);
    }

    public AppCompatImageView getIvColor() {
        return ivColor;
    }

    public void setIvColor(AppCompatImageView ivColor) {
        this.ivColor = ivColor;
    }

    public AppCompatImageView getIvDone() {
        return ivDone;
    }

    public void setIvDone(AppCompatImageView ivDone) {
        this.ivDone = ivDone;
    }
}

