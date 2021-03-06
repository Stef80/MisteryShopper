package com.example.misteryshopper.utils.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.MyApplication;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;


public class NotificationHandler {


    private static int notId;
    private static NotificationManagerCompat notificationManager;

   public static void displayNotificationShopper(Context context, String title, String place, String when, String fee, String eName, String id, String hId, String uriImg, int notificationId) {

        notId = notificationId;
       RemoteViews collapsedView = new RemoteViews(context.getPackageName(),R.layout.notification_shopper_layout);
        collapsedView.setTextViewText(R.id.content_title_collapsed,title);
        collapsedView.setTextViewText(R.id.notification_ename_collapsed,eName);
        collapsedView.setTextViewText(R.id.timestamp,DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

       RemoteViews expandedView = new RemoteViews(context.getPackageName(),R.layout.notification_shopper_layout_expanse);
       expandedView.setTextViewText(R.id.content_title_expanded,title);
       expandedView.setTextViewText(R.id.notification_ename,eName);
       expandedView.setTextViewText(R.id.notification_place,place);
       expandedView.setTextViewText(R.id.notification_when,when);
       expandedView.setTextViewText(R.id.notification_fee,fee);
 /*      if (uriImg != null) {
           Log.i(TAG, "displayNotificationShopper: imageUri : "+ uriImg);
           Log.i(TAG, "displayNotificationShopper: Uri: " + Uri.parse(uriImg).toString());
           expandedView.setImageViewUri(R.id.notification_img,Uri.parse(uriImg));
       }*/
       expandedView.setTextViewText(R.id.timestamp_expanded,DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

       Intent acceptIntent = new Intent(context, NotificationIntentHandler.class);
       acceptIntent.setAction("accept");
       acceptIntent.putExtra("id",id);
       acceptIntent.putExtra("hId",hId);
       expandedView.setOnClickPendingIntent(R.id.accept_button,PendingIntent.getService(context,0,acceptIntent,PendingIntent.FLAG_UPDATE_CURRENT));

       Intent declineIntent = new Intent(context,NotificationIntentHandler.class);
       declineIntent.setAction("decline");
       declineIntent.putExtra("id",id);
       declineIntent.putExtra("hId",hId);
       expandedView.setOnClickPendingIntent(R.id.decline_button,PendingIntent.getService(context,1,declineIntent,PendingIntent.FLAG_UPDATE_CURRENT));

       Intent showIntent = new Intent(context,NotificationIntentHandler.class);
       showIntent.setAction("show");
       showIntent.putExtra("id",id);
       showIntent.putExtra("hId",hId);
       showIntent.putExtra("address", place);
       expandedView.setOnClickPendingIntent(R.id.show_button,PendingIntent.getService(context,2,showIntent,PendingIntent.FLAG_UPDATE_CURRENT));
       NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.PRIMARY_CHANNEL_ID)
               .setSmallIcon(R.drawable.i_notifiation)
               .setContentTitle(title)
               .setCustomHeadsUpContentView(collapsedView)
               .setCustomContentView(collapsedView)
               .setCustomBigContentView(expandedView)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setAutoCancel(true);
             //  .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
       Notification notification = builder.build();
       Log.i(TAG, "displayNotificationShopper:  notification id: " + notificationId);
       notificationManager.notify(notId,notification);
       Handler handler = new Handler(Looper.getMainLooper());
       handler.post(new Runnable() {
           @Override
           public void run() {
               if(uriImg != null)
               Picasso.get().load(uriImg).into(expandedView,R.id.notification_img,notId,notification);
           }
       });


   }

    public static void displayNotificationEmployer(Context context, String title, String sName, String outcome, int notificationId) {
       notId = notificationId;
        RemoteViews customView = new RemoteViews(context.getPackageName(),R.layout.notification_employer_layout);
         customView.setTextViewText(R.id.title_response,title);
         customView.setTextViewText(R.id.notification_sname,sName);
         customView.setTextViewText(R.id.notification_response,outcome);
         customView.setTextViewText(R.id.timestamp_employer,DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

           Intent intent = new Intent(context,NotificationReciver.class);
           intent.putExtra("name", sName);
           intent.putExtra("outcome", outcome);
           intent.setAction("showmessage");
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.i_notifiation)
                .setCustomContentView(customView)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
               // .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        Log.i(TAG, "displayNotificationEmployer: notification id: " + notificationId);
        notificationManager.notify(notificationId,builder.build());
    }


    public static void cancelNotification(){
       notificationManager.cancel(getNotId());
    }




    public static void createNotificationChannel(Context context) {
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           NotificationChannel notificationChannel = new NotificationChannel(MyApplication.PRIMARY_CHANNEL_ID,
                   "channel 1", NotificationManager.IMPORTANCE_HIGH);
           notificationChannel.enableLights(true);
           notificationChannel.setLightColor(Color.RED);
           notificationChannel.enableVibration(true);
           notificationChannel.setDescription("Notification from Mystery Shopper");
           notificationManager = NotificationManagerCompat.from(context);
           notificationManager.createNotificationChannel(notificationChannel);
       }
    }


    public static int getNotId() {
        return notId;
    }

}
