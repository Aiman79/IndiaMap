package com.digitalhorizons.indiamapapp.marketplace.orders.model;

import java.io.Serializable;

/**
 * Created by Aiman Pathan on 06-04-2023.
 */
public class OrdersModel implements Serializable {
    private String thumbnail;
    private String name;
    private String status;
    private String price;
    private String address;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
