package com.sgenterprises;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/**
 * Created by harmis on 27/12/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
   //     {"M":{"vendorId":"223","requestId":102,"m":"Hello , for Medical Service Products."}}

        // Check if message contains a notification payload.

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload:" + remoteMessage.getData().toString());


           // {"success":"false","msg":"no orders found."}

            //{"M":{"nType":"1","id":"34","m":"Newnormaluser comment on your kipanga John update post","subtype":"4"}}
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, "json ==: " + json.toString());


                String _id = "";
                if (json.has("success")) {
                    _id = json.getString("success");
                }

                String message = "";
                if (json.has("msg")) {
                    message = json.getString("msg");
                }


                PushNotification.notify(this, message, (int) System.currentTimeMillis());


            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }


    }


}