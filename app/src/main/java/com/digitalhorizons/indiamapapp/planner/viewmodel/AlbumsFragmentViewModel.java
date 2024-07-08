package com.digitalhorizons.indiamapapp.planner.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.adapter.CustomRecyclerItemsAdapter;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.CustomisedBindViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.GetViewHolderListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.UpdateRecyclerDataListener;
import com.digitalhorizons.indiamapapp.common.customrecycler.view.ReusableRecyclerView;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.digitalhorizons.indiamapapp.planner.viewholder.DocumentsViewHolder;

import java.util.ArrayList;

public class AlbumsFragmentViewModel extends AndroidViewModel {
    private Application context;
//    private AppCompatActivity mActivity;

    //listerners
    private CustomisedBindViewHolderListener customisedBindViewHolderListener;
    private GetViewHolderListener getViewHolderListener;
    private UpdateRecyclerDataListener updateRecyclerDataListener;
    private OnItemClickedListener onItemClickedListener;

    //recyclerview
    private CustomRecyclerItemsAdapter adapter;
    private ArrayList<DocumentPojo> mList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private LifecycleOwner lifecycleOwner;

    public AlbumsFragmentViewModel(@NonNull Application context) {
        super(context);
        this.context = context;
    }


    public void setDataFromLocal(ArrayList<DocumentPojo> documentPojos) {
        mList = documentPojos;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener= onItemClickedListener;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
    }

    public void updateData(ArrayList<DocumentPojo> list){
        adapter.updateUserList(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * setup recycler view add items and set adapter
     */
    public void setUpRecyclerView(RecyclerView rvItems, ArrayList<DocumentPojo> documentPojos) {
        mList = documentPojos;
        addListeners(rvItems);

        adapter = new CustomRecyclerItemsAdapter(layoutInflater, customisedBindViewHolderListener,
                getViewHolderListener);

        ReusableRecyclerView recyclerView = new ReusableRecyclerView(context, rvItems, adapter, updateRecyclerDataListener);

        recyclerView.setUpRecyclerView(false, 2);
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
                adapter.updateUserList(mList);
        };

        customisedBindViewHolderListener = (viewHolder, viewType) -> {
            int pos = viewHolder.getAdapterPosition();
            DocumentsViewHolder itemsViewHolder = ((DocumentsViewHolder) viewHolder);
            Glide
                    .with( context )
                    .load(mList.get(pos).getDocumentUrl())
                    .into(itemsViewHolder.getIvDocument());
//            itemsViewHolder.getIvDocument().setImageBitmap(getVideoThumbnail(mList.get(pos).getDocumentUrl().getPath()));
            itemsViewHolder.getTvDocumentName().setText(mList.get(pos).getName());

            itemsViewHolder.getIvClose().setOnClickListener(view -> {
                if (onItemClickedListener != null){
                    onItemClickedListener.OnItemClicked(pos);
                }
            });
        };

        getViewHolderListener = new GetViewHolderListener() {
            @Override
            public View getView(int viewType, LayoutInflater layoutInflater, ViewGroup parent) {
                View view;
                view = layoutInflater.inflate(R.layout.raw_item_albums, parent, false);
                return view;
            }

            @Override
            public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
                return new DocumentsViewHolder(view);
            }
        };
    }

    public Bitmap getVideoThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
    }

    public DocumentPojo getDocumentOnPos(int pos){
        return mList.get(pos);
    }
}