package com.silptech.kdf.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Amrit on 2/15/2016.
 */
public class MailTo {

    static Context myContext;

    public static void SendEmail(final Context context, String emailDev) {
        final String emailId = emailDev;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        try {
                            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "");
                            intent.putExtra(Intent.EXTRA_TEXT, "");
                            intent.setData(Uri.parse("mailto:" + emailId)); // or just "mailto:" for blank
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                            context.startActivity(intent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Email " + emailId + "?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}

