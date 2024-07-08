package com.digitalhorizons.indiamapapp.marketplace.main.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.marketplace.main.model.ColorModel;

import java.util.ArrayList;

/**
 * Created by Aiman Pathan on 24-03-2023.
 */
public class FilterRepository {
    private Context context;
    private ArrayList<ColorModel> itemListModelList = new ArrayList<>();

    private MutableLiveData<ArrayList<ColorModel>> mutableLiveData = new MutableLiveData<>();

    public FilterRepository(Context context){
        this.context = context;
    }

    public ArrayList<ColorModel> getData() {
        ColorModel colorModel;

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_red);
        itemListModelList.add(colorModel);

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_black);
        itemListModelList.add(colorModel);

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_dark_primary_holo);
        itemListModelList.add(colorModel);

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_teal);
        itemListModelList.add(colorModel);

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_dark_primary);
        itemListModelList.add(colorModel);

        colorModel = new ColorModel();
        colorModel.setDrawable(R.drawable.circle_purple);
        itemListModelList.add(colorModel);

        return itemListModelList;
    }

    public MutableLiveData<ArrayList<ColorModel>> getMutableLiveData(){
        mutableLiveData.setValue(itemListModelList);
        return mutableLiveData;
    }
}
