package com.digitalhorizons.indiamapapp.marketplace.orders.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.digitalhorizons.indiamapapp.marketplace.orders.model.OrdersModel;

public class ClsOrderDetailActivity extends AppCompatActivity {
    private AppCompatTextView tvName, tvPrice, tvStatus, tvAddress;
    private AppCompatImageView ivThumbnail;
    private OrdersModel ordersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_order_detail);

        getBundle();
        registerViews();
        init();
    }

    public void getBundle(){
        if (getIntent() != null && getIntent().getExtras() != null){
            ordersModel = (OrdersModel) getIntent().getExtras().getSerializable(AppUtils.DATA_TRIP);
        }
    }

    public void registerViews(){
        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvStatus = findViewById(R.id.tv_status);
        tvAddress = findViewById(R.id.tv_address);
        ivThumbnail = findViewById(R.id.iv_thumbnail);
    }

    public void init(){
        if (ordersModel != null){
            tvName.setText(ordersModel.getName());
            tvPrice.setText(ordersModel.getPrice());
            tvStatus.setText(ordersModel.getStatus());
            tvAddress.setText(ordersModel.getAddress());

            String url = ordersModel.getThumbnail();
            if (url != null && !url.isEmpty()){
                Glide.with(ivThumbnail).load(url).into(ivThumbnail);
            } else {
                ivThumbnail.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            item.setIcon(R.drawable.ic_favorite_red);
        }
        return super.onOptionsItemSelected(item);
    }
}