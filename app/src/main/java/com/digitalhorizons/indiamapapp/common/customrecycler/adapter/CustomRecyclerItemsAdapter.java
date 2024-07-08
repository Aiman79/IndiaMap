package com.digitalhorizons.indiamapapp.common.customrecycler.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewTypeListener;

import java.util.ArrayList;
import java.util.List;


public class CustomRecyclerItemsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private ArrayList<T> mList = new ArrayList<>();
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private GetViewTypeListener getViewTypeListener;

    public CustomRecyclerItemsAdapter(LayoutInflater layoutInflater,
                                      CustomisedBindViewHolderListener customisedBindViewHolderListener,
                                      GetViewHolderListener getViewHolderListener) {
        this.layoutInflater = layoutInflater;
        this.customisedBindViewHolderListener = customisedBindViewHolderListener;
        this.getViewHolderListener = getViewHolderListener;
    }

    public void setGetViewTypeListener(GetViewTypeListener getViewTypeListener){
        this.getViewTypeListener = getViewTypeListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = getViewHolderListener.getView(viewType, layoutInflater, parent);

//            view = layoutInflater.inflate(layout, parent, false);

        return getViewHolderListener.getViewHolder(view, viewType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      /*  holder.tvItem.setText(mList.get(position).getName());

        holder.ivCheck.setOnClickListener(view -> {
            boolean isChecked = !mList.get(holder.getAdapterPosition()).isChecked();
            changeChecksImage(holder, isChecked);
            onContactsSelectUnselectListener.onSelectItem(isChecked,
                    mList.get(holder.getAdapterPosition()));
            mList.get(holder.getAdapterPosition()).setChecked(isChecked);
        });*/
        int viewType = getItemViewType(position);
        bindItem(holder, viewType);
    }

    public void bindItem(RecyclerView.ViewHolder holder, int viewType){
        customisedBindViewHolderListener.onBindViewHolder(holder, viewType);
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (getViewTypeListener != null){
            return getViewTypeListener.getItemViewType(position);
        }
        return super.getItemViewType(position);
    }


    public void updateUserList(final List<T> model) {
        this.mList.clear();
        this.mList.addAll(model);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
