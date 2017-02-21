package com.eeyuva.fcm;

/**
 * Created by kavi on 14/08/16.
 */


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.eeyuva.Application;
import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.splash.SplashActivity;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import okhttp3.internal.Util;

public class DeliverxFBMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Utils.printJson(remoteMessage.getData());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Activity -----> " + Application.isActivityVisible());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification modid: " + remoteMessage.getData().get("modid"));
            Log.d(TAG, "Message Notification articleid: " + remoteMessage.getData().get("articleid"));

            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // do stuff
                Log.d(TAG, "Message Notification Tag: " + key + " --- " + value);
            }


//            if (!Application.isActivityVisible())
                sendNotification(remoteMessage);
//                sendNotification(remoteMessage.getNotification().getTitle() + "\n\n" + remoteMessage.getNotification().getBody());
//            else {
//                Intent intents = new Intent(Constants.DISPLAY_MESSAGE_ACTION);
//                intents.putExtra(Constants.MESSAGE_DATA, remoteMessage.getNotification().getTitle() + "\n\n " + remoteMessage.getNotification().getBody());
//                intents.putExtra(Constants.TAG_Module_ID, remoteMessage.getData().get(Constants.TAG_Module_ID));
//                intents.putExtra(Constants.TAG_Module_Name, remoteMessage.getData().get(Constants.TAG_Module_Name));
//                intents.putExtra(Constants.TAG_Article_ID, remoteMessage.getData().get(Constants.TAG_Article_ID));
//                sendBroadcast(intents);
//            }

        }

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intents = new Intent(this, SplashActivity.class);
        intents.putExtra(Constants.TAG_Notification, "Noti");
        intents.putExtra(Constants.TAG_Module_ID, remoteMessage.getData().get(Constants.TAG_Module_ID));
        intents.putExtra(Constants.TAG_Module_Name, remoteMessage.getData().get(Constants.TAG_Module_Name));
        intents.putExtra(Constants.TAG_Article_ID, remoteMessage.getData().get(Constants.TAG_Article_ID));
        intents.putExtra("type", "home");

        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intents,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(remoteMessage.getNotification().getTitle())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
