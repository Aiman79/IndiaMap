package com.digitalhorizons.indiamapapp.marketplace.orders.viewmodel;

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
import com.digitalhorizons.indiamapapp.marketplace.orders.model.OrdersModel;
import com.digitalhorizons.indiamapapp.marketplace.orders.repo.OrdersItemsRepository;
import com.digitalhorizons.indiamapapp.marketplace.orders.viewholder.OrdersItemsViewHolder;

import java.util.ArrayList;

public class OrdersViewModel extends AndroidViewModel {
    private Application context;
//    private AppCompatActivity mActivity;
    private OrdersItemsRepository ordersItemsRepository;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;
//    private GetViewTypeListener getViewTypeListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<OrdersModel> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    public OrdersViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        ordersItemsRepository = new OrdersItemsRepository(context);
    }


    public ArrayList<OrdersModel> getDataFromLocal() {
        return ordersItemsRepository.getData();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }

    public MutableLiveData<ArrayList<OrdersModel>> getAllItems(){
        return ordersItemsRepository.getMutableLiveData();
    }

   /* public void addItem(TripModel tripModel){
        marketPlaceItemsRepository.setData(tripModel);
        mList.add(tripModel);
        adapter.updateUserList(mList);
        adapter.notifyDataSetChanged();
    }*/

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems, LifecycleOwner lifecycleOwner) {
        this.layoutInflater = LayoutInflater.from(context);
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
            OrdersItemsViewHolder itemsViewHolder = ((OrdersItemsViewHolder) viewHolder);
            itemsViewHolder.getTvName().setText(mList.get(pos).getName());
            itemsViewHolder.getTvStatus().setText(mList.get(pos).getStatus());
            itemsViewHolder.getTvPrice().setText(context.getString(R.string.dollar).concat(mList.get(pos).getPrice()));

            String url = mList.get(pos).getThumbnail();
            if (url != null && !url.isEmpty()){
                Glide.with(itemsViewHolder.getIvThumbnail()).load(url).into(itemsViewHolder.getIvThumbnail());
            } else {
                itemsViewHolder.getIvThumbnail().setImageResource(R.drawable.ic_launcher_foreground);
            }

            itemsViewHolder.getCdMain().setOnClickListener(view -> {
                if (onItemClickedListener != null){
                    onItemClickedListener.OnItemClicked(pos);
                }
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_item_orders, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new OrdersItemsViewHolder(view);
            }
        };
    }

    public OrdersModel getMarketPlaceItemWithPos(int pos){
        return mList.get(pos);
    }
}