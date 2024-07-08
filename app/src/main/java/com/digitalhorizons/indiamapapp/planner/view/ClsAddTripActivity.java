package com.digitalhorizons.indiamapapp.planner.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.planner.adapter.PlannerPagerAdapter;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ClsAddTripActivity extends AppCompatActivity {
    private TabLayout tab_layout;
    private ViewPager2 view_pager;
    private PlannerPagerAdapter plannerPagerAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> mTabsList = new ArrayList<>();
    private AppCompatButton btnNext;
    private TabLayoutMediator.TabConfigurationStrategy tab;

    private TripModel mTripModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_add_trip);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        initializeAll();
        loadViewPager();
        addListeners();
//        loadTabLayout();
    }

    /* This method is for setting tablayout and viewpager
       created by Aiman Pathan 22/02/2023 */
    void loadViewPager(){
        try {
            mTabsList.add(getString(R.string.details));
//            mTabsList.add(getString(R.string.date));
            mTabsList.add(getString(R.string.documents));
            mTabsList.add(getString(R.string.album));

            fragmentList.add(DetailsFragment.newInstance());
//            fragmentList.add(DateFragment.newInstance());
            fragmentList.add(DocumentsFragment.newInstance());
            fragmentList.add(AlbumsFragment.newInstance());

            plannerPagerAdapter = new PlannerPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
            view_pager.setAdapter(plannerPagerAdapter);

            tab = new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                    getData();
                    tab.setText(mTabsList.get(position));
                    tab.view.setEnabled(false
                    );
                }
            };
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_layout, view_pager, tab);
            tabLayoutMediator.attach();
            view_pager.setUserInputEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* This method is for initializing all view
       created by Aiman Pathan 22/02/2023 */
    void initializeAll(){
        try {
            mTripModel = new TripModel();
            tab_layout = findViewById(R.id.tab_layout);
            view_pager =  findViewById(R.id.view_pager);
            btnNext =  findViewById(R.id.btn_next);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addListeners(){
        btnNext.setOnClickListener(view -> {
            try {
                if (view_pager.getCurrentItem() < mTabsList.size() - 1){
//                    TripModel model = mTabsList.get(view_pager.getCurrentItem()).
                    if (getData()){
                        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                    }
                } else {
                    getData();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppUtils.DATA_TRIP, mTripModel);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } catch (Exception e){
                e.printStackTrace();
//                finish();
            }
        });
    }

    public boolean getData(){
        Fragment fragment = fragmentList.get(view_pager.getCurrentItem());
        TripModel tripModel = null;
        if (fragment instanceof DetailsFragment){
            tripModel = ((DetailsFragment) fragment).getValues();
           if (tripModel != null){
               mTripModel.setTitle(tripModel.getTitle());
               mTripModel.setDesc(tripModel.getDesc());
               mTripModel.setCountry(tripModel.getCountry());
               mTripModel.setCity(tripModel.getCity());
               mTripModel.setStartDate(tripModel.getStartDate());
               mTripModel.setEndDate(tripModel.getEndDate());
           }
        } else if (fragment instanceof DocumentsFragment){
            tripModel = ((DocumentsFragment) fragment).getValues();
            if (tripModel != null){
                mTripModel.setDocumentList(tripModel.getDocumentList());
            }
        } else if (fragment instanceof AlbumsFragment){
            tripModel = ((AlbumsFragment) fragment).getValues();
            if (tripModel != null){
                mTripModel.setAlbumList(tripModel.getAlbumList());
            }
        }

        return tripModel != null;
    }
}