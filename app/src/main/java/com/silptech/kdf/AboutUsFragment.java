package com.silptech.kdf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This fragment displays the corresponding layout.
 * The organisation info are displayed here.
 */
public class AboutUsFragment extends android.support.v4.app.Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_about_us, container, false);


        return view;
    }
}
