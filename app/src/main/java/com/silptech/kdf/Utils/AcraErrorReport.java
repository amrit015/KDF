package com.silptech.kdf.Utils;

import android.app.Application;

import com.silptech.kdf.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * This class is used to mail the crash log of the application.
 */

@ReportsCrashes(mailTo = "silptechnepal@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)


public class AcraErrorReport extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this); //comment this line to disable
    }
}
