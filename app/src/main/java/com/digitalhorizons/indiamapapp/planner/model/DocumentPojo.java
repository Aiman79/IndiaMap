package com.digitalhorizons.indiamapapp.planner.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DocumentPojo implements Parcelable {
    private Uri documentUrl;
    private String url;
    private String name;
    private String type;

    public DocumentPojo() {
    }
    public DocumentPojo(Uri documentUrl, String name) {
        this.documentUrl = documentUrl;
        this.name = name;
    }

    protected DocumentPojo(Parcel in) {
        documentUrl = in.readParcelable(Uri.class.getClassLoader());
        url = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<DocumentPojo> CREATOR = new Creator<DocumentPojo>() {
        @Override
        public DocumentPojo createFromParcel(Parcel in) {
            return new DocumentPojo(in);
        }

        @Override
        public DocumentPojo[] newArray(int size) {
            return new DocumentPojo[size];
        }
    };

    public Uri getDocumentUrl() {
        return documentUrl;
    }
    public void setDocumentUrl(Uri document) {
        this.documentUrl = document;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(documentUrl, i);
        parcel.writeString(url);
        parcel.writeString(name);
        parcel.writeString(type);
    }
}
