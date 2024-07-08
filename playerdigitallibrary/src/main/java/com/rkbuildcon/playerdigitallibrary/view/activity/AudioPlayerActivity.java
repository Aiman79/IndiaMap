package com.rkbuildcon.playerdigitallibrary.view.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.rkbuildcon.playerdigitallibrary.audio.ClsEntryClass;
import com.rkbuildcon.playerdigitallibrary.R;
import com.rkbuildcon.playerdigitallibrary.audio.interfaces.MediaPlayerListener;
import com.rkbuildcon.playerdigitallibrary.audio.notifications.NotificationModel;
import com.rkbuildcon.playerdigitallibrary.model.FilesModel;
import com.rkbuildcon.playerdigitallibrary.view.fragments.AudioPlayerFragment;

import java.io.File;

public class AudioPlayerActivity extends AppCompatActivity {
    public final static String TAG_FILE = "file";
    //view variables
    private FrameLayout fmMain;

    //other variables
    private AudioPlayerFragment audioPlayerFragment;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio_player);

        getBundle();
        registerViews();
        init();
    }

    public void getBundle(){
        if (getIntent() != null){
            file = (File) getIntent().getSerializableExtra(TAG_FILE);
        }
    }

    public void registerViews(){
        fmMain = findViewById(R.id.fm_main);
    }

    public void init(){
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setFile(new FilesModel(file, true));
        ClsEntryClass entryClass = new ClsEntryClass(notificationModel, new MediaPlayerListener() {
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
        }, this,AudioPlayerActivity.class );
        audioPlayerFragment = AudioPlayerFragment.newInstance(entryClass);
        replaceFragment(R.id.fm_main, audioPlayerFragment, "audio player", "");
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

}