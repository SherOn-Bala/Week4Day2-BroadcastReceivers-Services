package ca.judacribz.week4day2_broadcastreceivers_services.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import ca.judacribz.week4day2_broadcastreceivers_services.MainActivity;
import ca.judacribz.week4day2_broadcastreceivers_services.R;
import ca.judacribz.week4day2_broadcastreceivers_services.model.Person;

import static ca.judacribz.week4day2_broadcastreceivers_services.MainActivity.ACTION_PLAY_MUSIC;
import static ca.judacribz.week4day2_broadcastreceivers_services.list.PersonAdapter.ACTION_SHOW_NOTIFICATION;
import static ca.judacribz.week4day2_broadcastreceivers_services.list.PersonAdapter.EXTRA_PERSON_FROM;
import static ca.judacribz.week4day2_broadcastreceivers_services.list.PersonAdapter.EXTRA_PERSON_NAME;
import static ca.judacribz.week4day2_broadcastreceivers_services.list.PersonAdapter.PREF_FILE;
import static ca.judacribz.week4day2_broadcastreceivers_services.service.PersonService.EXTRA_PERSON;
import static ca.judacribz.week4day2_broadcastreceivers_services.util.UI.buildNotification;
import static ca.judacribz.week4day2_broadcastreceivers_services.util.UI.createNotificationChannel;


public class ForegroundService extends Service {
    private static final String ACTION_STOP_MUSIC = "ACTION_STOP_MUSIC";

    private static final String NOTIFICATION_CHANNEL_ID =
            "ca.judacribz.week4day2_broadcastreceivers_services.MUSIC_CHANNEL";
    private static final int NOTIFY_MUSIC_ID = 1001;
    private static final int NOTIFY_PERSON_ID = 1002;

    MediaPlayer mediaPlayer = null;
    NotificationManager notifyManager;
    NotificationCompat.Builder stopMusicBuilder, showMainBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create notification channel
        notifyManager = createNotificationChannel(this, NOTIFICATION_CHANNEL_ID);


        // Create pending intent to stop music playback
        PendingIntent stopMusicIntent = PendingIntent.getService(
                this,
                0,
                new Intent(this, ForegroundService.class).setAction(ACTION_STOP_MUSIC),
                0
        );

        // Build Notification
        stopMusicBuilder = buildNotification(
                this,
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.stop_music),
                null,
                stopMusicIntent
        );

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_PLAY_MUSIC:
                    mediaPlayer = MediaPlayer.create(this, R.raw.lion_king);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            notifyManager.cancel(NOTIFY_MUSIC_ID);
                            stopMediaPlayer();
                        }
                    });

                    mediaPlayer.start();

                    if (notifyManager != null && stopMusicBuilder != null) {
                        notifyManager.notify(NOTIFY_MUSIC_ID, stopMusicBuilder.build());
                    }

                    break;

                case ACTION_STOP_MUSIC:
                    stopMediaPlayer();

                    break;
                case ACTION_SHOW_NOTIFICATION:
                    if (notifyManager != null) {
                        notifyManager.cancel(NOTIFY_PERSON_ID);
                        PendingIntent showMainIntent = PendingIntent.getActivity(
                                this,
                                0,
                                new Intent(this, MainActivity.class),
                                0
                        );
                        SharedPreferences sharedPreferences = getSharedPreferences(
                                PREF_FILE,
                                Context.MODE_PRIVATE
                        );
                        // Build Notification
                        showMainBuilder = buildNotification(
                                this,
                                NOTIFICATION_CHANNEL_ID,
                                sharedPreferences.getString(EXTRA_PERSON_NAME, ""),
                                sharedPreferences.getString(EXTRA_PERSON_FROM, ""),
                                showMainIntent
                        );

                        if (showMainBuilder != null) {
                            notifyManager.notify(NOTIFY_PERSON_ID, showMainBuilder.build());
                        }
                    }
                    break;
            }
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
}
