package com.sgenterprises;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sgenterprises.Common.CommonSession;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences preferences=getSharedPreferences("FCM",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",refreshedToken);
        editor.commit();

        //SharedPreferenceHelper.initialize(this);
        //SharedPreferenceHelper.save(Keys.SharedPreferenceKeys.DEVICE_TOCKEN, refreshedToken);

        //Displaying token on logcat
        Log.e(TAG, "Refreshed token: " + refreshedToken);

    }

}