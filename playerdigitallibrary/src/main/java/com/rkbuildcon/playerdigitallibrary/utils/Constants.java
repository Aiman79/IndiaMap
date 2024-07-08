package com.rkbuildcon.playerdigitallibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rkbuildcon.playerdigitallibrary.R;

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.playerdigitallibrary.customnotification.action.main";
        public static String INIT_ACTION = "com.playerdigitallibrary.customnotification.action.init";
        public static String PREV_ACTION = "com.playerdigitallibrary.customnotification.action.prev";
        public static String PLAY_ACTION = "com.playerdigitallibrary.customnotification.action.play";
        public static String PAUSE_ACTION = "com.playerdigitallibrary.customnotification.action.pause";
        public static String NEXT_ACTION = "com.playerdigitallibrary.customnotification.action.next";
        public static String STARTFOREGROUND_ACTION = "com.playerdigitallibrary.customnotification.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.playerdigitallibrary.customnotification.action.stopforeground";

    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }


}
