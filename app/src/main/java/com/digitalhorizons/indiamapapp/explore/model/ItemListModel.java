package com.digitalhorizons.indiamapapp.explore.model;

import java.util.ArrayList;

public class ItemListModel {
    private String title;
//    private  int size;
    private ArrayList<ItemsModel> itemList = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /*  public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }*/

    public ArrayList<ItemsModel> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<ItemsModel> itemList) {
        this.itemList = itemList;
    }
}
