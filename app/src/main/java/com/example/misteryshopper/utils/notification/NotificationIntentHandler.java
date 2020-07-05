package com.example.misteryshopper.utils.notification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.R;
import com.example.misteryshopper.datbase.DBHelper;
import com.example.misteryshopper.datbase.impl.FirebaseDBHelper;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.SharedPrefConfig;

import java.util.List;


public class NotificationIntentHandler extends IntentService {

    private DBHelper mDBHelper = FirebaseDBHelper.getInstance();

    public NotificationIntentHandler() {
        super("notificationIntentHandler");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPrefConfig config = new SharedPrefConfig(getApplicationContext());
        ShopperModel shopperModel = (ShopperModel) config.readLoggedUser();
        String name = shopperModel.getName();
        String surname = shopperModel.getSurname();
        Bundle extras = intent.getExtras();
        mDBHelper.getTokenById(extras.getString("id"), new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<?> obj, List<String> keys) {
                String token = (String) obj.get(0);

                switch (intent.getAction()) {
                    case "accept":
                        Handler acceptHandler = new Handler(Looper.getMainLooper());
                        acceptHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                MessageCreationService.buildMessage(getApplicationContext(),
                                        token, getApplicationContext().getString(R.string.response_notification), name + " " + surname, "accepted");
                            }
                        });
                        break;
                    case "decline":
                        Handler declineHandler = new Handler(Looper.getMainLooper());
                        declineHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                MessageCreationService.buildMessage(getApplicationContext(),
                                        token,getApplicationContext().getString(R.string.response_notification), name + " " + surname, "declined");
                            }
                        });
                        break;
                    case "show":
                        Handler showHandler = new Handler(Looper.getMainLooper());
                        showHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent go = new Intent(getApplicationContext(), MainActivity.class);
                                go.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(go);
                            }
                        });
                        break;
                }
            }
        });


    }
}
