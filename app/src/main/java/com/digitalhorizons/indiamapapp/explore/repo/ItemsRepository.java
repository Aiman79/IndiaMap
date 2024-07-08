package com.digitalhorizons.indiamapapp.explore.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.model.CategoryModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemListModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemsModel;

import java.util.ArrayList;

public class ItemsRepository {
    private Context context;
    private ArrayList<ItemListModel> itemListModelList = new ArrayList<>();
    private ArrayList<CategoryModel> categoryList = new ArrayList<>();

    private MutableLiveData<ArrayList<CategoryModel>> mutableLiveData = new MutableLiveData<>();

    public ItemsRepository(Context context){
        this.context = context;
    }

    public ArrayList<CategoryModel> getCategoryData() {
        CategoryModel categoryModel;

        for (int i = 0; i < 10; i++) {
            categoryModel = new CategoryModel();
            categoryModel.setName("category" + i);
            categoryList.add(categoryModel);
        }
        return categoryList;
    }

    public MutableLiveData<ArrayList<CategoryModel>> getMutableLiveData(){
        mutableLiveData.setValue(categoryList);
        return mutableLiveData;
    }

    public ArrayList<ItemListModel> getData(){
        ItemListModel itemListModel;

        itemListModel = new ItemListModel();
        ArrayList<ItemsModel> itemsModels = new ArrayList<>();
        itemListModel.setTitle("Recommended");
//        itemListModel.setSize(2);

        ItemsModel itemsModel;

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemListModel.setItemList(itemsModels);
        itemListModelList.add(itemListModel);


        itemListModel = new ItemListModel();
        itemsModels = new ArrayList<>();
        itemListModel.setTitle("Recently added");
//        itemListModel.setSize(2);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemsModel = new ItemsModel();
        itemsModel.setItemWidth(150);
        itemsModel.setItemLength(150);
        itemsModel.setThumbnailDrawable(R.drawable.ic_bg_big);
        itemsModel.setTextAvailable(false);
        itemsModels.add(itemsModel);

        itemListModel.setItemList(itemsModels);
        itemListModelList.add(itemListModel);


        return itemListModelList;
    }
}
