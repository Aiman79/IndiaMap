package com.digitalhorizons.indiamapapp.explore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;


import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.model.BannerModel;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    Context context;
  /*  private int[] GalImages = new int[] {
            R.drawable.ic_home_banner1,
            R.drawable.ic_top_account,
            R.drawable.ic_fav_img1
    };*/

    private ArrayList<BannerModel> mList;
 
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<BannerModel> list){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        AppCompatImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mList.get(position).getDrawable());
        imageView.setOnClickListener(view -> {

        });

        AppCompatImageView ivNext = itemView.findViewById(R.id.ic_next);
        AppCompatImageView ivPrev = itemView.findViewById(R.id.iv_prev);

        ivNext.setOnClickListener(view -> {
            if (onPagerItemClicked != null){
                onPagerItemClicked.nextButtonClicked(position);
            }
        });

        ivPrev.setOnClickListener(view -> {
            if (onPagerItemClicked != null){
                onPagerItemClicked.prevButtonClicked(position);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }

    public interface OnPagerItemClicked{
        void nextButtonClicked(int currentPos);
        void prevButtonClicked(int currentPos);
    }

    private OnPagerItemClicked onPagerItemClicked;

    public void setOnPagerItemClicked(OnPagerItemClicked onPagerItemClicked){
        this.onPagerItemClicked = onPagerItemClicked;
    }
}