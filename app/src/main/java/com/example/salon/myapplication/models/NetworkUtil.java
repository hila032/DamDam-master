package com.example.salon.myapplication.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return "Wifi enabled";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "Mobile data enabled";
            }

        }
        return "No internet is available";
    }
}