package com.digitalhorizons.indiamapapp.explore.model;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.adapter.CustomPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class GetBannerViewClass {

    public ArrayList<BannerModel> getBannerData(){
        ArrayList<BannerModel> bannerList = new ArrayList<>();
        BannerModel bannerModel;

        bannerModel = new BannerModel();
        bannerModel.setDrawable(R.drawable.ic_home_banner1);
        bannerList.add(bannerModel);

        bannerModel = new BannerModel();
        bannerModel.setDrawable(R.drawable.ic_fav_img1);
        bannerList.add(bannerModel);

        bannerModel = new BannerModel();
        bannerModel.setDrawable(R.drawable.ic_top_account);
        bannerList.add(bannerModel);

        return bannerList;
    }
    public void setUpViewPagerForBanners(Context context, ViewPager vpBanners, TabLayout tabBanners){
        ArrayList<BannerModel> bannerList = getBannerData();

        CustomPagerAdapter bannerPagerAdapter = new CustomPagerAdapter(context, bannerList);
        vpBanners.setAdapter(bannerPagerAdapter);
        tabBanners.setupWithViewPager(vpBanners, true);

        bannerPagerAdapter.setOnPagerItemClicked(new CustomPagerAdapter.OnPagerItemClicked() {
            @Override
            public void nextButtonClicked(int currentPos) {
                try {
                    tabBanners.selectTab(tabBanners.getTabAt(currentPos + 1));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void prevButtonClicked(int currentPos) {
                try {
                    tabBanners.selectTab(tabBanners.getTabAt(currentPos - 1));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
