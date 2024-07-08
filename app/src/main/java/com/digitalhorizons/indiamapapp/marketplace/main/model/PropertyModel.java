package com.digitalhorizons.indiamapapp.marketplace.main.model;

import java.io.Serializable;

/**
 * Created by Aiman Pathan on 28-03-2023.
 */
public class PropertyModel implements Serializable {
    private String name;
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
