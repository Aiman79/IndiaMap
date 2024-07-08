package com.digitalhorizons.indiamapapp.marketplace.orders.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;


public class OrdersItemsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvName, tvPrice, tvStatus;
    private AppCompatImageView ivThumbnail;
    private CardView cdMain;

    public OrdersItemsViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvPrice = itemView.findViewById(R.id.tv_price);
        tvStatus = itemView.findViewById(R.id.tv_status);
        ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        cdMain = itemView.findViewById(R.id.cd_main);
    }

    public AppCompatTextView getTvName() {
        return tvName;
    }

    public void setTvName(AppCompatTextView tvName) {
        this.tvName = tvName;
    }

    public AppCompatTextView getTvPrice() {
        return tvPrice;
    }

    public void setTvPrice(AppCompatTextView tvPrice) {
        this.tvPrice = tvPrice;
    }

    public AppCompatImageView getIvThumbnail() {
        return ivThumbnail;
    }

    public void setIvThumbnail(AppCompatImageView ivThumbnail) {
        this.ivThumbnail = ivThumbnail;
    }

    public CardView getCdMain() {
        return cdMain;
    }

    public void setCdMain(CardView cdMain) {
        this.cdMain = cdMain;
    }

    public AppCompatTextView getTvStatus() {
        return tvStatus;
    }

    public void setTvStatus(AppCompatTextView tvStatus) {
        this.tvStatus = tvStatus;
    }
}

