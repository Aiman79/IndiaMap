package com.digitalhorizons.indiamapapp.home.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.home.model.DrawerMenuModel;

import java.util.ArrayList;

/**
 * Created by Aiman Pathan on 24-03-2023.
 */
public class DrawerMenuItemsREpository {
    private Context context;
    private ArrayList<DrawerMenuModel> itemListModelList = new ArrayList<>();

    private MutableLiveData<ArrayList<DrawerMenuModel>> mutableLiveData = new MutableLiveData<>();

    public DrawerMenuItemsREpository(Context context){
        this.context = context;
    }

    public ArrayList<DrawerMenuModel> getData() {
        DrawerMenuModel menuModel;

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.places));
        menuModel.setThumbnail("https://indiamap.com/images/chhatrapatishivajiterminussignificantbuilding_tourism_image.jpg");
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.food));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.experiences));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.promotions));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.shopping));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.restaurants));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.hotels));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.festivals));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.events));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.languages));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.favorites));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.reviews));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.support));
        itemListModelList.add(menuModel);

        menuModel = new DrawerMenuModel();
        menuModel.setMenu(context.getString(R.string.account));
        itemListModelList.add(menuModel);
        ;
        return itemListModelList;
    }

    public MutableLiveData<ArrayList<DrawerMenuModel>> getMutableLiveData(){
        mutableLiveData.setValue(itemListModelList);
        return mutableLiveData;
    }
}
