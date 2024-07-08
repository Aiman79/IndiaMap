package com.digitalhorizons.indiamapapp.marketplace.orders.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.digitalhorizons.indiamapapp.marketplace.itemdetails.ClsSaleItemDetailActivity;
import com.digitalhorizons.indiamapapp.marketplace.orders.model.OrdersModel;
import com.digitalhorizons.indiamapapp.marketplace.orders.viewmodel.OrdersViewModel;

public class ClsOrdersActivity extends AppCompatActivity {
    private RecyclerView rvItems;
    private AppCompatImageView ivFilter;
    private OrdersViewModel ordersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_orders);

        registerViews();
        init();
    }

    public void registerViews() {
        rvItems = findViewById(R.id.rv_items);

        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        ordersViewModel.getDataFromLocal();
    }

    public void init() {
        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
        ordersViewModel.setUpRecyclerView(rvItems, this);
        ordersViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                startOrderDetailActivity(ordersViewModel.getMarketPlaceItemWithPos(pos));
            }
        });
    }

    public void startOrderDetailActivity(OrdersModel model){
        Intent intent = new Intent(this, ClsOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppUtils.DATA_TRIP, model);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}