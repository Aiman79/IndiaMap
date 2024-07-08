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

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.marketplace.main.model.ColorModel;
import com.digitalhorizons.indiamapapp.marketplace.main.repo.FilterRepository;
import com.digitalhorizons.indiamapapp.marketplace.main.viewholder.ColorsItemsViewHolder;

import java.util.ArrayList;

public class FilterViewModel extends AndroidViewModel {
    private Application context;
    //    private AppCompatActivity mActivity;
    private FilterRepository filterRepository;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;
//    private GetViewTypeListener getViewTypeListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<ColorModel> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;
    private int prePos = -1;

    public FilterViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        filterRepository = new FilterRepository(context);
    }


    public ArrayList<ColorModel> getDataFromLocal() {
        return filterRepository.getData();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public MutableLiveData<ArrayList<ColorModel>> getAllItems() {
        return filterRepository.getMutableLiveData();
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
            getAllItems().observe(lifecycleOwner, itemModels -> {
                mList.clear();
                mList.addAll(itemModels);
                adapter.updateUserList(mList);
            });
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getBindingAdapterPosition();
            ColorsItemsViewHolder itemsViewHolder = ((ColorsItemsViewHolder) viewHolder);
            itemsViewHolder.getIvColor().setImageResource(mList.get(pos).getDrawable());

            boolean isChecked = mList.get(pos).isChecked();
            if (isChecked){
                itemsViewHolder.getIvDone().setVisibility(View.VISIBLE);
            } else {
                itemsViewHolder.getIvDone().setVisibility(View.GONE);
            }

            itemsViewHolder.getIvColor().setOnClickListener(view -> {
                boolean isCheck = mList.get(pos).isChecked();
                mList.get(pos).setChecked(!isCheck);
                if (onItemClickedListener != null) {
                    onItemClickedListener.OnItemClicked(pos);
                }
                adapter.notifyItemChanged(pos);
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_colors, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new ColorsItemsViewHolder(view);
            }
        };
    }

    public ColorModel getMarketPlaceItemWithPos(int pos) {
        return mList.get(pos);
    }
}