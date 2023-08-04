package mg.itu.m1p10android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationScheduler {
    private static final int NOTIFICATION_ID = 1234; // Un identifiant unique pour la notification.
    private static final long INTERVAL_TIME = 5 * 60 * 1000; // 5 minutes en millisecondes.

    public static void scheduleNotification(Context context) {
        // Créez l'intention pour l'envoi de la notification.
        Intent notificationIntent = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obtenez l'instance de l'AlarmManager.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Planifiez la répétition de la notification toutes les 5 minutes.
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL_TIME, pendingIntent);
        }
    }

    public static void cancelNotification(Context context) {
        // Créez l'intention pour l'annulation de la notification.
        Intent notificationIntent = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obtenez l'instance de l'AlarmManager.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Annulez la répétition de la notification.
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
