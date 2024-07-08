package com.rkbuildcon.playerdigitallibrary.audio;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;

import com.rkbuildcon.playerdigitallibrary.audio.interfaces.MediaPlayerListener;
import com.rkbuildcon.playerdigitallibrary.model.FilesModel;
import com.rkbuildcon.playerdigitallibrary.audio.notifications.NotificationGenerator;
import com.rkbuildcon.playerdigitallibrary.audio.notifications.NotificationModel;
import com.rkbuildcon.playerdigitallibrary.audio.notifications.NotificationService;
import com.rkbuildcon.playerdigitallibrary.utils.Constants;

import java.io.File;
import java.io.Serializable;

public class ClsEntryClass implements Serializable, ServiceConnection {
//    private File file;
//    private String url;
//    private String thumbnail;


    private MediaPlayerListener mediaPlayerListener;
    private MediaPlayer mediaPlayer;

    private Context context;
    private NotificationService notificationService;
    private NotificationGenerator notificationGenerator;
    private NotificationModel notificationModel;
    private MediaPlayerListener notificationCallBacks;

    //audio focus
    AudioAttributes playbackAttributes;
    private  AudioManager audioManager;
    private int audioFocusRequest;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = focusChange -> {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            playAudio();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            pauseAudio();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            stopAudio();
        }
    };


  /*  public ClsEntryClass(String url, MediaPlayerListener mediaPlayerListener) {
        this.url = url;
        this.mediaPlayerListener = mediaPlayerListener;
    }*/

    /*public ClsEntryClass(File file, MediaPlayerListener mediaPlayerListener) {
//        this.file = file;
        notificationModel = new NotificationModel();
        FilesModel filesModel = new FilesModel(file, true);
        notificationModel.setFile(filesModel);
        this.mediaPlayerListener = mediaPlayerListener;
    }*/
    public ClsEntryClass(NotificationModel notificationModel, MediaPlayerListener mediaPlayerListener,Context context, Class myClass) {
        this.notificationModel = notificationModel;
        this.mediaPlayerListener = mediaPlayerListener;
        this.context = context;
        this.mediaPlayer = MediaPlayer.create(context, Uri.fromFile(notificationModel.getFile().getFile()));
        NotificationService.myClass = myClass;
    }

    public File getFile() {
        return notificationModel.getFile().getFile();
    }

    public String getUrl() {
        return notificationModel.getFile().getLink();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaPlayerListener getMediaPlayerListener() {
        return mediaPlayerListener;
    }

    public String getThumbnail() {
        return notificationModel.getFile().getThumbnail();
    }

    public void setThumbnail(String thumbnail) {
        this.notificationModel.getFile().setThumbnail(thumbnail);
    }

    public void playAudio(){
        if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.start();
                    if (mediaPlayerListener != null){
                        mediaPlayerListener.onPlay();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pauseAudio(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
                if (mediaPlayerListener != null){
                    mediaPlayerListener.onPause();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopAudio(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                if (mediaPlayerListener != null){
                    mediaPlayerListener.onStop();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setUpAudioFocus(){
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        // set the playback attributes for the focus requester
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(playbackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .build();

            audioFocusRequest = audioManager.requestAudioFocus(focusRequest);
        }
    }

    public void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void setUpNotification(){
        Intent intent = new Intent(context, NotificationService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            entryClass.startService();
            notificationGenerator = new NotificationGenerator(context, NotificationService.myClass, notificationModel);
            notificationGenerator.createNotificationChannel();
            notificationCallBacks = new MediaPlayerListener() {
                @Override
                public void onPlay() {
                    playAudio();
                    mediaPlayerListener.onPlay();
                }

                @Override
                public void onPause() {
                    pauseAudio();
                    mediaPlayerListener.onPause();
                }

                @Override
                public void onStop() {
                    stopAudio();
                    mediaPlayerListener.onStop();
                }

                @Override
                public void onSeekBarProgressUpdate(int progress) {

                }
            };
        }
    }

    public void unBindService(){
        context.unbindService(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startService() {
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        context.startService(serviceIntent);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        NotificationService.MyBinder binder = (NotificationService.MyBinder) iBinder;
        notificationService = binder.getService();
        notificationService.setMediaPlayerListener(notificationCallBacks);
        Log.e("Connected", notificationService + "");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        notificationService = null;
        Log.e("Disconnected", null + "");
    }

    public void showNotification(){
        notificationGenerator.showNotification();
    }
}
