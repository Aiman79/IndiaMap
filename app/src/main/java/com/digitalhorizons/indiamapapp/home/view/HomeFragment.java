package com.digitalhorizons.indiamapapp.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.view.ExploreFragment;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;
import com.digitalhorizons.indiamapapp.marketplace.main.view.MarketPlaceFragment;
import com.digitalhorizons.indiamapapp.planner.view.PlannerFragment;
import com.digitalhorizons.indiamapapp.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private View view;
    private boolean isFirstView;

    //view variables
    private BottomNavigationView bottomNavigationView;

    //other variables
    private ExploreFragment exploreFragment;
    private PlannerFragment plannerFragment;
    private SearchFragment searchFragment;
    private MarketPlaceFragment marketPlaceFragment ;
//    private AccountFragment accountFragment;

    //toolbar variables
//    private Toolbar toolbar;
//    private AppCompatTextView tvTitle;
    private AppCompatActivity mActivity;
    private Context mContext;
    private FragmentManager fm;
    private ChangeFragmentListener changeFragmentListener;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
            view = inflater.inflate(R.layout.fragment_home, container, false);
            registerViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstView){
            registerViews(view);
//            setUpToolBar();
            init();
            addListeners();
        }
        isFirstView = false;
    }

    public void registerViews(View view){

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        exploreFragment = ExploreFragment.newInstance();
        plannerFragment = PlannerFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        marketPlaceFragment = MarketPlaceFragment.newInstance();

        fm = getChildFragmentManager();

        changeFragmentListener = (id, fragment, tag, backtack) -> {
            if(id == -1){
                replaceFragment(R.id.fm_main, fragment, tag, backtack);
            } else {
                replaceFragment(id, fragment, tag, backtack);
            }
        };
    }

    public void addListeners() {
        exploreFragment.setChangeFragmentListener(changeFragmentListener);
        plannerFragment.setChangeFragmentListener(changeFragmentListener);
        searchFragment.setChangeFragmentListener(changeFragmentListener);
        marketPlaceFragment.setChangeFragmentListener(changeFragmentListener);
    }


    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener){
        this.changeFragmentListener = changeFragmentListener;
    }


    public void init(){
        setUpBottomNavMenu();
    }

    public void setUpBottomNavMenu() {

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            switch (id) {
                //check id
                case R.id.explore:
//                    tvTitle.setText(getString(R.string.menu));
                    replaceFragment(R.id.fm_main, exploreFragment, "explore", "explore");
                    break;
                case R.id.planner:
//                    tvTitle.setText(getString(R.string.menu));
                    replaceFragment(R.id.fm_main, plannerFragment, "planner", "planner");
                    break;
                case R.id.search:
//                    tvTitle.setText(getString(R.string.menu));
                    replaceFragment(R.id.fm_main, searchFragment, "search", "search");
                    break;
                case R.id.market:
//                    tvTitle.setText(getString(R.string.menu));
                    replaceFragment(R.id.fm_main, marketPlaceFragment, "marketplace", "marketplace");
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.explore);
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
}