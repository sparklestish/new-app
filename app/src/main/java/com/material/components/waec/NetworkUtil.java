package com.material.components.waec;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = " ";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = " ";
                return status;
            }
        } else {
            status = "No internet Connection!! Please ensure you have an active internet connection";
            return status;
        }
        return status;
    }
}