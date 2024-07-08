package com.rkbuildcon.playerdigitallibrary.video.interfaces;

import android.media.MediaPlayer;

public interface VideoPlayerListener {
    public void onBufferUpdate(int percent);
    void onProgressUpdate(int percent);
    void onPlay();
    void onPause();
    void onStop();
}
