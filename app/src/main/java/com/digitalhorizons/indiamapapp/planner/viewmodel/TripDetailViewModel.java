package com.digitalhorizons.indiamapapp.planner.viewmodel;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;

import java.util.ArrayList;

public class TripDetailViewModel extends AndroidViewModel {
    private Application context;
//    private AppCompatActivity mActivity;
//    private TripsRepository tripsRepository;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onDocsItemClickedListener;

    private CustomisedBindViewHolderListener customisedAlbumBindViewHolderListener;
    private GetViewHolderListener getAlbumViewHolderListener;
    private UpdateRecyclerDataListener updateAlbumRecyclerDataListener;
    private OnItemClickedListener onAlbumItemClickedListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter, albumAdapter;
    private ArrayList<DocumentPojo> mDocsList = new ArrayList<>();
    private ArrayList<DocumentPojo> mAlbumList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    //live data
    private MutableLiveData<ArrayList<DocumentPojo>> mutableLiveDataDocs;
    private MutableLiveData<ArrayList<DocumentPojo>> mutableLiveDataAlbum;

    public TripDetailViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
//        tripsRepository = new TripsRepository(context);
    }

    public void setOnDocsItemClickedListener(OnItemClickedListener onDocsItemClickedListener){
        this.onDocsItemClickedListener = onDocsItemClickedListener;
    }

    public void setOnAlbumItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onAlbumItemClickedListener = onItemClickedListener;
    }

    /*public void getDataFromLocal() {
        tripsRepository.getDocsAlbumData();
    }*/

    public void setmDocsList(ArrayList<DocumentPojo> docsList){
        this.mDocsList = docsList;
        mutableLiveDataDocs = new MutableLiveData<>();
        mutableLiveDataDocs.setValue(mDocsList);
    }

    public void setmAlbumList(ArrayList<DocumentPojo> albumList){
        this.mAlbumList = albumList;
        mutableLiveDataAlbum = new MutableLiveData<>();
        mutableLiveDataAlbum.setValue(mAlbumList);
    }


    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
    }


    public MutableLiveData<ArrayList<DocumentPojo>> getAllDocsItems(){
        return mutableLiveDataDocs;
    }

    public MutableLiveData<ArrayList<DocumentPojo>> getAllAlbumItems(){
        return mutableLiveDataAlbum;
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems) {
        addListeners(rvItems);

        adapter = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapter, updateRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }

    // method for adding all listeners
    public void addListeners(RecyclerView rvItems) {

        /*getViewTypeListener = position -> {
            if (mList.get(position).isCategory()){
                return AppUtils.VIEW_TYPE_CAT;
            } else {
                return AppUtils.VIEW_TYPE_ITEM;
            }
        };*/

        updateRecyclerDataListener = () -> {
                getAllDocsItems().observe(lifecycleOwner, itemModels -> {
//                    mDocsList.clear();
//                    mDocsList.addAll(itemModels);
                    adapter.updateUserList(mDocsList);
                });
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAdapterPosition();
            TripDetailsViewHolder itemsViewHolder = ((TripDetailsViewHolder) viewHolder);
            itemsViewHolder.getTvCat().setText(mDocsList.get(pos).getName());
            itemsViewHolder.getIvAlbum().setVisibility(View.GONE);
            itemsViewHolder.itemView.setOnClickListener(view -> {
                if (onDocsItemClickedListener != null){
                    onDocsItemClickedListener.OnItemClicked(pos);
                }
            });
//            itemsViewHolder.getTvDes().setText(mList.get(pos).getDesc());
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_album_detail, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new TripDetailsViewHolder(view);
            }
        };
    }




    /**
     * setup recycler view add items and set adapter
     */
    public void setUpAlbumRecyclerView(RecyclerView rvItems) {
        addAlbumListeners(rvItems);

        albumAdapter = new CustomRecyclerItemsAdapter(layoutInflater, customisedAlbumBindViewHolderListener,
                getAlbumViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, albumAdapter, updateAlbumRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }

    // method for adding all listeners
    public void addAlbumListeners(RecyclerView rvItems) {

        /*getViewTypeListener = position -> {
            if (mList.get(position).isCategory()){
                return AppUtils.VIEW_TYPE_CAT;
            } else {
                return AppUtils.VIEW_TYPE_ITEM;
            }
        };*/

        updateAlbumRecyclerDataListener = () -> {
                getAllAlbumItems().observe(lifecycleOwner, itemModels -> {
//                    mAlbumList.clear();
//                    mAlbumList.addAll(itemModels);
                    albumAdapter.updateUserList(mAlbumList);
                });
        };

        customisedAlbumBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAdapterPosition();
            TripDetailsViewHolder itemsViewHolder = ((TripDetailsViewHolder) viewHolder);
            itemsViewHolder.getTvCat().setText(mAlbumList.get(pos).getName());
            Glide
                    .with( context )
                    .load(mAlbumList.get(pos).getDocumentUrl())
                    .into(itemsViewHolder.getIvAlbum());
            itemsViewHolder.getTvCat().setOnClickListener(view -> {
                if (onAlbumItemClickedListener != null){
                    onAlbumItemClickedListener.OnItemClicked(pos);
                }
            });
//            itemsViewHolder.getTvDes().setText(mList.get(pos).getDesc());
        };

        getAlbumViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_album_detail, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new TripDetailsViewHolder(view);
            }
        };
    }

    public DocumentPojo getDocumentsWithPos(int pos){
        return mDocsList.get(pos);
    }

    public DocumentPojo getAlbumWithPos(int pos){
        return mAlbumList.get(pos);
    }
}