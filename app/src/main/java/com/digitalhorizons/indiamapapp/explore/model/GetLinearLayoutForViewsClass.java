package com.digitalhorizons.indiamapapp.explore.model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.explore.repo.ItemsRepository;
import com.digitalhorizons.indiamapapp.explore.viewholder.ItemsViewHolder;

import java.util.ArrayList;

public class GetLinearLayoutForViewsClass {
    private Context context;
    private ArrayList<ItemListModel> itemListModel;
    private boolean isShowCard = false;

    public GetLinearLayoutForViewsClass(Context context) {
        this.context = context;
        ItemsRepository itemsRepository = new ItemsRepository(context);
        itemListModel = itemsRepository.getData();
    }

    public LinearLayout getItemsView(boolean isShowCard) {
        LinearLayout llMain = new LinearLayout(context);
        LinearLayout.LayoutParams paramsM = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llMain.setOrientation(LinearLayout.VERTICAL);
        llMain.setLayoutParams(paramsM);

        this.isShowCard = isShowCard;
        for (int i = 0; i < itemListModel.size(); i++) {
            AppCompatTextView tvTitle = new AppCompatTextView(context);

            //set title
            tvTitle.setText(itemListModel.get(i).getTitle());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            params.setMarginStart(10);
            params.setMarginEnd(10);
            tvTitle.setLayoutParams(params);
//            llItems.addView(tvTitle);

            // recyclerview
            RecyclerView rvItems = new RecyclerView(context);
            RecyclerView.LayoutParams paramsRe = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
//            paramsRe.setMargins(10, 10, 10, 10);
//            paramsRe.setMarginStart(10);
//            paramsRe.setMarginEnd(10);
            rvItems.setNestedScrollingEnabled(false);
            rvItems.setLayoutParams(paramsRe);

            CardView cardView = new CardView(context);
            LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            cParams.setMargins(10, 10, 10, 10);
            cParams.setMarginStart(10);
            cParams.setMarginEnd(10);
            cardView.setLayoutParams(cParams);
            if (isShowCard) {
                cardView.setElevation(4);
            } else {
                cardView.setElevation(0);
            }

            LinearLayout llChild = new LinearLayout(context);
            llChild.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            llChild.setLayoutParams(llParams);

            llChild.addView(tvTitle);
            llChild.addView(rvItems);

            cardView.addView(llChild);

            llMain.addView(cardView);
            setUpRecyclerView(rvItems, i);
        }

        return llMain;
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems, int posRv) {
        //listeners
        CustomisedBindViewHolderListener customisedBindViewHolderListener;
        GetViewHolderListener getViewHolderListener;
        UpdateRecyclerDataListener updateRecyclerDataListener;
        //recyclerview
        CustomRecyclerItemsAdapter adapter;
     ArrayList<ItemsModel> mList = itemListModel.get(posRv).getItemList();


        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAbsoluteAdapterPosition();
            ItemsViewHolder itemsViewHolder = ((ItemsViewHolder) viewHolder);
            itemsViewHolder.getIvThumbnail().setImageResource(mList.get(pos).getThumbnailDrawable());
            if (mList.get(pos).isTextAvailable()) {
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

        adapter = new CustomRecyclerItemsAdapter(LayoutInflater.from(context), customisedBindViewHolderListener,
                getViewHolderListener);

        updateRecyclerDataListener = () -> {
            adapter.updateUserList(mList);
        };

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapter, updateRecyclerDataListener);

        recyclerView.setUpHorizontalRecyclerView(true);
    }
}
