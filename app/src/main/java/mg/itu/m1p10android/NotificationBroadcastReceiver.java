package mg.itu.m1p10android;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull Context context, Intent intent) {
        String title = "Madagascar tourisme";
        String message = "Voir les sites touristiques près de vous";
        long durationInMillis = 180000; // Durée en millisecondes (1 minute dans cet exemple).

        LocalNotificationManager notificationManager = new LocalNotificationManager(context);
        notificationManager.showScheduledNotification(title, message, durationInMillis);
    }
}
