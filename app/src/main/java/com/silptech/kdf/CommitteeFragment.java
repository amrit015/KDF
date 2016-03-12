package com.silptech.kdf;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.silptech.kdf.Utils.Log;

/**
 * Created by Amrit on 3/12/2016.
 */
public class CommitteeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "CommitteeFragment";
    ViewPager viewPager;
    CommitteeTabsPagerAdapter committeeTabsPagerAdapter;
    String[] committeeTab;
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_committee, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.pager_tabs);
        //getting committee tab titles from string-array
        committeeTab = getActivity().getResources().getStringArray(R.array.CommitteeTabs);

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
}
