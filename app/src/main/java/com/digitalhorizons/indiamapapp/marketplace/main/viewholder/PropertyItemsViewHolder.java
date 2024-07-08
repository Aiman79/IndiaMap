package com.digitalhorizons.indiamapapp.marketplace.main.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class PropertyItemsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvName;
    private AppCompatImageView ivCheck;

    public PropertyItemsViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        ivCheck = itemView.findViewById(R.id.iv_checkbox);
    }

    public AppCompatTextView getTvName() {
        return tvName;
    }

    public void setTvName(AppCompatTextView tvName) {
        this.tvName = tvName;
    }

    public AppCompatImageView getIvCheck() {
        return ivCheck;
    }

    public void setIvCheck(AppCompatImageView ivCheck) {
        this.ivCheck = ivCheck;
    }
}

