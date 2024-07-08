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

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.planner.repo.TripsRepository;
import com.digitalhorizons.indiamapapp.planner.viewholder.TripsViewHolder;

import java.util.ArrayList;

public class PlannerFragmentViewModel extends AndroidViewModel {
    private Application context;
//    private AppCompatActivity mActivity;
    private TripsRepository tripsRepository;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;
//    private GetViewTypeListener getViewTypeListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<TripModel> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    public PlannerFragmentViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
        tripsRepository = new TripsRepository(context);
    }


    public ArrayList<TripModel> getDataFromLocal() {
        return tripsRepository.getData();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
    }


    public MutableLiveData<ArrayList<TripModel>> getAllItems(){
        return tripsRepository.getMutableLiveData();
    }

    public void addItem(TripModel tripModel){
        tripsRepository.setData(tripModel);
        mList.add(tripModel);
        adapter.updateUserList(mList);
        adapter.notifyDataSetChanged();
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
                getAllItems().observe(lifecycleOwner, itemModels -> {
                    mList.clear();
                    mList.addAll(itemModels);
                    adapter.updateUserList(mList);
                });
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAdapterPosition();
            TripsViewHolder itemsViewHolder = ((TripsViewHolder) viewHolder);
            itemsViewHolder.getTvTitle().setText(mList.get(pos).getTitle());
            itemsViewHolder.getTvDes().setText(mList.get(pos).getDesc());
            itemsViewHolder.getTvDates().setText(mList.get(pos).getStartDate()
                            .concat(" ")
                    .concat(context.getString(R.string.to))
                            .concat(" ")
                    .concat(mList.get(pos).getEndDate()));

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
                view = layoutInflater.inflate(R.layout.raw_trip, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new TripsViewHolder(view);
            }
        };
    }

    public TripModel getTripsWithPos(int pos){
        return mList.get(pos);
    }
}