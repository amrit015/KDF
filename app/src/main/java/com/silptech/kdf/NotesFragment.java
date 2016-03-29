package com.silptech.kdf;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.silptech.kdf.Utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amrit on 2/4/2016.
 * This fragment displays the saved notes on the database and provides options such as EDIT,DELETE,SHARE through
 * the use of contextmenu on long click on the card items.
 */
public class NotesFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String TAG = "NotesFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context context;
    private ArrayList<CacheModule> memoArray = new ArrayList<>();
    File folder;
    DatabaseHelperNotes db;
    CacheModule cacheModule;
    EditText dialogNotesTitle;
    EditText dialogNotesContent;
    LinearLayout dialogUpdateLayout;
    TextView dialog_save;
    TextView dialog_cancel;
    Dialog dialog;
    String title;
    String notes;
    int position;
    //facebook share method initialization
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notes");
        folder.mkdirs();

        //facebook sdk initialization
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_button_add_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotesAddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        mLayoutManager = new LinearLayoutManager(context);
        //inflating recyclerview
        try {
            mAdapter = new MyRecyclerViewAdapter(getDataSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView.setOnCreateContextMenuListener(this);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //adding context menu on longclick to each cards
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Share via");
        menu.add(0, v.getId(), 0, "Share via Facebook");
        menu.add(0, v.getId(), 0, "Cancel");
    }

    //defining context menu actions
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        try {
            title = ((MyRecyclerViewAdapter) mRecyclerView.getAdapter()).getTitle();
            notes = ((MyRecyclerViewAdapter) mRecyclerView.getAdapter()).getNotes();
            position = ((MyRecyclerViewAdapter) mRecyclerView.getAdapter()).getPosition();
            Log.i(TAG, "title: " + title);
            Log.i(TAG, "note: " + notes);
            Log.i(TAG, "position: " + position);
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
            return super.onContextItemSelected(item);
        }
        if (item.getTitle() == "Edit") {
            editItem(title, notes);
        } else if (item.getTitle() == "Delete") {
            removeItem(title, position);
        } else if (item.getTitle() == "Share via") {
            shareItem(title, notes);
        } else if (item.getTitle() == "Share via Facebook") {
            shareItemFacebook(title, notes);
        } else {
            item.collapseActionView();
        }
        return super.onContextItemSelected(item);
    }

    private void shareItemFacebook(String title, String notes) {
        String urlToShare = "http://www.google.com";
        // See if official Facebook app is found
        boolean facebookAppFound = appInstalledOrNot("com.facebook.katana");
        // As fallback if no facebook is installed, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        } else {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription("Notes : " + notes)
                        .setContentUrl(Uri.parse("www.facebook.com"))
                        .build();
                shareDialog.show(linkContent);
            }
        }
    }

    //checking whether facebook app is installed
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    //share functionality
    private void shareItem(String title, String notes) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intent.EXTRA_TEXT, "Title : " + title + "\n" + "Notes: " + notes);
        startActivity(Intent.createChooser(intent, "SHARE NOTES"));
    }

    //editing the database
    private void editItem(String title, String notes) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_add_notes);
        dialog.show();
        dialogNotesTitle = (EditText) dialog.findViewById(R.id.memo_add_title);
        dialogNotesContent = (EditText) dialog.findViewById(R.id.memo_add_contents);
        dialogUpdateLayout = (LinearLayout) dialog.findViewById(R.id.update_layout);
        dialog_save = (TextView) dialog.findViewById(R.id.update_save);
        dialog_cancel = (TextView) dialog.findViewById(R.id.update_cancel);
        dialogUpdateLayout.setVisibility(View.VISIBLE);
        dialogNotesTitle.setText(title);
        dialogNotesContent.setText(notes);

        dialog_save.setOnClickListener(this);
        dialog_cancel.setOnClickListener(this);
    }

    //onclick listener for dialog save and cancel textviews
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_save:
                try {
                    DialogUpdateNote(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.update_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    private void DialogUpdateNote(String title) throws IOException {
        cacheModule = new CacheModule();
        cacheModule.title = dialogNotesTitle.getText().toString();
        cacheModule.notes = dialogNotesContent.getText().toString();
        db.updateNotes(cacheModule, title);
        mAdapter.notifyItemChanged(position);
        mAdapter.notifyDataSetChanged();
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        Toast.makeText(getActivity(), "Successfully Edited", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    //removing items from the database
    private void removeItem(String title, int position) {
        memoArray.remove(position);
        db.removeNotes(title);
        mAdapter.notifyDataSetChanged();
        Log.i(TAG, "getTitle : " + title);
    }

    public ArrayList getDataSet() throws IOException {
        memoArray.clear();
        db = new DatabaseHelperNotes(folder, getActivity());
        ArrayList<CacheModule> list = db.getNotes();
        for (int i = list.size() - 1; i >= 0; i--) {
            String nTitle = list.get(i).getTitle();
            String nNotes = list.get(i).getNotes();
            cacheModule = new CacheModule();
            cacheModule.setTitle(nTitle);
            cacheModule.setNotes(nNotes);
            memoArray.add(cacheModule);
        }
        return memoArray;
    }

    //after NoticesAddActivity is closed, the results are obtained from NoticesAddActivity and corresponding action is taken
    //the recyclerview is repopulated from the updated arraylist
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            try {
//                the google cards are repopulated with updated list
                mAdapter = new MyRecyclerViewAdapter(getDataSet());
                mRecyclerView.setAdapter(mAdapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // facebook share callback
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
