package com.rkbuildcon.playerdigitallibrary.audio.notifications;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.rkbuildcon.playerdigitallibrary.audio.interfaces.MediaPlayerListener;
import com.rkbuildcon.playerdigitallibrary.utils.Constants;

public class NotificationService  extends Service {
    public final String LOG_TAG = "NotificationService";
    Notification status;
    public static Class myClass;
    private MediaPlayerListener mediaPlayerListener;
    private IBinder mBinder = new MyBinder();

    @Override
    public void onDestroy() {
        Log.e(LOG_TAG, "service destroyed");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       if (intent.getStringExtra("MyAction").equals(Constants.ACTION.PLAY_ACTION)) {
            if (mediaPlayerListener != null){
                mediaPlayerListener.onPlay();
            }
            Log.i(LOG_TAG, "Clicked Play");
        } else if (intent.getStringExtra("MyAction").equals(Constants.ACTION.PAUSE_ACTION)) {
            if (mediaPlayerListener != null){
                mediaPlayerListener.onPause();
            }
            Log.i(LOG_TAG, "Clicked Pause");
        }
        return START_STICKY;
    }

    public void setMediaPlayerListener(MediaPlayerListener mediaPlayerListener){
        this.mediaPlayerListener = mediaPlayerListener;
    }

    public class MyBinder extends  Binder{
        public NotificationService getService(){
            return NotificationService.this;
        }
    }
}
