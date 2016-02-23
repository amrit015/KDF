package com.silptech.kdf.Utils;

/**
 * Created by Amrit on 2/23/2016.
 * Custom Log class.
 */
public class Log {
    private static boolean isLogEnabled = true;     // make false to disable
    public static void i(String TAG, String message) {

        if (isLogEnabled) {
            android.util.Log.i(TAG, message);
        }
    }

    public static void e(String TAG, String message) {

        if (isLogEnabled) {
            android.util.Log.e(TAG, message);
        }
    }

    public static void d(String TAG, String message) {

        if (isLogEnabled) {
            android.util.Log.d(TAG, message);
        }
    }
}
