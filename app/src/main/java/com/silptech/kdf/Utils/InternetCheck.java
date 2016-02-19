package com.silptech.kdf.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * This class checks if the internet connection is available.
 * If internet is available, this class returns TRUE
 */
public class InternetCheck {

    static ConnectivityManager cManager;
    static android.net.NetworkInfo wifi, data;
    static Context internetContext;

    InternetCheck() {
    }

    public static boolean hasInternet(Context context) {
        cManager = (ConnectivityManager) context.getSystemService(internetContext.CONNECTIVITY_SERVICE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        data = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null & data != null) && (wifi.isConnected() || data.isConnected())
                && (wifi.isAvailable() || data.isAvailable())) {
            return true;
        } else {
            return false;
        }
    }
}
