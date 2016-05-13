package com.silptech.kdf;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Amrit on 5/13/2016.
 */
public class FeaturedAppsFragment extends android.support.v4.app.Fragment {

    TextView playstoreNE;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featuredapps, container, false);
        playstoreNE = (TextView) view.findViewById(R.id.navigate_playstore_NE);
        playstoreNE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.silptech.nepalientertainment");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?" +
                            "id=com.silptech.nepalientertainment")));
                }
            }
        });
        return view;
    }
}
