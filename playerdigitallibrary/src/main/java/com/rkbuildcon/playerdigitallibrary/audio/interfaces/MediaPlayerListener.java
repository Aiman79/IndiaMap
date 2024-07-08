package com.rkbuildcon.playerdigitallibrary.audio.interfaces;

public interface MediaPlayerListener {
    public void onPlay();
    public void onPause();
    public void onStop();
    public void onSeekBarProgressUpdate(int progress);
}
