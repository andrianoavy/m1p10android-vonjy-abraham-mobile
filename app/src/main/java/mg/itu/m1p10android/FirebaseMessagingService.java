package mg.itu.m1p10android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String CANAL = "NotificationCanal";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = remoteMessage.getNotification().getBody();
        Log.e("FirebaseMessage","Vous venez de recevoir une notification :"+message);
        Log.d("FirebaseMessage","Vous venez de recevoir une notification :"+message);

        // Action sur la notification
        //        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6Xnz2fwf9Yk"));
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent ,0);


        // creer une notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CANAL);
        notificationBuilder.setContentTitle("Madagascar tourisme");
        notificationBuilder.setContentText(message);

        // ajouter l'action
        notificationBuilder.setContentIntent(pendingIntent);

        //Creer la vibration
        long[] vibrationPattern = {500,1000};
        notificationBuilder.setVibrate(vibrationPattern);

        //icon
        notificationBuilder.setSmallIcon(R.drawable.bg_alarm);

        //envoyer la notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = getString(R.string.notification_channel_id);
            String channelTitle = getString(R.string.notification_channel_title);
            String channelDescription = getString(R.string.notification_channel_desc);
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelTitle,NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationBuilder.setChannelId(channelId);
        }
        notificationManager.notify(1,notificationBuilder.build());
    }
}