package com.digitalhorizons.indiamapapp.planner.adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PlannerPagerAdapter extends FragmentStateAdapter {
   ArrayList<Fragment> fragmentArrayList = new ArrayList<>();



    public PlannerPagerAdapter(@NonNull androidx.fragment.app.FragmentManager fm, Lifecycle lifecycle,
                            ArrayList<Fragment> fragmentArrayList) {
        super(fm, lifecycle);
        this.fragmentArrayList = fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return fragmentArrayList.get(position);
    }
    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
