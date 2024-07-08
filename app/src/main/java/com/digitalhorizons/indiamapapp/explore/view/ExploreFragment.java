package com.digitalhorizons.indiamapapp.explore.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.model.GetBannerViewClass;
import com.digitalhorizons.indiamapapp.explore.model.GetLinearLayoutForViewsClass;
import com.digitalhorizons.indiamapapp.explore.viewmodel.ExploreViewModel;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {
    //viewpager banners
    private ViewPager vpBanners;
    private TabLayout tabBanners;
    private GetBannerViewClass getBannerViewClass;
    private LinearLayout llItems;
    private GetLinearLayoutForViewsClass getLinearLayoutForViewsClass;
    private ExploreViewModel exploreViewModel;
    private ChangeFragmentListener changeFragmentListener;
    private RecyclerView rvCat;
    private View view;
    private boolean isFirstView = false;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            isFirstView = true;
            view = inflater.inflate(R.layout.fragment_explore, container, false);
            registerViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstView){
            init();
        }
        isFirstView = false;
    }

    public void registerViews(View view){
        vpBanners = view.findViewById(R.id.vp_banners);
        tabBanners = view.findViewById(R.id.tabDots);
        rvCat = view.findViewById(R.id.rv_cat);
        llItems = view.findViewById(R.id.ll_items);

        exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        exploreViewModel.setLayoutInflater(LayoutInflater.from(requireContext()));
        exploreViewModel.setLifecycleOwner(this);

        getBannerViewClass = new GetBannerViewClass();
        getLinearLayoutForViewsClass = new GetLinearLayoutForViewsClass(requireContext());
    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener){
        this.changeFragmentListener = changeFragmentListener;
    }


    public void init(){
        getBannerViewClass.setUpViewPagerForBanners(requireContext(), vpBanners, tabBanners);
        exploreViewModel.setUpCategoryRecyclerView(rvCat);
        LinearLayout layout = getLinearLayoutForViewsClass.getItemsView(true);
        llItems.addView(layout);
    }
}