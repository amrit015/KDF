package com.silptech.kdf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silptech.kdf.Utils.CacheNotification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amrit on 2/4/2016.
 */
public class MemoFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MemoFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context context;
    private ArrayList memoArray;
    File folder;
    String memo_cache;
    String xfileName = "cache_note";
    int i;
    String notesTitleCache;
    String notesContentCache;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Memo");
        folder.mkdirs();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_button_add_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotesClickedActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        mLayoutManager = new LinearLayoutManager(context);
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        try {
            mAdapter = new MyRecyclerViewAdapter(getDataSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /* Since RecyclerView doesnt have a Click Listener, we have to implement it
           by modifying provided RecyclerView.OnItemTouchListener.
           */
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                super.onClick(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                super.onLongClick(view, position);
            }
        }));
    }

    public ArrayList getDataSet() throws IOException {
        memoArray = new ArrayList();

        for (int i = folder.list().length - 1; i >= 0; i--) {
            memo_cache = CacheNotification.readFile((xfileName + i), folder);
            if (memo_cache != "") {
                int a = memo_cache.indexOf("##");
                int b = memo_cache.indexOf("###");
                int c = memo_cache.indexOf("####");
                notesTitleCache = memo_cache.substring(a + 2, b);
                ;
                notesContentCache = memo_cache.substring(b + 3, c);
                ;
                NoticesModule noticesModule = new NoticesModule();
                noticesModule.setNotesTitle(notesTitleCache);
                noticesModule.setNotesContents(notesContentCache);
                memoArray.add(noticesModule);
            }
        }
        Log.i(TAG, "folder length :" + folder.list().length);
        return memoArray;
    }

    //after NoticesClickedActivity
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
    }
}
