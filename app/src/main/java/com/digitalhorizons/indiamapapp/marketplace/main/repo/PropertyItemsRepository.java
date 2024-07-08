package com.digitalhorizons.indiamapapp.marketplace.main.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.marketplace.main.model.PropertyModel;

import java.util.ArrayList;

/**
 * Created by Aiman Pathan on 24-03-2023.
 */
public class PropertyItemsRepository {
    private Context context;
    private ArrayList<PropertyModel> itemListModelList = new ArrayList<>();

    private MutableLiveData<ArrayList<PropertyModel>> mutableLiveData = new MutableLiveData<>();

    public PropertyItemsRepository(Context context){
        this.context = context;
    }

    public ArrayList<PropertyModel> getData() {
        PropertyModel propertyModel;

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.villa));
        itemListModelList.add(propertyModel);

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.appartment));
        itemListModelList.add(propertyModel);

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.flat));
        itemListModelList.add(propertyModel);

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.house));
        itemListModelList.add(propertyModel);

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.basement));
        itemListModelList.add(propertyModel);

        propertyModel = new PropertyModel();
        propertyModel.setName(context.getString(R.string.farmhouse));
        itemListModelList.add(propertyModel);

        return itemListModelList;
    }

    public MutableLiveData<ArrayList<PropertyModel>> getMutableLiveData(){
        mutableLiveData.setValue(itemListModelList);
        return mutableLiveData;
    }
}
