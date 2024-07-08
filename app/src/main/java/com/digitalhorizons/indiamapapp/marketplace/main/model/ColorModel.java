package com.digitalhorizons.indiamapapp.marketplace.main.model;

import java.io.Serializable;

/**
 * Created by Aiman Pathan on 28-03-2023.
 */
public class ColorModel implements Serializable {
    private String name;
    private int colorId;
    private int drawable;
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

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
