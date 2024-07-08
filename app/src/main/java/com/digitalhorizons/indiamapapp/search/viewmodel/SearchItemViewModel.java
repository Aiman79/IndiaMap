package com.digitalhorizons.indiamapapp.search.viewmodel;

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
import com.digitalhorizons.indiamapapp.search.model.SearchItemsModel;
import com.digitalhorizons.indiamapapp.search.repo.SearchItemsRepository;
import com.digitalhorizons.indiamapapp.search.viewholder.SearchViewHolder;

import java.util.ArrayList;

public class SearchItemViewModel extends AndroidViewModel {
    private Application context;
//    private AppCompatActivity mActivity;
    private SearchItemsRepository searchItemsRepository;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;
//    private GetViewTypeListener getViewTypeListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<SearchItemsModel> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    public SearchItemViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        searchItemsRepository = new SearchItemsRepository(context);
    }

    public void clearSearch(){
        searchItemsRepository.clearAllData();
        mList.clear();
        adapter.updateUserList(mList);
    }

    public ArrayList<SearchItemsModel> getDataFromLocal() {
        return searchItemsRepository.getData();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }

    public MutableLiveData<ArrayList<SearchItemsModel>> getAllItems(){
        return searchItemsRepository.getMutableLiveData();
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems, LayoutInflater layoutInflater, LifecycleOwner lifecycleOwner) {
        this.layoutInflater = layoutInflater;
        this.lifecycleOwner = lifecycleOwner;
        addListeners(rvItems);

        adapter = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapter, updateRecyclerDataListener);

        recyclerView.setUpRecyclerView(true, 0);
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
                getAllItems().observe(lifecycleOwner, itemModels -> {
                    mList.clear();
                    mList.addAll(itemModels);
                    adapter.updateUserList(mList);
                });
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getBindingAdapterPosition();
            SearchViewHolder itemsViewHolder = ((SearchViewHolder) viewHolder);
            itemsViewHolder.getTvTitle().setText(mList.get(pos).getName());
            itemsViewHolder.getTvDes().setText(mList.get(pos).getDesc());
           try {
               Glide.with( itemsViewHolder.getIvThumbnail())
                       .load(mList.get(pos).getThumbnail())
                       .into(itemsViewHolder.getIvThumbnail());
           } catch (Exception e){
               itemsViewHolder.getIvThumbnail().setImageResource(R.drawable.ic_bg_big);
           }
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_search_items, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new SearchViewHolder(view);
            }
        };
    }
}