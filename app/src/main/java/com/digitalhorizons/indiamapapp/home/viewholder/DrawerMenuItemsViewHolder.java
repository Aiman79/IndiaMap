package com.digitalhorizons.indiamapapp.home.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class DrawerMenuItemsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvMenu;
    private AppCompatImageView ivMenu;

    public DrawerMenuItemsViewHolder(View itemView){
        super(itemView);
        tvMenu = itemView.findViewById(R.id.tv_menu);
        ivMenu = itemView.findViewById(R.id.iv_menu);
    }

    public AppCompatTextView getTvMenu() {
        return tvMenu;
    }

    public void setTvMenu(AppCompatTextView tvMenu) {
        this.tvMenu = tvMenu;
    }

    public AppCompatImageView getIvMenu() {
        return ivMenu;
    }

    public void setIvMenu(AppCompatImageView ivMenu) {
        this.ivMenu = ivMenu;
    }
}

