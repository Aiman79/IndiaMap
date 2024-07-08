package com.digitalhorizons.indiamapapp.marketplace.itemdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    Context context;
    List<PropertyPojo> propertyPojoList;

    public PropertyAdapter(Context context, List<PropertyPojo> propertyPojoList) {
        this.context = context;
        this.propertyPojoList = propertyPojoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_property_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_property_name.setText(propertyPojoList.get(position).getPropertyName());
    }
    @Override
    public int getItemCount() {
        return propertyPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tv_property_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_property_name = itemView.findViewById(R.id.tv_property_name);
        }
    }
}
