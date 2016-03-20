package com.silptech.kdf.Utils;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Amrit on 2/15/2016.
 * Phone Dialing class
 */
public class CallDial {
    public static void PhoneDialer(final Context context, String phoneNumber) {
        //call dialer function
        final String phoneCall = phoneNumber;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        /*
                            no options.... default phone call
                         */
//                        try {
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel:" + phoneCall));
//                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//                            context.startActivity(callIntent);
//                        } catch (ActivityNotFoundException e) {
//                            Log.e("Dialing", "Call failed", e);
//                            Toast.makeText(context, "Call failed", Toast.LENGTH_LONG).show();
//                        }

                         /*
                            options.... viber, skype, phone
                         */
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneCall, null));
                        context.startActivity(intent);

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        //alertdialog to confirm
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Call " + phoneCall + "?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}
