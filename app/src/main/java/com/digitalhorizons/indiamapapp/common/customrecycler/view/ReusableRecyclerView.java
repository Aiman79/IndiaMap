package com.digitalhorizons.indiamapapp.common.customrecycler.view;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;

public class ReusableRecyclerView {
    private Context context;
    private RecyclerView recyclerView;
    private CustomRecyclerItemsAdapter adapter;
    private UpdateRecyclerDataListener updateRecyclerDataListener;

    public ReusableRecyclerView(Context context, RecyclerView recyclerView, CustomRecyclerItemsAdapter adapter, UpdateRecyclerDataListener updateRecyclerDataListener) {
        this.context = context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.updateRecyclerDataListener = updateRecyclerDataListener;
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(boolean isList, int col) {
        if (isList) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, col));
        }
        recyclerView.setAdapter(adapter);
        try {
            updateRecyclerDataListener.onUpdateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        recyclerBottomSheetViewModel.getAllItems().observe(this, itemModels -> adapter.updateUserList(itemModels));
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpHorizontalRecyclerView(boolean isHorizontal) {
        if (isHorizontal) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
        recyclerView.setAdapter(adapter);
        try {
            updateRecyclerDataListener.onUpdateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        recyclerBottomSheetViewModel.getAllItems().observe(this, itemModels -> adapter.updateUserList(itemModels));
    }

    /**
     * setup recycler view add items and set adapter
     */
  /*  public void setUpFlexBoxRecyclerView() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        try {
            updateRecyclerDataListener.onUpdateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        recyclerBottomSheetViewModel.getAllItems().observe(this, itemModels -> adapter.updateUserList(itemModels));
    }*/

}
