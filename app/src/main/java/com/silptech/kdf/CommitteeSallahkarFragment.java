package com.silptech.kdf;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.silptech.kdf.Utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Amrit on 3/12/2016.
 * ChildFragment for Sallahkar Samiti
 */
public class CommitteeSallahkarFragment extends Fragment {

    DatabaseHelperCommittee dbHelper;
    Context context;
    File folder;
    InputStream assets_path;
    private static String DATABASE_NAME;
    private static String DATABASE_TABLE;
    ListView listView;
    CommitteeAdapter adapter;
    ArrayList<CommitteeModule> committeeList = new ArrayList<CommitteeModule>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        getData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //defining database path to save in storage
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Database");
        folder.mkdirs();
        DATABASE_NAME = "sallakardata.db";
        DATABASE_TABLE = "sallakarData";

        //creating database object and defining its path
        try {
            assets_path = getActivity().getAssets().open(DATABASE_NAME);
            dbHelper = new DatabaseHelperCommittee(context, folder.toString(), DATABASE_NAME,DATABASE_TABLE, assets_path);
            dbHelper.copyDataBase();
        } catch (IOException e) {
            Log.e("CommitteeSallakar", e.getMessage());
        }
    }

    private void getData() {
        ArrayList<CommitteeModule> list = dbHelper.getCommitteeModule();

        //getting data and inserting in array
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            String post = list.get(i).getPost();
            String phone = list.get(i).getPhone();
            String cell = list.get(i).getCell();

            CommitteeModule committeeModule = new CommitteeModule();
            committeeModule.setName(name);
            committeeModule.setPost(post);
            committeeModule.setPhone(phone);
            committeeModule.setCell(cell);
            committeeList.add(committeeModule);
        }
        adapter = new CommitteeAdapter(getActivity(), committeeList);
        listView.setAdapter(adapter);
    }
}
