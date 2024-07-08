package com.digitalhorizons.indiamapapp.marketplace.main.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.marketplace.main.model.MarketPlaceItemModel;

import java.util.ArrayList;

/**
 * Created by Aiman Pathan on 24-03-2023.
 */
public class MarketPlaceItemsRepository {
    private Context context;
    private ArrayList<MarketPlaceItemModel> itemListModelList = new ArrayList<>();

    private MutableLiveData<ArrayList<MarketPlaceItemModel>> mutableLiveData = new MutableLiveData<>();

    public MarketPlaceItemsRepository(Context context){
        this.context = context;
    }

    public ArrayList<MarketPlaceItemModel> getData() {
        MarketPlaceItemModel marketPlaceItemModel;

        for (int i = 0; i < 10; i++){
            marketPlaceItemModel = new MarketPlaceItemModel();
            marketPlaceItemModel.setName("Item " + i);
            marketPlaceItemModel.setPrice(String.valueOf(10000 + (i * i)));
            marketPlaceItemModel.setThumbnail("https://exej2saedb8.exactdn.com/wp-content/uploads/2022/02/Screen-Shot-2022-02-04-at-2.28.40-PM.png?strip=all&lossy=1&ssl=1");
            itemListModelList.add(marketPlaceItemModel);
        }

        return itemListModelList;
    }

    public MutableLiveData<ArrayList<MarketPlaceItemModel>> getMutableLiveData(){
        mutableLiveData.setValue(itemListModelList);
        return mutableLiveData;
    }
}
