package com.silptech.kdf;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pushbots.push.Pushbots;
import com.silptech.kdf.Utils.Log;
import com.silptech.kdf.Utils.StatusBarColor;

/*
    THis is the main activity which opens after SPlash Screen. Here the navigation drawer is defined and populated
    and each corresponding drawer item click activity is defined and inflated on the fragment of this Activity.
 */

public class MainActivity extends AppCompatActivity {

    // defining variables for navigation drawer
    private DrawerLayout drawerLayout;
    private RelativeLayout relativeLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    DrawerTitleAdapter adapter;

    // title drawer
    String[] drawerItemsArray;

    // TypedArray for displaying icons let to navigation drawer titles
    TypedArray drawerIconsArray;

    // for navigation drawer headings
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private static final String TAG = "MainActivity";
    Fragment fm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pushbots library initialization
        Pushbots.sharedInstance().init(this);
        //setting up titles for drawers
        mTitle = mDrawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.left_drawer);
        drawerList = (ListView) findViewById(R.id.drawerList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // changing the color of the status bar to match the app actionbar color
        // implementing status bar color change only on lollipop devices
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarColor.StatusBarColorChange(this);
        }
        //defining drawer items
        /*  drawerItemsArray = new String[]{"Home", "Notices", "Contact","Memo","About Us"}; */
        drawerItemsArray = getResources().getStringArray(R.array.NavItems);

        // defining drawers icons
        drawerIconsArray = getResources().obtainTypedArray(R.array.NavIcons);

        //set Adapter to the left drawer and passing layout for text
        adapter = new DrawerTitleAdapter(this, R.layout.adapter_title_drawer, drawerItemsArray, drawerIconsArray);
        drawerList.setAdapter(adapter);

        //set up click listener for the drawer items
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar App icon to behave as action to toggle navigation drawer
        // showing the navigation drawer button just like menu to open/close the drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // change action bar title
                toolbar.setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View view) {
                // change action bar title
                toolbar.setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };

        //selecting default navigation drawer on startup
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    //onclick item listener for navigation drawer items
    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            view.setBackgroundColor(getResources().getColor(R.color.material_palette));
//            view.setSelected(true);
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        //using fragments for drawers contents
        fm = null;
        switch (position) {
            case 0:
                fm = new HomeFragment();
                break;
            case 1:
                fm = new NoticesFragment();
                break;
            case 2:
                fm = new MembersFragment();
                break;
            case 3:
                fm = new NotesFragment();
                break;
            case 4:
                fm = new CommitteeFragment();
                break;
            case 5:
                fm = new AboutUsFragment();
                break;

            default:
                break;
        }
        if (fm != null) {
            // setting the fragment to be the current view
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, fm)
                    .commit();
            // updating selected item and title, then closing the drawer
            drawerList.setItemChecked(position, true);
            setTitle(drawerItemsArray[position]);
            drawerLayout.closeDrawer(relativeLayout);
        }
    }

    /* When using the ActionBarDrawerToggle, we must call onPostCreate() and onConfigurationChanged() */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // syncing the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // passing any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         /*
         * The action bar - up action should open or close the drawer.
		 * drawerToggle will take care of this.
		 */
        if (drawerToggle.onOptionsItemSelected(item)) {
            // handles navigation button click
            return true;
        }

        // using switch statement to implement actions for each menuItem
        switch (item.getItemId()) {
            case R.id.menu_developers:
                Intent intent = new Intent(MainActivity.this, DevelopersActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_rate_us:
                RateUs();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void RateUs() {
        //parsing url by getting packageName
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                    + getApplicationContext().getPackageName())));
        }
        Log.i(TAG, "Package Name : " + getApplicationContext().getPackageName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}