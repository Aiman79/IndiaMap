package com.digitalhorizons.indiamapapp.search.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.digitalhorizons.indiamapapp.search.model.SearchItemsModel;

import java.util.ArrayList;

public class SearchItemsRepository {
    private Context context;
    private ArrayList<SearchItemsModel> mList = new ArrayList<>();

    private MutableLiveData<ArrayList<SearchItemsModel>> mutableLiveData = new MutableLiveData<>();

    public SearchItemsRepository(Context context) {
        this.context = context;
    }

    public void clearAllData(){
        mList.clear();
    }


    public ArrayList<SearchItemsModel> getData() {
        SearchItemsModel model;
        model = new SearchItemsModel();
        model.setName("GATEWAY OF INDIA");
        model.setDesc("The Gateway of India is recognized as one of the most iconic buildings in India and was built along the Port of Mumbai in 1924. Situated at the top of Apollo Bunder, this huge building is a monument built to commemorate King George V and his wife Queen Marry touring in India. This famous tourist attraction used to be a colony of British people, but now it attracts street food vendors, hawkers, nature lovers and photographers to appreciate its charm .");
        model.setThumbnail("https://indiamap.com/images/gatewayofindia_tourism_image.jpg");
        mList.add(model);

        model = new SearchItemsModel();
        model.setName("CHHATRAPATI SHIVAJI TERMINUS");
        model.setDesc("Formerly known as the Victoria Terminus, the Chhatrapati Shivaji Terminus stands majestically on the shoreline of the Arabian Sea, is the first ever railway station to function in India and is also a fine example of the 19th century railway architecture. The design of the building exhibits a great inspiration of the Indian architecture and Victorian Gothic Revival. ");
        model.setThumbnail("https://indiamap.com/images/chhatrapatishivajiterminussignificantbuilding_tourism_image.jpg");
        mList.add(model);

        model = new SearchItemsModel();
        model.setName("JAMA MOSQUE (JUMA MASJID)");
        model.setDesc("The historic monument of Jama Mosque has been sitting since the 18th century. The building was once located near Crawford Market in Mumbai but was placed at Esplanade only to be get destroyed again by orders from Governor William Hornby, who emphasized on having no construction within 600 yards of the Fort area. ");
        model.setThumbnail("https://indiamap.com/images/jamamosque_tourism_image.jpg");
        mList.add(model);

        model = new SearchItemsModel();
        model.setName("KANHERI CAVES");
        model.setDesc("The Kanheri Caves are a widespread network of pre-historic caves situated in the Borivali area on the outskirts of Mumbai. This network of more than one hundred caves dates back to the 1st century AD, and as per belief the construction lasted until the 10th century. These caves are home to Buddhist sculpture, and the style is taken from the historical Buddhist motifs.");
        model.setThumbnail("https://indiamap.com/images/kanhericaves_tourism_image.jpg");
        mList.add(model);
        return mList;
    }

    public MutableLiveData<ArrayList<SearchItemsModel>> getMutableLiveData() {
        mutableLiveData.setValue(mList);
        return mutableLiveData;
    }
}
