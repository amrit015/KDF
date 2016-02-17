package com.silptech.kdf;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    private ArrayList<NoticesModule> mDataset;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    CardView cardView;

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
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Item clicked. "+uniName.getText(), Toast.LENGTH_SHORT).show();
//
//        }
    }

    public MyRecyclerViewAdapter(ArrayList mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public NoticesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notices_adapter, parent, false);
        NoticesHolder noticesHolder = new NoticesHolder(view);
        return noticesHolder;
    }

    @Override
    public void onBindViewHolder(NoticesHolder holder, int position) {
        NoticesModule noticesModule = mDataset.get(position);
        if (noticesModule.getMessage() != "") {

            holder.noticeMessage.setText(noticesModule.getMessage());
            holder.noticeAuthor.setText(noticesModule.getAuthor());
            holder.noticeDate.setText(noticesModule.getDate());
        } else if (noticesModule.getNotesTitle() != "" && noticesModule.getNotesContents() != "") {
            holder.noteTitle.setText(noticesModule.getNotesTitle());
            holder.noteContents.setText(noticesModule.getNotesContents());
            holder.noteTitle.setVisibility(View.VISIBLE);
            holder.noteContents.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.GONE);
        }
    }

//    public void addItem(NoticesModule dataObj, int index) {
//        mDataset.add(index, dataObj);
//        notifyItemInserted(index);
//    }
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
