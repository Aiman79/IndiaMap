package com.digitalhorizons.indiamapapp.marketplace.main.viewmodel;

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
import com.digitalhorizons.indiamapapp.marketplace.main.model.PropertyModel;
import com.digitalhorizons.indiamapapp.marketplace.main.repo.PropertyItemsRepository;
import com.digitalhorizons.indiamapapp.marketplace.main.viewholder.PropertyItemsViewHolder;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.digitalhorizons.indiamapapp.planner.viewholder.DocumentsViewHolder;

import java.util.ArrayList;

public class AddOrderItemSaleViewModel extends AndroidViewModel {
    private PropertyItemsRepository propertyItemsRepository;
    private Application context;

    //property
    private ArrayList<PropertyModel> mListProperty = new ArrayList<>();
    private OnItemClickedListener onItemClickedListenerProperty;
    private CustomRecyclerItemsAdapter adapterProperty;

    //photos
    private ArrayList<DocumentPojo> mListPhotos = new ArrayList<>();
    private OnItemClickedListener onItemClickedListenerPhotos;
    private CustomRecyclerItemsAdapter adapterPhotos;

    public AddOrderItemSaleViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        propertyItemsRepository = new PropertyItemsRepository(context);
    }

    public MutableLiveData<ArrayList<PropertyModel>> getAllItems() {
        return propertyItemsRepository.getMutableLiveData();
    }

    public void getDataFromLocal() {
        propertyItemsRepository.getData();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListenerProperty = onItemClickedListener;
    }

    public void setOnItemClickedListenerPhotos(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListenerPhotos = onItemClickedListener;
    }

    public void updatePhotoData(ArrayList<DocumentPojo> list){
        adapterPhotos.updateUserList(list);
        adapterPhotos.notifyDataSetChanged();
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpPropertyRecyclerView(RecyclerView rvItems, LifecycleOwner lifecycleOwner) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        this.lifecycleOwner = lifecycleOwner;
//        addListeners(rvItems);

        //listeners
        CustomisedBindViewHolderListener customisedBindViewHolderListener;
        GetViewHolderListener getViewHolderListener;
        UpdateRecyclerDataListener updateRecyclerDataListener;

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getBindingAdapterPosition();
            PropertyItemsViewHolder itemsViewHolder = ((PropertyItemsViewHolder) viewHolder);
            itemsViewHolder.getTvName().setText(mListProperty.get(pos).getName());
            if (mListProperty.get(pos).isChecked()) {
                itemsViewHolder.getIvCheck().setImageResource(R.drawable.ic_vector_checkbox_checked);
            } else {
                itemsViewHolder.getIvCheck().setImageResource(R.drawable.ic_vector_checkbox_unchecked);
            }
            itemsViewHolder.itemView.setOnClickListener(view -> {
                boolean isChecked = mListProperty.get(pos).isChecked();
                mListProperty.get(pos).setChecked(!isChecked);
                if (onItemClickedListenerProperty != null) {
                    onItemClickedListenerProperty.OnItemClicked(pos);
                }
                adapterProperty.notifyItemChanged(pos);
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_property_item, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new PropertyItemsViewHolder(view);
            }
        };

        adapterProperty = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        updateRecyclerDataListener = () -> {
            getAllItems().observe(lifecycleOwner, strings -> {
                mListProperty.clear();
                mListProperty.addAll(strings);
                adapterProperty.updateUserList(mListProperty);
            });
        };

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapterProperty, updateRecyclerDataListener);

        recyclerView.setUpRecyclerView(true, 0);
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpPhotosRecyclerView(RecyclerView rvItems, LifecycleOwner lifecycleOwner, ArrayList<DocumentPojo> list) {
        mListPhotos = list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        this.lifecycleOwner = lifecycleOwner;
//        addListeners(rvItems);

        //listeners
        CustomisedBindViewHolderListener customisedBindViewHolderListener;
        GetViewHolderListener getViewHolderListener;
        UpdateRecyclerDataListener updateRecyclerDataListener;

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getBindingAdapterPosition();
            DocumentsViewHolder itemsViewHolder = ((DocumentsViewHolder) viewHolder);
            Glide
                    .with( context )
                    .load(mListPhotos.get(pos).getDocumentUrl())
                    .into(itemsViewHolder.getIvDocument());
//            itemsViewHolder.getIvDocument().setImageBitmap(getVideoThumbnail(mList.get(pos).getDocumentUrl().getPath()));
            itemsViewHolder.getTvDocumentName().setText(mListPhotos.get(pos).getName());

            itemsViewHolder.getIvClose().setOnClickListener(view -> {
                if (onItemClickedListenerPhotos != null){
                    onItemClickedListenerPhotos.OnItemClicked(pos);
                }
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_item_sale_photos, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new DocumentsViewHolder(view);
            }
        };

        adapterPhotos = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        updateRecyclerDataListener = () -> {
            getAllItems().observe(lifecycleOwner, strings -> {
                adapterPhotos.updateUserList(mListPhotos);
            });
        };

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapterPhotos, updateRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }

    public PropertyModel getPropertyItemWithPos(int pos) {
        return mListProperty.get(pos);
    }

    public DocumentPojo getPhotosItemWithPos(int pos) {
        return mListPhotos.get(pos);
    }
}