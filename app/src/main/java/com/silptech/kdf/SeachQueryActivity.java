package com.silptech.kdf;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Amrit on 2/15/2016.
 */
public class SeachQueryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "SearchQueryActivity";
    ListView listView;
    DatabaseHelper dbHelper = null;
    ArrayList<MembersModule> memberList = new ArrayList<MembersModule>();
    ;
    CustomAdapter adapter;
    InputStream assets_path;
    File folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_listview);
        listView = (ListView) findViewById(R.id.listview);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Database");
        folder.mkdirs();
        try {
            assets_path = this.getAssets().open("database.db");
            dbHelper = new DatabaseHelper(getApplicationContext(), folder.toString(), assets_path);
            dbHelper.prepareDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_actionbar).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        searchView.onActionViewExpanded();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String queryObtained = query.toLowerCase().toString();
        Log.i(TAG, "Obtained Query : " + queryObtained);
        //hiding keyboard after the user enters query for search
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        listView.setAdapter(null);
        memberList.clear();
        getData(queryObtained);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void getData(String queryObtained) {
        String a = queryObtained;
        //getting data from data in asset folder and inserting in array
        ArrayList<MembersModule> list = dbHelper.getMembersModule();
        Log.i(TAG, "list size: " + list.size());

        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            String address = list.get(i).getAddress();
            int phone = list.get(i).getPhone();
            int amount = list.get(i).getAmount();
            String toSearch = name.replace(" ", "").toLowerCase();

            if (toSearch.contains(a)) {
                MembersModule membersModule = new MembersModule();
                membersModule.setId(id);
                membersModule.setName(name);
                membersModule.setAddress(address);
                membersModule.setPhone(phone);
                membersModule.setAmount(amount);
                memberList.add(membersModule);
                Log.i(TAG, "search list: " + memberList);
            }
        }
        adapter = new CustomAdapter(this, memberList);
        listView.setAdapter(adapter);
    }
}
