package com.silptech.kdf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.silptech.kdf.Utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Amrit on 2/4/2016.
 * This fragment is used to access the databse and inflate the layout with names,address,phone and so on.
 * Listview is used
 * On search, intent is used to go to SearchQueryActivity
 */
public class MembersFragment extends android.support.v4.app.Fragment {

    private final static String TAG = "MembersFragment";
    DatabaseHelperMembers dbHelper = null;
    Context context;
    ListView listView;
    ArrayList<MembersModule> memberList = new ArrayList<MembersModule>();
    MembersAdapter adapter;
    File folder;
    InputStream assets_path;
    int previousPosition = 0;
    int newPosition;
    LinearLayout loginLayout;
    EditText getUsername, getPassword;
    Button logIn;
    String insertedUsername;
    String insertedPassword;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        getUsername = (EditText) view.findViewById(R.id.username);
        getPassword = (EditText) view.findViewById(R.id.password);
        logIn = (Button) view.findViewById(R.id.login);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                login();
            }

        });
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Database");
        folder.mkdirs();

        //creating database object and defining its path
        try {
            assets_path = getActivity().getAssets().open("database.db");
            dbHelper = new DatabaseHelperMembers(context, folder.toString(), assets_path);
            Log.i(TAG, " from contacts :" + folder.toString());
            dbHelper.prepareDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return view;
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
                listView.setVisibility(View.VISIBLE);

                //Creating a shared preference
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //Adding values to editor
                editor.putBoolean("loggedIn", true);
                //Saving values to editor
                editor.commit();

                // accessing data
                getData();
            } else {
                Toast.makeText(getActivity(), "Invalid username or code", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getData() {
        //getting data from data in asset folder and inserting in array
        ArrayList<MembersModule> list = dbHelper.getMembersModule();
        for (int i = 0; i < list.size(); i++) {

            int id = list.get(i).getId();
            String name = list.get(i).getName();
            String address = list.get(i).getAddress();
            String phone = list.get(i).getPhone();
            int amount = list.get(i).getAmount();

            MembersModule membersModule = new MembersModule();
            membersModule.setId(id);
            membersModule.setName(name);
            membersModule.setAddress(address);
            membersModule.setPhone(phone);
            membersModule.setAmount(amount);
            memberList.add(membersModule);
        }
        adapter = new MembersAdapter(getActivity(), memberList);
        listView.setAdapter(adapter);
    }

    //search functionality(menu/toolbar)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_search_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_actionbar:
                Search();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Search() {
        Intent intent = new Intent(getActivity(), SeachQueryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        previousPosition = sharedPreferences.getInt("last_position", 0);
        Log.i(TAG, "last position :" + previousPosition);
        //returning listview to scroll to the position saved previously while closing the fragment
        listView.setSelectionFromTop(previousPosition, 0);

        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences1.getBoolean("loggedIn", false);

        //If we will get true
        if (loggedIn) {
            // showing the data
            loginLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            // accessing data
            getData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        newPosition = listView.getFirstVisiblePosition();
        SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putInt("last_position", newPosition);
        editor.apply();
        Log.i(TAG, "new position :" + newPosition);
    }
}
