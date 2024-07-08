package com.digitalhorizons.indiamapapp.marketplace.main.model;

import java.io.Serializable;

/**
 * Created by Aiman Pathan on 28-03-2023.
 */
public class MarketPlaceItemModel implements Serializable {
    private String name;
    private String price;
    private String thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
