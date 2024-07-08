package com.digitalhorizons.indiamapapp.planner.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.google.android.material.card.MaterialCardView;

public class TripsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView tvTitle;
    private AppCompatTextView tvDes;
    private AppCompatTextView tvDates;
    private MaterialCardView cdMain;
    public TripsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDes = itemView.findViewById(R.id.tv_desc);
        cdMain = itemView.findViewById(R.id.cd_main);
        tvDates = itemView.findViewById(R.id.tv_date);
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

    public MaterialCardView getCdMain() {
        return cdMain;
    }

    public void setCdMain(MaterialCardView cdMain) {
        this.cdMain = cdMain;
    }

    public AppCompatTextView getTvDates() {
        return tvDates;
    }

    public void setTvDates(AppCompatTextView tvDates) {
        this.tvDates = tvDates;
    }
}
