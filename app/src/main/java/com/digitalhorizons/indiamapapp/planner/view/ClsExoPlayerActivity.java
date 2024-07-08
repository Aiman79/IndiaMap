/* Filename :- ClsExoPlayerActivity.java
Directory :- src/main/java/com/nixel/customexoplayer/ClsExoPlayerActivity.java
Description :- Its is the ClsExoPlayerActivity which has the exoplayer and it handles the all the events like mute,lock,play,pause,loop .
Created - January 09, 2023
Author - Yogeswara Sri Rama Reddy Nallamilli
Updated - January 09, 2023
Updater - Yogeswara Sri Rama Reddy Nallamilli
*/

package com.digitalhorizons.indiamapapp.planner.view;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

/* Class :- ClsExoPlayerActivity is the Activity. Its Contains the exoplayer and it handles the all the events like mute,lock,play,pause,loop .
Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

public class ClsExoPlayerActivity extends AppCompatActivity {

    private ExoPlayer exoPlayer;
    private ImageView bt_fullscreen, bt_lockscreen; //bt_mute, bt_loop;
    private boolean isFullScreen = false;
    private boolean isLock = false;
//    private boolean isMute = false;
//    private boolean isLoop = false;
    private PlayerView playerView;
    private ProgressBar progressBar;
    private Uri videoUrl;
//    private String strUrl = "https://media.oasisapi.net/exercises/wsinmrmfwdrrwujqiypp.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_cls_exo_player);
            getBundle();
            registerViews();
//            updatingUrl();
            onClickEvents();
            settingUpExoPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBundle(){
        if (getIntent() != null){
            String url  = (String) getIntent().getExtras().getSerializable(AppUtils.DATA_TRIP);
            videoUrl = Uri.parse(url);
        }
    }

    /* Class :- registerViews is the method . It has the code for registering Views .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void registerViews() {
        try {
            playerView = findViewById(R.id.player);
            progressBar = findViewById(R.id.progress_bar);
            bt_fullscreen = findViewById(R.id.bt_fullscreen);
            bt_lockscreen = findViewById(R.id.exo_lock);
//            bt_mute = findViewById(R.id.exo_mute);
//            bt_loop = findViewById(R.id.exo_loop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Class :- updatingUrl is the method . It has the code for updating the url .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

 /*   private void updatingUrl() {
        if (getIntent().getStringExtra(EXTRA_URL).length() > 0)
            strUrl = getIntent().getStringExtra(EXTRA_URL);
    }*/

    /* Class :- onClickEvents is the method . It has the code for onClick events .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void onClickEvents() {
        try {
            bt_fullscreen.setOnClickListener(view -> fullscreenClicked());
            bt_lockscreen.setOnClickListener(view -> lockClicked());
//            bt_mute.setOnClickListener(view -> isMuteClicked());
//            bt_loop.setOnClickListener(view -> isLoopClicked());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Class :- settingUpExoPlayer is the method .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void settingUpExoPlayer() {
        try {
            TrackSelector trackSelector = new DefaultTrackSelector(this);
            exoPlayer = new ExoPlayer.Builder(this)
                    .setSeekBackIncrementMs(15000)
                    .setSeekForwardIncrementMs(15000)
                    .setTrackSelector(trackSelector)
                    .build();
            playerView.setPlayer(exoPlayer);
            playerView.setKeepScreenOn(true);
            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {

                    if (playbackState == Player.STATE_BUFFERING) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else if (playbackState == Player.STATE_READY) {
                        progressBar.setVisibility(View.GONE);
                    } else if (playbackState == Player.STATE_ENDED) {// && !isLoop
                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

//            Uri videoUrl = Uri.parse(strUrl);
            MediaItem media = MediaItem.fromUri(videoUrl);
            exoPlayer.setMediaItem(media);
            exoPlayer.prepare();
            exoPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Class :- fullscreenClicked is the method . Its has the code for the onclick events of LANDSCAPE and PORTRAIT .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void fullscreenClicked() {
        try {
            if (!isFullScreen) {
                bt_fullscreen.setImageDrawable(
                        ContextCompat
                                .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                bt_fullscreen.setImageDrawable(ContextCompat
                        .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            isFullScreen = !isFullScreen;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Class :- lockClicked is the method .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void lockClicked() {
        try {
            if (!isLock) {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock));
            } else {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock_open_24));
            }
            isLock = !isLock;
            lockScreen(isLock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Class :- lockClicked is the method . Its has code to mute and unMute .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

   /* private void isMuteClicked() {
        try {
            if (!isMute) {
                bt_mute.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mute));
                exoPlayer.setVolume(0f);
            } else {
                bt_mute.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.listen));
                exoPlayer.setVolume(1f);
            }
            isMute = !isMute;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /* Class :- lockClicked is the method . Its has code to loop and single .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

   /* private void isLoopClicked() {
        try {
            if (!isLoop) {
                bt_loop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedloop));
                exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            } else {
                bt_loop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loop));
                exoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
            }
            isLoop = !isLoop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /* Class :- lockScreen is the method . Its has code to handle the visibility of views .
    Created by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023.
    Updated by Yogeswara Sri Rama Reddy Nallamilli January 09, 2023. */

    private void lockScreen(boolean lock) {
        try {
            LinearLayout sec_mid = findViewById(R.id.sec_controlvid1);
            LinearLayout sec_bottom = findViewById(R.id.sec_controlvid2);
            if (lock) {
                sec_mid.setVisibility(View.INVISIBLE);
                sec_bottom.setVisibility(View.INVISIBLE);
//                bt_mute.setVisibility(View.INVISIBLE);
//                bt_loop.setVisibility(View.INVISIBLE);
            } else {
                sec_mid.setVisibility(View.VISIBLE);
                sec_bottom.setVisibility(View.VISIBLE);
//                bt_mute.setVisibility(View.VISIBLE);
//                bt_loop.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {

        if (isLock) return;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bt_fullscreen.performClick();
        } else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.play();
    }
}

/* End File */