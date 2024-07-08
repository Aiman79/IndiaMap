package com.digitalhorizons.indiamapapp.planner.model;

import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class TripModel implements Parcelable {
    private String title;
    private String desc;
    private String country;
    private String city;
    private String startDate;
    private String endDate;
    private ArrayList<DocumentPojo> documentList = new ArrayList<>();
    private ArrayList<DocumentPojo> albumList = new ArrayList<>();

    public TripModel() {
    }

    protected TripModel(Parcel in) {
        title = in.readString();
        desc = in.readString();
        country = in.readString();
        city = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        in.readTypedList(documentList, DocumentPojo.CREATOR);
        in.readTypedList(albumList, DocumentPojo.CREATOR);
    }

    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
        @Override
        public TripModel createFromParcel(Parcel in) {
            return new TripModel(in);
        }

        @Override
        public TripModel[] newArray(int size) {
            return new TripModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<DocumentPojo> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<DocumentPojo> documentList) {
        this.documentList = documentList;
    }

    public ArrayList<DocumentPojo> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(ArrayList<DocumentPojo> albumList) {
        this.albumList = albumList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeTypedList(documentList);
        parcel.writeTypedList(albumList);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeParcelableList(documentList, i);
            parcel.writeParcelableList(albumList, i);
        } else {
            parcel.writeList(documentList);
            parcel.writeList(albumList);
        }*/
    }

}
