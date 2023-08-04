package mg.itu.m1p10android;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class LocalNotificationManager{
    private Context context;
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public LocalNotificationManager(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void showScheduledNotification(String title, String message, long durationInMillis) {
        // Créez un ID unique pour la notification (utilisé pour l'annulation ultérieure).
        int notificationId = (int) System.currentTimeMillis();

        // Construisez l'intent pour l'ouverture de l'activité spécifique lors de la notification.
        Intent intent = new Intent(context, MainActivity.class);
        // Ajoutez des informations supplémentaires à l'intention si nécessaire.
        // Par exemple, vous pouvez passer des données spécifiques à l'activité pour effectuer une action.
        // intent.putExtra("key", value);

        // Créez l'intent pour l'annulation de la notification.
        Intent cancelIntent = new Intent(context, NotificationCancelReceiver.class);
        cancelIntent.putExtra("notification_id", notificationId);
        alarmIntent = PendingIntent.getBroadcast(context, notificationId, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        // Planifiez l'annulation de la notification après la durée spécifiée.
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + durationInMillis, alarmIntent);

        // Construisez la notification avec l'intention de redirection.
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.bg_alarm)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Cela permettra de supprimer la notification lorsque l'utilisateur clique dessus.

        // Vérifiez si l'appareil utilise Android Oreo (API 26) ou une version supérieure.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            CharSequence channelName = "Channel Name";
            String channelDescription = "Channel Description";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        // Affichez la notification.
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);

    }

    public static class NotificationCancelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int notificationId = intent.getIntExtra("notification_id", 0);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);
        }
    }

}


