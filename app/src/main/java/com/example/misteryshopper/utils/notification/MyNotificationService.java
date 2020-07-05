package com.example.misteryshopper.utils.notification;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.misteryshopper.datbase.DBHelper;
import com.example.misteryshopper.datbase.impl.FirebaseDBHelper;
import com.example.misteryshopper.utils.SharedPrefConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyNotificationService extends FirebaseMessagingService {

    private static DBHelper mDBHelper = FirebaseDBHelper.getInstance();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


        if(remoteMessage != null){
            String title = remoteMessage.getNotification().getTitle();
            Map<String,String> body = remoteMessage.getData();
            NotificationHandler.displayNotification(getApplicationContext(),title,body.get("place"),
                    body.get("when"),body.get("fee"),body.get("eName"));
        }
    }


        @Override
    public void onNewToken(@NonNull String s) {
            SharedPrefConfig preferences = new SharedPrefConfig(this);
            if(preferences.readLoggedUser()!= null) {
                String token = preferences.readLoggedUser().getToken();
                FirebaseMessaging.getInstance().subscribeToTopic(token)
                        .addOnCompleteListener(x -> Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG));
            }else{
                FirebaseMessaging.getInstance().subscribeToTopic(s)
                        .addOnCompleteListener(x -> Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG));
            }
    }

}
