package com.silptech.kdf;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silptech.kdf.Utils.CallDial;
import com.silptech.kdf.Utils.Log;

import java.util.ArrayList;

/**
 * Created by Amrit on 3/12/2016.
 * This class is a custom adapter which implements BaseAdapter and returns text to the listView
 * and is used by tabs of CommitteeFragment.
 */
public class CommitteeAdapter extends BaseAdapter {

    private static final String TAG = "CommitteeAdapter";
    ArrayList<CommitteeModule> newList;
    Activity context;

    public CommitteeAdapter(Activity activity, ArrayList<CommitteeModule> list) {
        newList = list;
        context = activity;
        Log.i(TAG, "new list size:  " + newList.size());
    }

    @Override
    public int getCount() {
        return newList.size();
    }

    @Override
    public Object getItem(int position) {
        return newList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return newList.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_committee, parent, false);
            holder.NAME = (TextView) convertView.findViewById(R.id.committee_name);
            holder.POST = (TextView) convertView.findViewById(R.id.committee_post);
            holder.PHONE = (TextView) convertView.findViewById(R.id.committee_phone);
            holder.CELL = (TextView) convertView.findViewById(R.id.committee_cell);
            holder.cellLayout = (LinearLayout) convertView.findViewById(R.id.layout_committee_cell);
            holder.phoneLayout = (LinearLayout) convertView.findViewById(R.id.layout_committee_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.NAME.setText(Html.fromHtml(newList.get(position).getName()).toString());
        holder.POST.setText(Html.fromHtml("(" + newList.get(position).getPost()).toString() + ")");
        holder.PHONE.setText(Html.fromHtml(String.valueOf(newList.get(position).getPhone())).toString());
        holder.CELL.setText(Html.fromHtml(String.valueOf(newList.get(position).getCell())).toString());
        holder.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneCall = String.valueOf((newList.get(position).getPhone()));
                CallDial.PhoneDialer(context, phoneCall);
            }
        });
        holder.cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cellCall = String.valueOf((newList.get(position).getCell()));
                CallDial.PhoneDialer(context, cellCall);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView NAME;
        TextView POST;
        TextView PHONE;
        TextView CELL;
        LinearLayout phoneLayout;
        LinearLayout cellLayout;
    }
}
