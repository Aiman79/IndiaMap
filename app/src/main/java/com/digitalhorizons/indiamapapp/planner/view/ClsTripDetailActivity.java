package com.digitalhorizons.indiamapapp.planner.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.planner.model.DocumentPojo;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.planner.viewmodel.TripDetailViewModel;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rkbuildcon.playerdigitallibrary.audio.*;
import com.rkbuildcon.playerdigitallibrary.audio.interfaces.*;

public class ClsTripDetailActivity extends AppCompatActivity {

    private RecyclerView rvDocs, rvAlbum;
    private TripDetailViewModel tripDetailViewModel;
    private AppCompatTextView tvTitle, tvDesc, tvDate, tvCity;
    private ConstraintLayout clImg, clVideo;
    private AppCompatImageView ivBackImg, ivBackVideo, ivImage;
    private TripModel mTripModel;
    private VideoView videoView;

    //toolbar
    private AppCompatTextView tvToolbarTitle;
    private AppCompatImageView ivToolbarBack;
    private Toolbar toolbar;

    //video
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        getBundle();
        registerViews();
        setUpToolbar();
        init();
        addListeners();
    }

    private void getBundle(){
        if (getIntent() != null){
            if (getIntent().getExtras() != null){
                mTripModel = getIntent().getExtras().getParcelable(AppUtils.DATA_TRIP);
            }
        }
    }

    private void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);

        tvToolbarTitle.setText(getString(R.string.trip_description));
        ivToolbarBack.setOnClickListener(view -> onBackPressed());

        try {
            toolbar.inflateMenu(R.menu.menu_trip_description);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (clImg.getVisibility() == View.VISIBLE){
            clImg.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    public void registerViews() {
        rvAlbum = findViewById(R.id.rv_album);
        rvDocs = findViewById(R.id.rv_docs);
        clImg = findViewById(R.id.cl_img);
        ivBackImg = findViewById(R.id.iv_back_img);
        ivImage = findViewById(R.id.iv_img);
        tvTitle = findViewById(R.id.tv_trip_title);
        tvDesc = findViewById(R.id.tv_desc);
        tvDate = findViewById(R.id.tv_date);
        tvCity = findViewById(R.id.tv_city);
        ivBackVideo = findViewById(R.id.iv_back_video);
        videoView = findViewById(R.id.video_view);
        clVideo = findViewById(R.id.cl_video);

        tripDetailViewModel = new ViewModelProvider(this).get(TripDetailViewModel.class);
        tripDetailViewModel.setLayoutInflater(LayoutInflater.from(this));
        tripDetailViewModel.setLifecycleOwner(this);
    }

    public void init() {
        setData();
        setUpRecyclerView();
    }

    public void setData(){
       try {
           tvTitle.setText(mTripModel.getTitle());
           tvDesc.setText(mTripModel.getDesc());
           tvCity.setText(mTripModel.getCity().concat(", ").concat(mTripModel.getCountry()));
           tvDate.setText(mTripModel.getStartDate()
                   .concat(" ")
                   .concat(getString(R.string.to))
                   .concat(" ")
                   .concat(mTripModel.getEndDate()));
           tripDetailViewModel.setmDocsList(mTripModel.getDocumentList());
           tripDetailViewModel.setmAlbumList(mTripModel.getAlbumList());
       } catch (Exception e){
           e.printStackTrace();
       }
    }

    public void addListeners(){

        ivBackImg.setOnClickListener(view -> {
            clImg.setVisibility(View.GONE);
        });

        ivBackVideo.setOnClickListener(view -> {
            clVideo.setVisibility(View.GONE);
        });

//        ivBack.setOnClickListener(view -> onBackPressed());
    }

    public void setUpRecyclerView(){
        tripDetailViewModel.setUpRecyclerView(rvDocs);
        tripDetailViewModel.setOnDocsItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                try{
                    DocumentPojo documentPojo = tripDetailViewModel.getDocumentsWithPos(pos);
                    Uri uri = documentPojo.getDocumentUrl();
                    if (documentPojo.getType().equalsIgnoreCase("pdf")){
                        openFile(uri);
                    } else if (documentPojo.getType().equalsIgnoreCase("mp4")){
                        Intent intent = new Intent(ClsTripDetailActivity.this, ClsExoPlayerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AppUtils.DATA_TRIP, uri.toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        openVideo(uri);
//                        clVideo.setVisibility(View.VISIBLE);
                    } else {
                        Glide.with(ivImage).load(uri).into(ivImage);
                        clImg.setVisibility(View.VISIBLE);
                    }

//                    takePersistablePermissions(ClsTripDetailActivity.this, uri);
//                    Glide.with(ivImage).load(tripDetailViewModel.getAlbumWithPos(pos).getUrl()).into(ivImage);
//                    clImg.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        tripDetailViewModel.setUpAlbumRecyclerView(rvAlbum);
        tripDetailViewModel.setOnAlbumItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {

                DocumentPojo documentPojo = tripDetailViewModel.getAlbumWithPos(pos);
                Uri uri = documentPojo.getDocumentUrl();
                if (documentPojo.getType().equalsIgnoreCase("pdf")){
                    openFile(uri);
                } else if (documentPojo.getType().equalsIgnoreCase("mp4")){
                    Intent intent = new Intent(ClsTripDetailActivity.this, ClsExoPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppUtils.DATA_TRIP, uri.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
//                        openVideo(uri);
//                        clVideo.setVisibility(View.VISIBLE);
                } else if(documentPojo.getType().equalsIgnoreCase("mp3")){
                    openDialogForAudio();
                }
                else {
                    Glide.with(ivImage).load(uri).into(ivImage);
                    clImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * method for opening the dialog box for filter by status
     */
    public void openDialogForAudio() {
        View view = getLayoutInflater().inflate(R.layout.fragment_dialog_audio, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorWhiteTransparentDark));

        dialog.show();

        AppCompatImageView ivPlay = view.findViewById(R.id.iv_play);
        AppCompatImageView ivPause = view.findViewById(R.id.iv_pause);
        AppCompatImageView ivClear = view.findViewById(R.id.iv_cancel);

        ClsEntryClass clsEntryClass = new ClsEntryClass(null, new MediaPlayerListener() {
            @Override
            public void onPlay() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onSeekBarProgressUpdate(int progress) {

            }
        },
                this, ClsTripDetailActivity.class);

        ivPlay.setOnClickListener(view1 -> clsEntryClass.playAudio());

        ivPause.setOnClickListener(view1 -> {
            clsEntryClass.pauseAudio();
        });

        ivClear.setOnClickListener(view1 -> {
            clsEntryClass.stopAudio();
            clsEntryClass.clearMediaPlayer();
            dialog.dismiss();
        });
    }

    private void openFile(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openVideo(Uri videoUrl){
//        clVideo.setVisibility(View.VISIBLE);
        mediaController= new MediaController(ClsTripDetailActivity.this);
        mediaPlayer = new MediaPlayer();
//        String vPath = "android.resource://"+ requireActivity().getPackageName()+"/raw/chay_and_shadi";
//        Uri videoUrl = Uri.parse(vPath);
        videoView.setVideoURI(videoUrl);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPopupWindow(view);
            }
        });
    }
/*
    public void showPopupWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_video, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 10, 10);

        AppCompatImageView iv_pause = popupView.findViewById(R.id.iv_pause);
        AppCompatImageView iv_play = popupView.findViewById(R.id.iv_play);
        AppCompatImageView iv_cancel = popupView.findViewById(R.id.iv_cancel);

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    videoView.pause();
                    iv_play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    Toast.makeText(ClsTripDetailActivity.this, "Pause ", Toast.LENGTH_SHORT).show();
                }else if(!mediaPlayer.isPlaying()){
                    videoView.start();
                    iv_play.setImageResource(R.drawable.ic_baseline_pause_24);
                    Toast.makeText(ClsTripDetailActivity.this, "Playing ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();
                iv_play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                iv_pause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                Toast.makeText(ClsTripDetailActivity.this, "Pause ", Toast.LENGTH_SHORT).show();
            }
        });
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                popupWindow.dismiss();
//                return true;
//            }
//        });
    }*/

    private ActivityResultLauncher<Intent> activityResultLauncherOpenURI = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try{
                        takePersistablePermissions(ClsTripDetailActivity.this, result.getData().getData());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
    );

    private void takePersistablePermissions(Activity appCompatActivity, Uri fileUri) {
        try {
//            Intent.ACTION_OPEN_DOCUMENT
            appCompatActivity.grantUriPermission(appCompatActivity.getPackageName(), fileUri, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            appCompatActivity.getContentResolver().takePersistableUriPermission(fileUri, takeFlags);
            ivImage.setImageURI(fileUri);
            clImg.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trip_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editData();
                return true;
            case R.id.menu_delete:
                showLogoutDialog();
                return true;
            default: return false;
        }
    }

    private void deleteData() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppUtils.DATA_TRIP, mTripModel);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
    }

    private void editData() {
        Intent intent = new Intent(this, ClsAddTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppUtils.DATA_TRIP, mTripModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showLogoutDialog(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.are_you_sure_to_delete)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                       deleteData();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}