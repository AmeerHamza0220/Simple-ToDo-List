package com.example.lablnet.simplenote;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

/**
 * Created by lablnet on 8/27/2017.
 */

public class AlarmReciever extends BroadcastReceiver {
    int currentReplay = 0;
    int repeatMedia = 4;

    @Override
    public void onReceive(final Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(text);
        Notification notification = builder.build();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean setAlarm = pref.getBoolean("switch_key", false);
        if (setAlarm) {

            final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.beep);
            mediaPlayer.start();
            currentReplay++;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (currentReplay < repeatMedia) {
                        mediaPlayer.start();
                    }
                    currentReplay++;
                }
            });
        }
        NotificationManagerCompat notionficationmanager = NotificationManagerCompat.from(context);
        notionficationmanager.notify(0, notification);
    }

}
