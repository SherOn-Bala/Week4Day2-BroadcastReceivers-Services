package ca.judacribz.week4day2_broadcastreceivers_services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;


public class ForegroundService extends Service {
    private static final String ACTION_STOP = "ACTION_STOP";
    NotificationManager mNotifyManager;

    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    MediaPlayer mediaPlayer = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_STOP:
                    stopMediaPlayer();
                    break;
            }
        } else {

            mediaPlayer = MediaPlayer.create(this, R.raw.lion_king);
            mediaPlayer.start();

            createNotificationChannel();
            PendingIntent pendingIntent = PendingIntent.getService(
                    this,
                    0,
                    new Intent(this, ForegroundService.class).setAction(ACTION_STOP),
                    0
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder
                    (this, PRIMARY_CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(getString(R.string.stop_music))
                    .setSmallIcon(R.drawable.ic_music)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);

            mNotifyManager.notify(0, builder.build());
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        stopMediaPlayer();
    }

    void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void createNotificationChannel() {

        // Define notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    "Job Service notification",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
