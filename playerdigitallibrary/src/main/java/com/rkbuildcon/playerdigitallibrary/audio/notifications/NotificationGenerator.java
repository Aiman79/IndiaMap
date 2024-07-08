package com.rkbuildcon.playerdigitallibrary.audio.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;

import com.rkbuildcon.playerdigitallibrary.R;
import com.rkbuildcon.playerdigitallibrary.utils.Constants;

public class NotificationGenerator {
    public static final String CHANNEL_1 = "channel_1";
    public static final String CHANNEL_2 = "channel_2";
    public static final String CHANNEL_PLAY = "play";
    public static final String CHANNEL_PAUSE = "pause";
    public static final String CHANNEL_STOP = "stop";

    private Context context;
    private Class myClass;
    private MediaSessionCompat mediaSessionCompat;
    private NotificationModel notificationModel;

    public NotificationGenerator(Context context, Class myClass, NotificationModel notificationModel) {
        this.context = context;
        this.myClass = myClass;
        this.notificationModel = notificationModel;
        mediaSessionCompat = new MediaSessionCompat(context, "PlayAudio");
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel1 = new NotificationChannel(CHANNEL_1, "Channel player",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel1.setDescription("channel player a desc");

           /* NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_2, "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel2.setDescription("channel2 a desc");*/

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel1);
//            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }

    public void showNotification() {
        Intent intent = new Intent(context, myClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Intent playIntent = new Intent(context, NotificationReciever.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, playIntent,
                PendingIntent.FLAG_IMMUTABLE);

        Intent pauseIntent = new Intent(context, NotificationReciever.class);
        pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(context, 0, pauseIntent,
                PendingIntent.FLAG_IMMUTABLE);

        Notification notification;

        int lIcon = R.drawable.default_album_art;
        if (notificationModel.getNotificationLargeIcon() != -1){
            lIcon = notificationModel.getNotificationLargeIcon();
        }
        String title = "Title";
        String singer = "Singer";
        int nIcon = R.drawable.ic_launcher;
        int play = R.drawable.ic_vector_play;
        int pause = R.drawable.ic_vector_pause;
        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), lIcon);

        if (notificationModel.getNotificationIcon() != -1) {
           nIcon = notificationModel.getNotificationIcon();
        } else if (notificationModel.getFile().getName() != null && !notificationModel.getFile().getName().isEmpty()) {
            title = notificationModel.getFile().getName();
        } else if (notificationModel.getFile().getArtistName() != null && !notificationModel.getFile().getArtistName().isEmpty()) {
            singer = notificationModel.getFile().getArtistName();
        } else if (notificationModel.getPlayBtn() != -1) {
            play = notificationModel.getPlayBtn();
        } else if (notificationModel.getPauseBtn() != -1) {
            pause = notificationModel.getPauseBtn();
        }
        notification = new NotificationCompat.Builder(context, CHANNEL_1)
                .setSmallIcon(nIcon)
                .setLargeIcon(picture)
                .setContentTitle(title)
                .setContentText(singer)
                .addAction(play, "Play", playPendingIntent)
                .addAction(pause, "Pause", pausePendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
