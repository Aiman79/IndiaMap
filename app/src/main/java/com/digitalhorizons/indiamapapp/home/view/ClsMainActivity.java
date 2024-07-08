package com.digitalhorizons.indiamapapp.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.digitalhorizons.indiamapapp.home.viewmodel.MainViewModel;

public class ClsMainActivity extends AppCompatActivity {
   private FragmentManager fm;

    //drawerlayout
//    private NavController navController;
//    private AppBarConfiguration appBarConfiguration;
//    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private RecyclerView rvMenuItems;
    private MainViewModel mainViewModel;
    //toolbar
    private AppCompatTextView tvToolbarTitle;
    private AppCompatImageView ivBackToolbar;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerViews();
        setUpToolBar();
        init();
    }

    public void registerViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        rvMenuItems = findViewById(R.id.rv_menu_items);
        fm= getSupportFragmentManager();
    }

    public void setUpToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        ivBackToolbar = findViewById(R.id.iv_toolbar_back);

        ivBackToolbar.setVisibility(View.GONE);
        tvToolbarTitle.setText(getString(R.string.app_name));
//        setActionBar(mToolBar);
        mToolBar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_vector_menu));
//        mToolBar.se(true);
//        getActionBar().setDisplayShowHomeEnabled(true);

        mToolBar.setNavigationOnClickListener(view -> {
            if (drawerLayout.isOpen()){
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void init() {
        setUpDrawerLayout();
    }

    public void setUpDrawerLayout(){
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupWithNavController(navView, navController);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        loadDrawerItems();
    }



    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {

        fm
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void loadDrawerItems(){
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setLayoutInflater(LayoutInflater.from(this));
        mainViewModel.setLifecycleOwner(this);
        mainViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                Intent intent = new Intent(ClsMainActivity.this, ClsDrawerMenuItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppUtils.DATA_TRIP, mainViewModel.getItemWithPos(pos));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mainViewModel.getDataFromLocal();
        mainViewModel.setUpRecyclerView(rvMenuItems);
    }
}