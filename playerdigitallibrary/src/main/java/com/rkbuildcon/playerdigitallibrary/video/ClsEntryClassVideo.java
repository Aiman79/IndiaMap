package com.rkbuildcon.playerdigitallibrary.video;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rkbuildcon.playerdigitallibrary.audio.interfaces.MediaPlayerListener;
import com.rkbuildcon.playerdigitallibrary.audio.notifications.NotificationService;
import com.rkbuildcon.playerdigitallibrary.model.FilesModel;
import com.rkbuildcon.playerdigitallibrary.video.interfaces.VideoPlayerListener;

import java.io.File;
import java.io.IOException;

public class ClsEntryClassVideo implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener {
//    private VideoView videoView;
    private FilesModel filesModel;
    private boolean isLive;
    private Context context;
    private MediaPlayer mediaPlayer;
    private VideoPlayerListener videoPlayerListener;
    private SurfaceHolder holder;

    public ClsEntryClassVideo(FilesModel filesModel, VideoPlayerListener videoPlayerListener,
                              Context context, SurfaceHolder holder) {
        this.filesModel = filesModel;
        this.context = context;
        this.videoPlayerListener = videoPlayerListener;
        this.holder = holder;
        setLive();
        mediaPlayer = new MediaPlayer();
    }

    public void setLive(){
        isLive = filesModel.getLink() != null && !filesModel.getLink().isEmpty();
    }

    public String getVideoLink(){return filesModel.getLink();}

    public File getVideoFile(){
        return filesModel.getFile();
    }

    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }

   /* public void playVideo(){
        Uri uri = Uri.parse(filesModel.getLink());
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }*/

    public void playVideo() {
        try {
            if (isLive){
                mediaPlayer.setDataSource(filesModel.getLink());
            } else {
                mediaPlayer.setDataSource(filesModel.getFile().getPath());
            }
            mediaPlayer.setDisplay(holder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
        );
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnErrorListener(this);
    }

    public void pauseVideo(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
                if (videoPlayerListener != null){
                    videoPlayerListener.onPause();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopVideo(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                if (videoPlayerListener != null){
                    videoPlayerListener.onStop();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (videoPlayerListener != null){
            videoPlayerListener.onBufferUpdate(i);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mediaPlayer.start();
        if (videoPlayerListener != null){
            videoPlayerListener.onPlay();
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
         return false;
    }
}
