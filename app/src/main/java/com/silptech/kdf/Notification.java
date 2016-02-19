package com.silptech.kdf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;

/**
 * This is an activity for getting and displaying the new notification.
 * Here the message and author are taken as per Pushbots key and date and time is taken from system.
 */
public class Notification extends AppCompatActivity {

    public final static String TAG = ".Notification";
    String message_string, author_string, date_string;
    TextView newNoticeMessage;
    TextView newNoticeAuthor;
    TextView newNoticeDate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_notification);
        newNoticeMessage = (TextView) findViewById(R.id.new_message);
        newNoticeAuthor = (TextView) findViewById(R.id.new_author);
        newNoticeDate = (TextView) findViewById(R.id.new_date);
        //getting notification bundles and displaying in textview if both message and key "author" have been sent from Pushbots
        Bundle extras = this.getIntent().getExtras();
        if (null != extras && this.getIntent().getExtras().containsKey("message") && this.getIntent().getExtras().containsKey("author")) {
            message_string = extras.getString("message").toString();
            author_string = extras.getString("author").toString();
            date_string = String.valueOf(Calendar.getInstance().getTime());

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

