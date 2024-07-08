package com.digitalhorizons.indiamapapp.home.model;

import java.io.Serializable;

/**
 * Created by Aiman Pathan on 28-03-2023.
 */
public class DrawerMenuModel implements Serializable {
    private String menu;
    private String thumbnail;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
