package com.silptech.kdf.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by Amrit on 3/30/2016.
 */
public class FacebookOpen {
    static Context myContext;
    static String fbPageId;

    public static void receiveFacebookPageId(Context context, String padeId) {
        myContext = context;
        fbPageId = padeId;
        try {
            //trying to open page in facebook native app.
            String uri = "fb://page/" + fbPageId;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myContext.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            //if facebook native app isn't available, using browser.
            String uri = "http://touch.facebook.com/pages/x/" + fbPageId;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myContext.startActivity(i);
        }
    }
}
