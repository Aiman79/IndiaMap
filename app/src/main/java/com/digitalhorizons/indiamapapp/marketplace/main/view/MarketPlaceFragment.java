package com.digitalhorizons.indiamapapp.marketplace.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;
import com.digitalhorizons.indiamapapp.marketplace.itemdetails.ClsSaleItemDetailActivity;
import com.digitalhorizons.indiamapapp.marketplace.location.view.MapFragment;
import com.digitalhorizons.indiamapapp.marketplace.main.model.MarketPlaceItemModel;
import com.digitalhorizons.indiamapapp.marketplace.orders.view.ClsOrdersActivity;
import com.digitalhorizons.indiamapapp.marketplace.main.viewmodel.MarketplaceFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketPlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketPlaceFragment extends Fragment {
    //viewpager banners
    private RecyclerView rvItems;
    private FloatingActionButton fabAdd;
    private AppCompatImageView ivFilter, ivLocation;
    private ChangeFragmentListener changeFragmentListener;
    private MarketplaceFragmentViewModel marketplaceFragmentViewModel;
    private View view;
    private boolean isFirstView = false;
    private AppCompatTextView tvOrders;

    public MarketPlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static MarketPlaceFragment newInstance() {
        MarketPlaceFragment fragment = new MarketPlaceFragment();
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
            view = inflater.inflate(R.layout.fragment_market_place, container, false);
            registerViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        init();
        if (isFirstView){
            addListeners();
            init();
        }
        isFirstView = false;
    }

    public void registerViews(View view) {
        rvItems = view.findViewById(R.id.rv_items);
        fabAdd = view.findViewById(R.id.fab_add);
        ivFilter = view.findViewById(R.id.iv_filter);
        ivLocation = view.findViewById(R.id.iv_location);
        tvOrders = view.findViewById(R.id.tv_orders);

        marketplaceFragmentViewModel = new ViewModelProvider(this).get(MarketplaceFragmentViewModel.class);
        marketplaceFragmentViewModel.getDataFromLocal();
    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }


    public void init() {
        setUpRecyclerView();
    }

    public void addListeners(){
        fabAdd.setOnClickListener(view -> startAddTripActivity());
        ivFilter.setOnClickListener(view1 -> startFilterActivity());
        ivLocation.setOnClickListener(view1 -> {
            if (changeFragmentListener != null){
                changeFragmentListener.onFragmentChange(-1, MapFragment.newInstance(), "map", "map");
            }
        });

        tvOrders.setOnClickListener(view1 -> startOrdersActivity());
    }

    public void startAddTripActivity(){
        Intent intent = new Intent(requireContext(), ClsAddItemSaleActivity.class);
//        activityResultLauncherAddTrip.launch(intent);
        startActivity(intent);
    }

    public void startFilterActivity(){
        Intent intent = new Intent(requireContext(), ClsMarketPlaceFilterActivity.class);
        startActivity(intent);
    }

    public void startOrdersActivity(){
        Intent intent = new Intent(requireContext(), ClsOrdersActivity.class);
        startActivity(intent);
    }

    /*public ActivityResultLauncher<Intent> activityResultLauncherAddTrip = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result != null){
                    Intent intent = result.getData();
                    if (intent != null){
                        if (intent.getExtras() != null){
                            TripModel tripModel = (TripModel) intent.getExtras().getParcelable(AppUtils.DATA_TRIP);
                            if (tripModel != null){
                                marketplaceFragmentViewModel.addItem(tripModel);
                            }
                        }
                    }
                }
            });*/

    public void startMarketPlaceDetailActivity(MarketPlaceItemModel model){
        Intent intent = new Intent(requireContext(), ClsSaleItemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppUtils.DATA_TRIP, model);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setUpRecyclerView(){
        marketplaceFragmentViewModel.setUpRecyclerView(rvItems, this);
        marketplaceFragmentViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                startMarketPlaceDetailActivity(marketplaceFragmentViewModel.getMarketPlaceItemWithPos(pos));
            }
        });
    }
}