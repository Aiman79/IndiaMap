package com.digitalhorizons.indiamapapp.planner.repo;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.explore.model.CategoryModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemListModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemsModel;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;

import java.util.ArrayList;

public class TripsRepository {
    private Context context;
    private ArrayList<TripModel> mList = new ArrayList<>();
    private ArrayList<DocumentPojo> mDocsList = new ArrayList<>();
    private ArrayList<DocumentPojo> mAlbumList = new ArrayList<>();

    private MutableLiveData<ArrayList<TripModel>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<DocumentPojo>> mutableDocsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<DocumentPojo>> mutableAlbumLiveData = new MutableLiveData<>();

    public TripsRepository(Context context) {
        this.context = context;
    }

    public void setData(TripModel tripModel){
        mList.add(tripModel);
    }

    public ArrayList<TripModel> getData() {
        TripModel model;

        for (int i = 0; i < 10; i++) {
            model = new TripModel();
            model.setStartDate(context.getString(R.string.dummy_start_dates));
            model.setEndDate(context.getString(R.string.dummy_end_dates));
            model.setTitle(context.getString(R.string.dummy_title));
            model.setDesc(context.getString(R.string.dummy_desc));
            getDocsAlbumData();
            model.setDocumentList(mDocsList);
            model.setAlbumList(mAlbumList);
            mList.add(model);
        }
        return mList;
    }

    public void getDocsAlbumData() {
        DocumentPojo model;

        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.media.documents/document/image%3A57"));
        model.setUrl("https://picsum.photos/id/237/200/300");
        model.setName("Title.jpg");
        model.setType("jpg");
        mDocsList.add(model);
        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.media.documents/document/image%3A57"));
        model.setUrl("https://picsum.photos/id/237/200/300");
        model.setName("Title.jpg");
        model.setType("jpg");
        mAlbumList.add(model);

        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.downloads.documents/document/540"));
        model.setUrl("https://picsum.photos/seed/picsum/200/300");
        model.setName("Emailing CELPIP-Speaking-Practice.pdf");
        model.setType("pdf");
        mDocsList.add(model);
        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.downloads.documents/document/540"));
        model.setUrl("https://picsum.photos/seed/picsum/200/300");
        model.setName("Emailing CELPIP-Speaking-Practice.pdf");
        model.setType("pdf");
        mAlbumList.add(model);

        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.media.documents/document/audio%3A15"));
        model.setName("Over the Horizon");
        model.setType("mp3");
        mDocsList.add(model);
        model = new DocumentPojo();
        model.setDocumentUrl(Uri.parse("content://com.android.providers.media.documents/document/audio%3A15"));
        model.setName("Over the Horizon");
        model.setType("mp3");
        mAlbumList.add(model);

    }

    public MutableLiveData<ArrayList<TripModel>> getMutableLiveData() {
        mutableLiveData.setValue(mList);
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<DocumentPojo>> getMutableDocsLiveData() {
        mutableDocsLiveData.setValue(mDocsList);
        return mutableDocsLiveData;
    }

    public MutableLiveData<ArrayList<DocumentPojo>> getMutableAlbumLiveData() {
        mutableAlbumLiveData.setValue(mAlbumList);
        return mutableAlbumLiveData;
    }
}
