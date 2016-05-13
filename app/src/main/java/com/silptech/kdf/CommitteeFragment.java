package com.silptech.kdf;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.silptech.kdf.Utils.Log;

/**
 * Created by Amrit on 3/12/2016.
 * Fragment for Committee drawer. Tabs are initialized here.
 */
public class CommitteeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "CommitteeFragment";
    ViewPager viewPager;
    CommitteeTabsPagerAdapter committeeTabsPagerAdapter;
    String[] committeeTab;
    Context context;
    LinearLayout loginLayout;
    LinearLayout tabsLayout;
    EditText getUsername, getPassword;
    Button logIn;
    String insertedUsername;
    String insertedPassword;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    PagerSlidingTabStrip tabs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_committee, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.pager_tabs);
        tabsLayout = (LinearLayout) rootView.findViewById(R.id.tabs_layout);
        //getting committee tab titles from string-array
        committeeTab = getActivity().getResources().getStringArray(R.array.CommitteeTabs);
        loginLayout = (LinearLayout) rootView.findViewById(R.id.login_layout);
        getUsername = (EditText) rootView.findViewById(R.id.username);
        getPassword = (EditText) rootView.findViewById(R.id.password);
        logIn = (Button) rootView.findViewById(R.id.login);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                login();
            }
        });

        //creating childfragment for tabs inside fragment drawers
        committeeTabsPagerAdapter = new CommitteeTabsPagerAdapter(getChildFragmentManager(), context);
        viewPager.setAdapter(committeeTabsPagerAdapter);
        // tabs.setShouldExpand(true); // expand tabs to fill parent
        // Bind the tabs to the ViewPager
        tabs.setViewPager(viewPager);

        //the system keeps 3 page instance on either side on memory
        viewPager.setOffscreenPageLimit(3);
        //setting the default tab
        viewPager.setCurrentItem(0);
        return rootView;
    }

    private void login() {
        //Getting values from edit text
        insertedUsername = getUsername.getText().toString();
        insertedPassword = getPassword.getText().toString();

        if (insertedUsername.equals("") || insertedPassword.equals("")) {
            Toast.makeText(getActivity(), "Empty username or code", Toast.LENGTH_SHORT).show();
        } else {
            if (insertedUsername.equals("kdfadmin") && insertedPassword.equals("kdfauth123")) {
                loginLayout.setVisibility(View.GONE);
                tabsLayout.setVisibility(View.VISIBLE);

                //Creating a shared preference
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //Adding values to editor
                editor.putBoolean("loggedIn", true);
                //Saving values to editor
                editor.commit();
            } else {
                Toast.makeText(getActivity(), "Invalid username or code", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // tabs initialization adapter
    private class CommitteeTabsPagerAdapter extends FragmentStatePagerAdapter {
        Context context;

        public CommitteeTabsPagerAdapter(FragmentManager childFragmentManager, Context context) {
            super(childFragmentManager);
            this.context = context;
        }

        //no of tabs
        @Override
        public int getCount() {
            return 3;
        }

        // setting childfragment
        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "selected tab index is :" + position);
            switch (position) {
                case 0:
                    return new CommitteeKendriyaFragment();
                case 1:
                    return new CommitteeSallahkarFragment();
                case 2:
                    return new CommitteeTankiSinwariFragment();
            }
            return null;
        }

        public CharSequence getPageTitle(int position) {
            //setting tabs title
            switch (position) {
                case 0:
                    return committeeTab[0];
                case 1:
                    return committeeTab[1];
                case 2:
                    return committeeTab[2];
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences1.getBoolean("loggedIn", false);

        //If we will get true
        if (loggedIn) {
            // showing the data
            loginLayout.setVisibility(View.GONE);
            tabsLayout.setVisibility(View.VISIBLE);
        }
    }
}
