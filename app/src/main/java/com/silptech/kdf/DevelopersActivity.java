package com.silptech.kdf;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silptech.kdf.Utils.CallDial;
import com.silptech.kdf.Utils.FacebookOpen;
import com.silptech.kdf.Utils.MailTo;

/**
 * Created by Amrit on 2/14/2016.
 * This class displays the developers name and info.
 * The phone dialing and emailing are also provided.
 */
public class DevelopersActivity extends AppCompatActivity implements View.OnClickListener {

    TextView developerA, developerB, developerC;
    String devA, devB, devC; //name resources from string
    ImageView devPhoneA, devPhoneB, devPhoneC;
    String phA, phB, phC; //phone resources from string
    Context context;
    LinearLayout silptechLayout;
    LinearLayout silptechFacebook;
    String silptech_email;
    String facebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        //initializing
        developerA = (TextView) findViewById(R.id.developer_a_name);
        developerB = (TextView) findViewById(R.id.developer_b_name);
        developerC = (TextView) findViewById(R.id.developer_c_name);
        devPhoneC = (ImageView) findViewById(R.id.developer_b_phone);
        silptechLayout = (LinearLayout) findViewById(R.id.silptech_layout);
        silptechFacebook = (LinearLayout) findViewById(R.id.company_facebook_layout);

        //getting string
        devA = getResources().getString(R.string.developer_a);
        devB = getResources().getString(R.string.developer_b);
        devC = getResources().getString(R.string.developer_c);
        phC = getResources().getString(R.string.developer_phone_c);
        silptech_email = getResources().getString(R.string.silptech_email);
        facebookId = getResources().getString(R.string.silptech_facebook_pageId);

        //setting textview on names
        developerA.setText(devA);
        developerB.setText(devB);
        developerC.setText(devC);

        //onclick email and phone
        devPhoneC.setOnClickListener(this);
        silptechLayout.setOnClickListener(this);
        silptechFacebook.setOnClickListener(this);

        //seting visibility of devPhoneB
        devPhoneC.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.silptech_layout:
                MailTo.SendEmail(DevelopersActivity.this, silptech_email);
                break;
            case R.id.developer_b_phone:
                CallDial.PhoneDialer(DevelopersActivity.this, phC);
                break;
            case R.id.company_facebook_layout:
                FacebookOpen.receiveFacebookPageId(getApplicationContext(), facebookId);
                break;
            default:
                break;
        }
    }
}
