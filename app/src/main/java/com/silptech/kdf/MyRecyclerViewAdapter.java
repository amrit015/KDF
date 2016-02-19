package com.silptech.kdf;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This is the adapter for RecyclerView. The adapter must extend its child
 * i.e. RecyclerView.Adapter<MyRecyclerViewAdapter.ItemObjectHolder> and must have a ViewHolder
 * which extends RecyclerView.ViewHolder
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.NoticesHolder> {
    private ArrayList<CacheModule> mDataset;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    CardView cardView;
    Context context;
    private String title;
    private String notes;
    private int position;

    public class NoticesHolder extends RecyclerView.ViewHolder {

        TextView noticeMessage;
        TextView noticeAuthor;
        TextView noticeDate;
        TextView noteTitle;
        TextView noteContents;

        public NoticesHolder(View itemView) {
            super(itemView);
            noticeMessage = (TextView) itemView.findViewById(R.id.notices_message);
            noticeAuthor = (TextView) itemView.findViewById(R.id.notices_author);
            noticeDate = (TextView) itemView.findViewById(R.id.notices_date);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteContents = (TextView) itemView.findViewById(R.id.note_contents);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyRecyclerViewAdapter(ArrayList mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public NoticesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cardview_adapter, parent, false);
        context = parent.getContext();
        NoticesHolder noticesHolder = new NoticesHolder(view);
        return noticesHolder;
    }

    @Override
    public void onBindViewHolder(final NoticesHolder holder, final int position) {
        CacheModule cacheModule = mDataset.get(position);
        if (cacheModule.getMessage() != "") {
            holder.noticeMessage.setText(cacheModule.getMessage());
            holder.noticeAuthor.setText(cacheModule.getAuthor());
            holder.noticeDate.setText(cacheModule.getDate());
        } else if (cacheModule.getTitle() != "" && cacheModule.getNotes() != "") {
            holder.noteTitle.setText(cacheModule.getTitle());
            holder.noteContents.setText(cacheModule.getNotes());
            holder.noteTitle.setVisibility(View.VISIBLE);
            holder.noteContents.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.GONE);
        }
        //saving title,notes and position of each cards for contextmenu
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setTitle(holder.noteTitle.getText().toString());
                setNotes(holder.noteContents.getText().toString());
                setPosition(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
