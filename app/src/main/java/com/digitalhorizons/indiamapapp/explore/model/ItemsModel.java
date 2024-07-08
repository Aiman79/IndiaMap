package com.digitalhorizons.indiamapapp.explore.model;

public class ItemsModel {
    private int itemLength;
    private int itemWidth;
    private String thumbnail;
    private int thumbnailDrawable;
    private boolean isTextAvailable = false;
    private String text;
    private int textMaxLines;
    private String elipsize;

    public int getItemLength() {
        return itemLength;
    }

    public void setItemLength(int itemLength) {
        this.itemLength = itemLength;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnailDrawable() {
        return thumbnailDrawable;
    }

    public void setThumbnailDrawable(int thumbnailDrawable) {
        this.thumbnailDrawable = thumbnailDrawable;
    }

    public boolean isTextAvailable() {
        return isTextAvailable;
    }

    public void setTextAvailable(boolean textAvailable) {
        isTextAvailable = textAvailable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextMaxLines() {
        return textMaxLines;
    }

    public void setTextMaxLines(int textMaxLines) {
        this.textMaxLines = textMaxLines;
    }

    public String getElipsize() {
        return elipsize;
    }

    public void setElipsize(String elipsize) {
        this.elipsize = elipsize;
    }
}
