package com.digitalhorizons.indiamapapp.home.model;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

/**
 * Created by Aiman Pathan on 25-03-2023.
 */
public class GetLinearlayoutForMenuItems {
//    private ArrayList<BannerModel> bannerList = new ArrayList<>();
    private Context context;
    private String title, image;
    public GetLinearlayoutForMenuItems(Context context){
        this.context = context;
        getData();
    }

    public void getData(){
        this.title = "Dummy Title";
        this.image = "https://indiamap.com/images/jamamosque_tourism_image.jpg";
    }

    public void setUpView(LinearLayout mainLayout){
        AppCompatTextView tvTitle = new AppCompatTextView(context);
        //set title
        tvTitle.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        params.setMarginStart(10);
        params.setMarginEnd(10);
        tvTitle.setLayoutParams(params);

        AppCompatImageView ivImage = new AppCompatImageView(context);
        //set title
        Glide.with(ivImage).load(image).centerCrop().into(ivImage);
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                300);
        paramsImage.setMargins(10, 10, 10, 10);
        paramsImage.setMarginStart(10);
        paramsImage.setMarginEnd(10);
        ivImage.setLayoutParams(paramsImage);

        mainLayout.addView(tvTitle);
        mainLayout.addView(ivImage);
    }
}
