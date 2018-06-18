package com.solarabehety.intivechallenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by Sol Arabehety on 6/18/2018.
 */
public class Utils {
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()) {
            Log.i("checkInternetConnection", "Internet Connection Present");
            return true;
        } else {
            Log.e("checkInternetConnection", "No Internet connection");
            return false;
        }
    }

}
