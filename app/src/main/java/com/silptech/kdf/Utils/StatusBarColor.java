package com.silptech.kdf.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.silptech.kdf.R;

/**
 * This is the utility class which changes the status bar color on lollipop and post-lollipop devices
 */
public class StatusBarColor {
    @SuppressLint("NewApi")
    public static void StatusBarColorChange(Activity activity) {

        //changing the color of the status bar to match the app actionbar color
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.material_palette_dark));
    }
}
