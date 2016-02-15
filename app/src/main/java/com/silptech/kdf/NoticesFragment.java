package com.silptech.kdf;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silptech.kdf.Utils.CacheNotification;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amrit on 2/4/2016.
 */
public class NoticesFragment extends android.support.v4.app.Fragment {

    private static final String KEY_AUTHOR = "Silptech";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public final static String TAG = "NoticesFragment";
    Context context;
    private ArrayList<NoticesModule> noticesArray;
    String filename, message_string, author_string, date_string;
    String xfileName = "cache_file";
    String notification_cache;
    File folder;
    int i;
    String stored_message_string = "", stored_author_string = "", stored_date_string = "";
    String aa = "";
    String bb = "";
    String cc = "";

    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_TITLE = "title";
    static final String KEY_PUBDATE = "pubDate";
    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    String url = "http://kdfpost.blogspot.com/feeds/posts/default?alt=rss";
    String xml;
    NodeList nl;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notices, container, false);
        // initialising recyclerview,layoutmanager and adapter for adding list to cards
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notices");
        folder.mkdirs();
        mLayoutManager = new LinearLayoutManager(context);
        try {
            mAdapter = new MyRecyclerViewAdapter(getDataSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle extras = getActivity().getIntent().getExtras();
        if (null != extras && getActivity().getIntent().getExtras().containsKey("message") && getActivity().getIntent().getExtras().containsKey("author")) {
            message_string = extras.getString("message").toString();
            author_string = extras.getString("author").toString();
            date_string = extras.getString("date").toString();

            if ((message_string.equals(stored_message_string)) && (author_string.equals(stored_message_string)) && (date_string.equals(stored_date_string))) {
                Log.i(TAG, "No incoming notification, breaking the loop");
            } else {
                if (folder.exists()) {
                    i = folder.list().length;
                    Log.i(TAG, "folder exist length :" + i);
                } else {
                    i = 0;
                }
                filename = xfileName + i;

                CacheNotification.writeFile(filename, ("#" + message_string + "##" + author_string + "###" + date_string + "####"), folder);
                Log.i(TAG, " tori :" + filename);
            }
            stored_message_string = message_string;
            stored_author_string = author_string;
            stored_date_string = date_string;
        }
        new NoticesParser().execute(url);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public ArrayList<NoticesModule> getDataSet() throws IOException {
        noticesArray = new ArrayList();

        for (int i = folder.list().length - 1; i >= 0; i--) {
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
                NoticesModule noticesModule = new NoticesModule();
                noticesModule.setMessage(message_string);
                noticesModule.setAuthor(author_string);
                noticesModule.setDate(date_string);
                noticesArray.add(noticesModule);
            }
        }
        Log.i(TAG, "folder length :" + folder.list().length);
        return noticesArray;
    }

    private class NoticesParser extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            XMLParser parser = new XMLParser();
            String xml = parser.getXmlFromUrl(url);
            Document doc = parser.getDomElement(xml); // getting DOM element
            NodeList nl = doc.getElementsByTagName(KEY_ITEM);
// looping through all item nodes <item>
            Log.i(TAG, "nl ko length:" + nl.getLength());
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                Element e = (Element) nl.item(i);

                // adding each child node to HashMap key => value
                map.put(KEY_ITEM, parser.getValue(e, KEY_ITEM));
                map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
                map.put(KEY_PUBDATE, parser.getValue(e, KEY_PUBDATE));
// adding HashList to ArrayList
                menuItems.add(map);
                String a = menuItems.get(i).get(KEY_ITEM);
                String b = menuItems.get(i).get(KEY_TITLE);
                String c = menuItems.get(i).get(KEY_PUBDATE);

//                if (!a.equals(aa) && !(b.equals(bb) && !(c.equals(cc)))) {
                // adding HashList to ArrayList
                folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notices");
                folder.mkdirs();
                Log.i(TAG, "aayeko kuro : " + a);
                Log.i(TAG, "aayeko kuro : " + b);
                Log.i(TAG, "aayeko kuro : " + c);
                int y = folder.list().length;
                filename = xfileName + y;
                CacheNotification.writeFile(filename, ("#" + b + "##" + KEY_AUTHOR + "###" + c + "####"), folder);
//                }
                aa = a;
                bb = b;
                cc = c;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {

        }
    }
}
