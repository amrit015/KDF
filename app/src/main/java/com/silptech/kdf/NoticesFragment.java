package com.silptech.kdf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.silptech.kdf.Utils.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.silptech.kdf.Utils.CacheNotification;
import com.silptech.kdf.Utils.InternetCheck;
import com.silptech.kdf.Utils.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amrit on 2/4/2016.
 * This fragment displays the notices in cardview after parsing xml from the blog.
 */
public class NoticesFragment extends android.support.v4.app.Fragment {

    private static final String KEY_AUTHOR = "Silptech";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout progressBar;
    public final static String TAG = "NoticesFragment";
    Context context;
    private ArrayList<CacheModule> noticesArray;
    String filename, message_string, author_string, date_string;
    String xfileName = "cache_file";
    String notification_cache;
    File folder;
    int i;

    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_TITLE = "title";
    static final String KEY_PUBDATE = "pubDate";
    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    String url = "http://kdfpost.blogspot.com/feeds/posts/default?alt=rss";
    NoticesParser noticesParser = new NoticesParser();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notices, container, false);
        // initialising recyclerview and layoutmanager for adding list to cards
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        progressBar = (LinearLayout) v.findViewById(R.id.headerProgress);
        mLayoutManager = new LinearLayoutManager(context);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notices");
        folder.mkdirs();
        //executing xml parser on Asyntask method
        new NoticesParser().execute(url);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class NoticesParser extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading Notices");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            if (getActivity() != null && !noticesParser.isCancelled()) {
                //checking if internet is available and only parsing when avialable
                if (InternetCheck.hasInternet(getActivity())) {
                    XMLParser parser = new XMLParser();
                    String xml = parser.getXmlFromUrl(url);
                    int statusCodeXML = XMLParser.statusCode;
                    Log.i(TAG, " status code : " + statusCodeXML);
                    if (statusCodeXML == 200) {
                        Log.i(TAG, "Coming from status code 200");
                        int savedNodeLength = 0;
                        Document doc = parser.getDomElement(xml); // getting DOM element
                        NodeList nl = doc.getElementsByTagName(KEY_ITEM);
                        if (folder.list().length > 0) {
                            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                            savedNodeLength = sharedPreferences.getInt("last_xml_node", 0);
                        }
                        // looping through all item nodes <item>
                        Log.i(TAG, "nl ko length:" + nl.getLength());
                        Log.i(TAG, "sharePref ko length:" + savedNodeLength);
                        //the new input data will always have the tag from 0 i.e. ascending order
                        for (int i = 0; i < (nl.getLength() - savedNodeLength); i++) {
                            int y = folder.list().length;
                            filename = xfileName + y;
                            //save value on shared Preference and only execute y > nl.getLength() after 1st execution
                            Element e = (Element) nl.item(i);
                            // adding each child node to HashMap key => value
                            map.put(KEY_ITEM, parser.getValue(e, KEY_ITEM));
                            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
                            map.put(KEY_PUBDATE, parser.getValue(e, KEY_PUBDATE));
                            // adding HashList to ArrayList
                            menuItems.add(map);
//                String a = menuItems.get(i).get(KEY_ITEM);
                            String b = menuItems.get(i).get(KEY_TITLE);
                            String c = menuItems.get(i).get(KEY_PUBDATE);

                            // adding HashList to ArrayList
                            folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notices");
                            folder.mkdirs();
                            Log.i(TAG, "aayeko kuro : " + b);
                            Log.i(TAG, "aayeko kuro : " + c);
                            String toFile = ("#" + b + "##" + KEY_AUTHOR + "###" + c + "####");
                            Log.i(TAG, "toFile length : " + toFile.length());
                            CacheNotification.writeFile(filename, toFile, folder);
                            SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                            editor.putInt("last_xml_node", nl.getLength());
                            editor.apply();
                        }
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Internet Connection is required to check for new notice", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Loading cached notices", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void success) {
            if (getActivity() != null) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                try {
                    //repopulating with updated list item
                    mAdapter = new MyRecyclerViewAdapter(getDataSet());
                    mRecyclerView.setAdapter(mAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        noticesParser.cancel(true);
    }

    public ArrayList<CacheModule> getDataSet() throws IOException {
        noticesArray = new ArrayList();

        for (int i = 0; i < folder.list().length; i++) {
            notification_cache = CacheNotification.readFile((xfileName + i), folder);
            if (notification_cache != "") {
                int a = notification_cache.indexOf("#");
                int b = notification_cache.indexOf("##");
                int c = notification_cache.indexOf("###");
                int d = notification_cache.indexOf("####");
                Log.i(TAG, "here :" + notification_cache.substring(a + 1, b));
                message_string = notification_cache.substring(a + 1, b);
                author_string = notification_cache.substring(b + 2, c);
                date_string = notification_cache.substring(c + 3, d);
                CacheModule cacheModule = new CacheModule();
                cacheModule.setMessage(message_string);
                cacheModule.setAuthor(author_string);
                cacheModule.setDate(date_string);
                noticesArray.add(cacheModule);
            }
        }
        Log.i(TAG, "folder length :" + folder.list().length);
        return noticesArray;
    }
}
