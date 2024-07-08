package com.digitalhorizons.indiamapapp.explore.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.explore.model.CategoryModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemListModel;
import com.digitalhorizons.indiamapapp.explore.model.ItemsModel;
import com.digitalhorizons.indiamapapp.explore.repo.ItemsRepository;
import com.digitalhorizons.indiamapapp.explore.viewholder.CategoryViewHolder;
import com.digitalhorizons.indiamapapp.explore.viewholder.ItemsViewHolder;

import java.util.ArrayList;

public class ExploreViewModel extends AndroidViewModel {
    private ItemsRepository itemsRepository;
    private Application context;
//    private AppCompatActivity mActivity;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener, catCustomisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener, catGetViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener, catUpdateRecyclerDataListener;
//    private GetViewTypeListener getViewTypeListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter, catAdapter;
//    private ArrayList<ItemsModel> mList = new ArrayList<>();
    private ArrayList<CategoryModel> mCategoryList = new ArrayList<>();
    private ArrayList<ItemListModel> itemListModel;
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;
    private int posRv = 0;

    public ExploreViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        itemsRepository = new ItemsRepository(context);
    }


    public ArrayList<ItemListModel> getDataFromLocal() {
        itemListModel = itemsRepository.getData();
        getCategoryDataFromLocal();
        return itemListModel;
    }
    public void getCategoryDataFromLocal() {
        itemsRepository.getCategoryData();
    }


    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems, int pos) {
        posRv = pos;
        addListeners(rvItems, itemListModel.get(pos).getItemList());

        adapter = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapter, updateRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }

    // method for adding all listeners
    public void addListeners(RecyclerView rvItems, ArrayList<ItemsModel> mList) {

        /*getViewTypeListener = position -> {
            if (mList.get(position).isCategory()){
                return AppUtils.VIEW_TYPE_CAT;
            } else {
                return AppUtils.VIEW_TYPE_ITEM;
            }
        };*/

        updateRecyclerDataListener = () -> {
                adapter.updateUserList(mList);
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAbsoluteAdapterPosition();
            ItemsViewHolder itemsViewHolder = ((ItemsViewHolder) viewHolder);
            itemsViewHolder.getIvThumbnail().setImageResource(mList.get(pos).getThumbnailDrawable());
            if(mList.get(pos).isTextAvailable()){
                itemsViewHolder.getTvText().setText(mList.get(pos).getText());
                itemsViewHolder.getTvText().setMaxLines(mList.get(pos).getTextMaxLines());
                itemsViewHolder.getTvText().setEllipsize(TextUtils.TruncateAt.END);
            }
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_items, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new ItemsViewHolder(view);
            }
        };
    }

    public MutableLiveData<ArrayList<CategoryModel>> getAllItems(){
        return itemsRepository.getMutableLiveData();
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpCategoryRecyclerView(RecyclerView rvItems) {
        addCatListeners(rvItems);

        catAdapter = new CustomRecyclerItemsAdapter(layoutInflater, catCustomisedBindViewHolderListener,
                catGetViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, catAdapter,
                catUpdateRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }

    // method for adding all listeners
    public void addCatListeners(RecyclerView rvItems) {

        /*getViewTypeListener = position -> {
            if (mList.get(position).isCategory()){
                return AppUtils.VIEW_TYPE_CAT;
            } else {
                return AppUtils.VIEW_TYPE_ITEM;
            }
        };*/

        catUpdateRecyclerDataListener = () -> {
            getAllItems().observe(lifecycleOwner, categoryModels -> {
                mCategoryList.clear();
                mCategoryList.addAll(categoryModels);
                catAdapter.updateUserList(mCategoryList);
            });
        };

        catCustomisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAdapterPosition();
            CategoryViewHolder itemsViewHolder = ((CategoryViewHolder) viewHolder);
            itemsViewHolder.getTvCat().setText(mCategoryList.get(pos).getName());
        };

        catGetViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_category, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new CategoryViewHolder(view);
            }
        };
    }
}