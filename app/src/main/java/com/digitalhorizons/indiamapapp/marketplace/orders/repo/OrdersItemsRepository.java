package com.digitalhorizons.indiamapapp.marketplace.orders.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.marketplace.orders.model.OrdersModel;

import java.util.ArrayList;

/**
 * Created by Aiman Pathan on 24-03-2023.
 */
public class OrdersItemsRepository {
    private Context context;
    private ArrayList<OrdersModel> itemListModelList = new ArrayList<>();

    private MutableLiveData<ArrayList<OrdersModel>> mutableLiveData = new MutableLiveData<>();

    public OrdersItemsRepository(Context context){
        this.context = context;
    }

    public ArrayList<OrdersModel> getData() {
        OrdersModel ordersModel;

        for (int i = 0; i < 10; i++){
            ordersModel = new OrdersModel();
            ordersModel.setName("Item " + i);
            ordersModel.setPrice(String.valueOf(10000 + (i * i)));
            ordersModel.setStatus("Pending");
            ordersModel.setThumbnail("https://exej2saedb8.exactdn.com/wp-content/uploads/2022/02/Screen-Shot-2022-02-04-at-2.28.40-PM.png?strip=all&lossy=1&ssl=1");
            itemListModelList.add(ordersModel);
        }

        return itemListModelList;
    }

    public MutableLiveData<ArrayList<OrdersModel>> getMutableLiveData(){
        mutableLiveData.setValue(itemListModelList);
        return mutableLiveData;
    }
}
