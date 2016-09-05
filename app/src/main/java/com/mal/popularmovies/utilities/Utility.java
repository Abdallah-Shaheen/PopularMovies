package com.mal.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AbdAllah Shaheen on 9/3/2016.
 */
public class Utility {
    public static Boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
