package com.digitalhorizons.indiamapapp.planner.view;

import android.annotation.SuppressLint;
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
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.planner.viewmodel.AlbumsFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class AlbumsFragment extends Fragment {
    private View view;
//    private TextView tv_upload_document;
//    private ImageView iv_add;  //, iv_camera, iv_gallery;
    private boolean isImage = true;
    private RecyclerView rv_documents;
    private ArrayList<DocumentPojo> documentPojoList = new ArrayList<>();
    private DocumentPojo documentPojo= new DocumentPojo();
    private String base64="";
    private Uri documentUri = null;
    private  Bitmap photo;
    private AlbumsFragmentViewModel albumsFragmentViewModel;
    private FloatingActionButton fabAdd;

    public AlbumsFragment() {
        // Required empty public constructor
    }
    public static AlbumsFragment newInstance() {
        AlbumsFragment fragment = new AlbumsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album, container, false);
        return  view;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAll();
        setRecyclerView();
    }

    /* This method is for setting Recycler view
           created by Aiman Pathan 22/02/2023 */
    void setRecyclerView(){
        albumsFragmentViewModel.setUpRecyclerView(rv_documents, documentPojoList);
    }

    /* This method is for opening bottomsheet
        created by Aiman Pathan 22/02/2023 */
    void ShowBottomSheet(){
        Dialog dialog = new Dialog(getContext());
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


  /*  void ShowBottomSheetForVideo(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.document_bottom_sheet_layout);

        iv_camera = dialog.findViewById(R.id.iv_camera);
        iv_gallery = dialog.findViewById(R.id.iv_gallery);

        iv_camera.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_videocam));

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage = false;
                Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launchCameraActivity.launch(cameraIntent);
                dialog.dismiss();
            }
        });
        iv_gallery.setOnClickListener(new View.OnClickListener() {
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
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }*/

    /* This method is for getting documents from gallery
       created by Aiman Pathan 23/02/2023 */
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
        albumsFragmentViewModel.updateData(documentPojoList);
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
                albumsFragmentViewModel.updateData(documentPojoList);
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
                    albumsFragmentViewModel.updateData(documentPojoList);
                }
            }
        }
    }


    public String getRealPathFromURI(Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = requireContext().getContentResolver( ).query(
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
                           documentUri = getImageUri(requireContext(), photo);
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

    /* This method is for encoding image to byteArray
       created by Aiman Pathan 22/02/2023 */
    private String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOS = null;
        try {
            System.gc();
            byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        }
        assert byteArrayOS != null;
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

    /* This method is for initializing all view
       created by Aiman Pathan 22/02/2023
       updated by Aiman Pathan */
    void initializeAll(){
        try {
//            tv_upload_document = view.findViewById(R.id.tv_upload_document);
            rv_documents = view.findViewById(R.id.rv_documents);
            fabAdd = view.findViewById(R.id.fab_add);

            albumsFragmentViewModel = new ViewModelProvider(this).get(AlbumsFragmentViewModel.class);
            albumsFragmentViewModel.setDataFromLocal(documentPojoList);
            albumsFragmentViewModel.setLayoutInflater(LayoutInflater.from(requireContext()));
            albumsFragmentViewModel.setLifecycleOwner(this);

            albumsFragmentViewModel.setOnItemClickedListener(new OnItemClickedListener() {
                @Override
                public void OnItemClicked(RecyclerView.ViewHolder holder) {

                }

                @Override
                public void OnItemClicked(int pos) {
                    documentPojoList.remove(pos);
                    albumsFragmentViewModel.updateData(documentPojoList);
                }
            });

            fabAdd.setOnClickListener(view1 -> {
                ShowBottomSheet();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TripModel getValues(){
        TripModel tripModel = new TripModel();
        tripModel.setAlbumList(documentPojoList);
        return tripModel;
    }
}