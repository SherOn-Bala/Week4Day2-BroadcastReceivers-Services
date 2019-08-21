package ca.judacribz.week4day2_broadcastreceivers_services.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import ca.judacribz.week4day2_broadcastreceivers_services.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class UI {

    public static NotificationManager createNotificationChannel(Context context, String notificationId) {
        NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (mNotifyManager != null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        notificationId,
                        context.getString(R.string.notify_channel_name),
                        NotificationManager.IMPORTANCE_HIGH
                );

                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(context.getColor(R.color.colorPrimary));
                notificationChannel.enableVibration(true);
                notificationChannel.setDescription(
                        context.getString(R.string.notify_channel_desc)
                );

                mNotifyManager.createNotificationChannel(notificationChannel);
            }
        }

        return mNotifyManager;
    }

    public static NotificationCompat.Builder buildNotification(Context context,
                                                               String notificationId,
                                                               String title,
                                                               @Nullable String text,
                                                               PendingIntent pendingIntent) {
        return new NotificationCompat.Builder
                (context, notificationId)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(text != null ? text : "")
                .setSmallIcon(R.drawable.ic_music)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);


    }
}

