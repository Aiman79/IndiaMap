package com.digitalhorizons.indiamapapp.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.digitalhorizons.indiamapapp.explore.model.GetBannerViewClass;
import com.digitalhorizons.indiamapapp.home.model.DrawerMenuModel;
import com.digitalhorizons.indiamapapp.home.model.GetLinearlayoutForMenuItems;
import com.google.android.material.tabs.TabLayout;

public class ClsDrawerMenuItemActivity extends AppCompatActivity {
    //viewpager banners
    private ViewPager vpBanners;
    private TabLayout tabBanners;
    private GetBannerViewClass getBannerViewClass;
    private LinearLayout llMain;
    private GetLinearlayoutForMenuItems getLinearlayoutForMenuItems;
    private DrawerMenuModel drawerMenuModel;
    //toolbar
    private AppCompatTextView tvToolbarTitle;
    private AppCompatImageView ivBackToolbar;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_drawer_menu_item);

        getBundle();
        registerViews();
        setUpToolBar();
        init();
    }

    public void getBundle(){
        if (getIntent() != null && getIntent().getExtras() != null){
            drawerMenuModel = (DrawerMenuModel) getIntent().getExtras().getSerializable(AppUtils.DATA_TRIP);
        }
    }

    public void registerViews(){
        llMain = findViewById(R.id.ll_main);
        vpBanners = findViewById(R.id.vp_banners);
        tabBanners = findViewById(R.id.tabDots);
        getLinearlayoutForMenuItems = new GetLinearlayoutForMenuItems(this);
        getBannerViewClass = new GetBannerViewClass();
    }

    public void init(){
        getBannerViewClass.setUpViewPagerForBanners(this, vpBanners, tabBanners);
        getLinearlayoutForMenuItems.setUpView(llMain);
    }

    public void setUpToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        ivBackToolbar = findViewById(R.id.iv_toolbar_back);

        ivBackToolbar.setVisibility(View.VISIBLE);
        if (drawerMenuModel != null){
            tvToolbarTitle.setText(drawerMenuModel.getMenu());
        }
//        setActionBar(mToolBar);
//        mToolBar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_vector_menu));
//        mToolBar.se(true);
//        getActionBar().setDisplayShowHomeEnabled(true);

        ivBackToolbar.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}