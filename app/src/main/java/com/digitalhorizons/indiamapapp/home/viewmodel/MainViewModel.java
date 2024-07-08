package com.digitalhorizons.indiamapapp.home.viewmodel;

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
import com.digitalhorizons.indiamapapp.home.model.DrawerMenuModel;
import com.digitalhorizons.indiamapapp.home.repo.DrawerMenuItemsREpository;
import com.digitalhorizons.indiamapapp.home.viewholder.DrawerMenuItemsViewHolder;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private DrawerMenuItemsREpository drawerMenuItemsREpository;
    private Application context;
//    private AppCompatActivity mActivity;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<DrawerMenuModel> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    public MainViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        drawerMenuItemsREpository = new DrawerMenuItemsREpository(context);
    }

    public MutableLiveData<ArrayList<DrawerMenuModel>> getAllItems(){
        return drawerMenuItemsREpository.getMutableLiveData();
    }

    public void getDataFromLocal() {
        drawerMenuItemsREpository.getData();
    }


    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems) {
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
            getAllItems().observe(lifecycleOwner, strings -> {
                mList.clear();
                mList.addAll(strings);
                adapter.updateUserList(mList);
            });
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getBindingAdapterPosition();
            DrawerMenuItemsViewHolder itemsViewHolder = ((DrawerMenuItemsViewHolder) viewHolder);
            itemsViewHolder.getTvMenu().setText(mList.get(pos).getMenu());

            String thumbnail = mList.get(pos).getThumbnail();
            if (thumbnail != null && !thumbnail.isEmpty()){
                itemsViewHolder.getIvMenu().setVisibility(View.VISIBLE);
                Glide.with(itemsViewHolder.getIvMenu()).load(thumbnail).into(itemsViewHolder.getIvMenu());
            } else {
                itemsViewHolder.getIvMenu().setVisibility(View.GONE);
            }

            itemsViewHolder.itemView.setOnClickListener(view -> {
                if (onItemClickedListener != null){
                    onItemClickedListener.OnItemClicked(pos);
                }
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.item_drawer, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new DrawerMenuItemsViewHolder(view);
            }
        };
    }

    public DrawerMenuModel getItemWithPos(int pos){
        return mList.get(pos);
    }
}