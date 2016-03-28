package com.silptech.kdf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Amrit on 2/9/2016.
 * Splash Screen for displaying when the app is opened
 */
public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1500;  //splash screen timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //neeeded to restore the app after pressing home button
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_screen_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent main = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(main);
                // closes activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}