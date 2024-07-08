package com.digitalhorizons.indiamapapp.marketplace.main.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.marketplace.main.viewmodel.AddOrderItemSaleViewModel;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ClsAddItemSaleActivity extends AppCompatActivity {
    //toolbar
    private AppCompatTextView tvToolbarTitle;
    private AppCompatImageView ivBackToolbar;
    private Toolbar mToolBar;

    //view
    private RecyclerView rvProperty;
    private AddOrderItemSaleViewModel addOrderItemSaleViewModel;
    private Spinner spType;
    private CheckBox cbMonthly, cbLease;
    private TextInputLayout tilMonths;
    private AppCompatImageView ivAddPhoto;
    private RecyclerView rvPhotos;

    //add photo
    private ArrayList<DocumentPojo> documentPojoList = new ArrayList<>();
    private DocumentPojo documentPojo= new DocumentPojo();
    private String base64="";
    private Uri documentUri = null;
    private Bitmap photo;
    private boolean isImage = true;

    //spinner
    private  String[] type = {"Select type","Vehicle", "House","Room","Grocery","Electronics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_add_item_sale);

        registerViews();
        setUpToolBar();
        init();
        addListeners();
    }

    public void registerViews() {
        cbLease = findViewById(R.id.cb_lease);
        cbMonthly = findViewById(R.id.cb_monthly);
        tilMonths = findViewById(R.id.til_months);
        rvProperty = findViewById(R.id.rv_properties);
        spType = findViewById(R.id.spn_type);
        ivAddPhoto = findViewById(R.id.iv_add);
        rvPhotos = findViewById(R.id.rv_photo);

        addOrderItemSaleViewModel = new ViewModelProvider(this).get(AddOrderItemSaleViewModel.class);
    }

    public void setUpToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        ivBackToolbar = findViewById(R.id.iv_toolbar_back);

        tvToolbarTitle.setText(getString(R.string.app_name));

        ivBackToolbar.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    public void init() {
        addOrderItemSaleViewModel.getDataFromLocal();
        setUpTypeSpinner();
        setUpRecyclerView();
    }

    public void addListeners(){
        cbMonthly.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                tilMonths.setVisibility(View.VISIBLE);
            } else {
                tilMonths.setVisibility(View.GONE);
            }
        });

        ivAddPhoto.setOnClickListener(view -> {
            ShowBottomSheet();
        });
    }

    public void setUpRecyclerView(){
        addOrderItemSaleViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {

            }
        });

        addOrderItemSaleViewModel.setUpPropertyRecyclerView(rvProperty, this);

        addOrderItemSaleViewModel.setOnItemClickedListenerPhotos(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                documentPojoList.remove(pos);
                addOrderItemSaleViewModel.updatePhotoData(documentPojoList);
            }
        });

        addOrderItemSaleViewModel.setUpPhotosRecyclerView(rvPhotos, this, documentPojoList);
    }

    public void setUpTypeSpinner() {
        // spinner country
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);
    }

    void ShowBottomSheet(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.document_bottom_sheet_layout);

        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = dialog.findViewById(R.id.ll_gallery);
        LinearLayout llDocuments = dialog.findViewById(R.id.ll_document);
        LinearLayout llVideo = dialog.findViewById(R.id.ll_video);

        llVideo.setVisibility(View.VISIBLE);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    isImage = true;
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    launchCameraActivity.launch(cameraIntent);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        llGallery.setOnClickListener(view1 -> {
            Intent documentIntent = new Intent();
            documentIntent.setType("image/*");
            documentIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            documentIntent.setAction(Intent.ACTION_GET_CONTENT);
//            documentIntent.setAction(Intent.ACTION_VIEW);
//            documentIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            launchGalleryActivity.launch(documentIntent);
            dialog.dismiss();
        });

        llVideo.setOnClickListener(view1 -> {
            isImage = false;
            Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            launchCameraActivity.launch(cameraIntent);
            dialog.dismiss();
        });

        llDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent documentIntent = new Intent();
                    documentIntent.setType("*/*");
                    documentIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    documentIntent.setAction(Intent.ACTION_GET_CONTENT);
                    launchGalleryActivity.launch(documentIntent);
                    dialog.dismiss();
//                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    ActivityResultLauncher<Intent> launchGalleryActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    setUpImageFromURI(data);
                }
//                Toast.makeText(getContext(), "Picked "+documentUri, Toast.LENGTH_SHORT).show();
            });

    public void setUpImageURIForCamera(Uri cameraURI){
        DocumentPojo documentPojo = new DocumentPojo();
        documentPojo.setDocumentUrl(cameraURI);
        File f = new File(getRealPathFromURI(cameraURI));
        documentPojo.setName(f.getName());
        String type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1);
        documentPojo.setType(type);
        documentPojoList.add(documentPojo);
        addOrderItemSaleViewModel.updatePhotoData(documentPojoList);
    }

    public void setUpImageFromURI(Intent data){
        if (data != null) {
            if (data.getData() != null) {
                documentUri = data.getData();
                DocumentPojo documentPojo = new DocumentPojo();
                documentPojo.setDocumentUrl(documentUri);
                File f = new File(getRealPathFromURI(documentUri));
                documentPojo.setName(f.getName());
                String type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1);
                documentPojo.setType(type);
                documentPojoList.add(documentPojo);
                addOrderItemSaleViewModel.updatePhotoData(documentPojoList);
            } else {
                if (data.getClipData() != null){
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        DocumentPojo documentPojo = new DocumentPojo();
                        documentPojo.setDocumentUrl(uri);
                        File f = new File(getRealPathFromURI(uri));
                        documentPojo.setName(f.getName());
                        String type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1);
                        documentPojo.setType(type);
                        documentPojoList.add(documentPojo);
//                                    mArrayUri.add(uri);
                    }
                    addOrderItemSaleViewModel.updatePhotoData(documentPojoList);
                }
            }
        }
    }


    public String getRealPathFromURI(Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver( ).query(
                uri, null, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    /* This method is for capturing image from camera
       created by Aiman Pathan 22/02/2023 */
    ActivityResultLauncher<Intent> launchCameraActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Intent data = result.getData();
                        if (isImage){
                            photo = (Bitmap) data.getExtras().get("data");
                            documentUri = getImageUri(this, photo);
                            setUpImageURIForCamera(documentUri);
                        } else {
                            if (data != null){
                                setUpImageFromURI(data);
                            }
                        }

                    }
                }});


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}