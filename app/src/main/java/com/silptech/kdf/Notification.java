package com.silptech.kdf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.silptech.kdf.Utils.CacheNotification;

import java.io.File;

/**
 * Created by Amrit on 2/12/2016.
 */
public class Notification extends AppCompatActivity {

    public final static String TAG = ".Notification";
    String filename, message_string, author_string, date_string;
    String stored_message_string = "", stored_author_string = "", stored_date_string = "";
    String xfileName = "cache_file";
    File folder;
    int i;
    TextView newNoticeMessage;
    TextView newNoticeAuthor;
    TextView newNoticeDate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_notification);
        newNoticeMessage = (TextView) findViewById(R.id.new_message);
        newNoticeAuthor = (TextView) findViewById(R.id.new_author);
        newNoticeDate = (TextView) findViewById(R.id.new_date);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notices");
        folder.mkdirs();
        Bundle extras = this.getIntent().getExtras();
//        Log.i(TAG, "LOG :" + extras);
        if (null != extras && this.getIntent().getExtras().containsKey("message") && this.getIntent().getExtras().containsKey("author")) {
            message_string = extras.getString("message").toString();
            author_string = extras.getString("author").toString();
            date_string = extras.getString("date").toString();

            if ((message_string.equals(stored_message_string)) && (author_string.equals(stored_message_string)) && (date_string.equals(stored_date_string))) {
                Log.i(TAG, "No incoming notification, breaking the loop");
            } else {
                if (folder.exists()) {
                    i = folder.list().length;
                    Log.i(TAG, "folder exist length :" + i);
                } else {
                    i = 0;
                }
                filename = xfileName + i;

                CacheNotification.writeFile(filename, ("#" + message_string + "##" + author_string + "###" + date_string + "####"), folder);
                Log.i(TAG, " tori :" + filename);
            }
            stored_message_string = message_string;
            stored_author_string = author_string;
            stored_date_string = date_string;

            newNoticeMessage.setText(message_string);
            newNoticeAuthor.setText(author_string);
            newNoticeDate.setText(date_string);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Notification.this, MainActivity.class);
        startActivity(intent);
    }
}

