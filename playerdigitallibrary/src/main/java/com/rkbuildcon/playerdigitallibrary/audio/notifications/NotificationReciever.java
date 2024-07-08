package com.rkbuildcon.playerdigitallibrary.audio.notifications;

import static com.rkbuildcon.playerdigitallibrary.utils.Constants.ACTION.PAUSE_ACTION;
import static com.rkbuildcon.playerdigitallibrary.utils.Constants.ACTION.PLAY_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null){
            Intent intent1 = new Intent(context, NotificationService.class);
            switch (intent.getAction()){
                case PLAY_ACTION:
                case PAUSE_ACTION:
                    intent1.putExtra("MyAction", intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
