package com.silptech.kdf;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silptech.kdf.Utils.CallDial;
import com.silptech.kdf.Utils.EmailDevelopers;

/**
 * Created by Amrit on 2/14/2016.
 */
public class DevelopersActivity extends AppCompatActivity implements View.OnClickListener {

    TextView developerA, developerB, developerC;
    String devA, devB, devC; //name resources from string
    ImageView devPhoneA, devPhoneB, devPhoneC;
    String phA, phB, phC; //phone resources from string
    ImageView devEmailA, devEmailB, devEmailC;
    String emA, emB, emC; //email resources from string
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        //initializing
        developerA = (TextView) findViewById(R.id.developer_a_name);
        developerB = (TextView) findViewById(R.id.developer_b_name);
        developerC = (TextView) findViewById(R.id.developer_c_name);
        devPhoneA = (ImageView) findViewById(R.id.developer_a_phone);
        devPhoneB = (ImageView) findViewById(R.id.developer_b_phone);
        devPhoneC = (ImageView) findViewById(R.id.developer_c_phone);
        devEmailA = (ImageView) findViewById(R.id.developer_a_email);
        devEmailB = (ImageView) findViewById(R.id.developer_b_email);
        devEmailC = (ImageView) findViewById(R.id.developer_c_email);

        //getting string
        devA = getResources().getString(R.string.developer_a);
        devB = getResources().getString(R.string.developer_b);
        devC = getResources().getString(R.string.developer_c);
        phA = getResources().getString(R.string.developer_phone_a);
        phB = getResources().getString(R.string.developer_phone_b);
        phC = getResources().getString(R.string.developer_phone_c);
        emA = getResources().getString(R.string.developer_email_a);
        emB = getResources().getString(R.string.developer_email_b);
        emC = getResources().getString(R.string.developer_email_c);

        //setting textview on names
        developerA.setText(devA);
        developerB.setText(devB);
        developerC.setText(devC);

        //onclick email and phone
        devPhoneC.setOnClickListener(this);
        devEmailA.setOnClickListener(this);
        devEmailB.setOnClickListener(this);
        devEmailC.setOnClickListener(this);

        //seting visibility of devPhoneC
        devPhoneC.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.developer_a_email:
                EmailDevelopers.SendEmail(DevelopersActivity.this, emA);
                break;
            case R.id.developer_b_email:
                EmailDevelopers.SendEmail(DevelopersActivity.this, emB);
                break;
            case R.id.developer_c_email:
                EmailDevelopers.SendEmail(DevelopersActivity.this, emC);
                break;
            case R.id.developer_c_phone:
                CallDial.PhoneDialer(DevelopersActivity.this, phC);
                break;
            default:
                break;
        }
    }
}
