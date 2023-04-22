package com.alreyada.app.notifications;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alreyada.app.data.preference.AppPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        // send the token to wordpress
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> params = remoteMessage.getData();

            if (AppPreference.getInstance(MyFirebaseMessagingService.this).isNotificationOn()) {

                sendNotification(params.get("title"), params.get("message"), params.get("newsId"));
                broadcastNewNotification();
            }
        }

    }

    private void sendNotification(String title, String messageBody, String postId) {

        // insert data into database
        //NotificationDbController notificationDbController = new NotificationDbController(MyFirebaseMessagingService.this);
        //notificationDbController.insertData(title, messageBody, postId);
    }


    private void broadcastNewNotification() {
        Intent intent = new Intent("new_notification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
