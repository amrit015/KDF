package com.silptech.kdf;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Amrit on 2/4/2016.
 * This class is a custom adapter which is used to populate the title drawer and inflate with the corresponding
 * drawer name and images.
 */
public class DrawerTitleAdapter extends BaseAdapter {
    private final String[] drawerItemsArray;
    private final TypedArray drawerIconsArray;
    Context c;
    private int id; //textView layout id

    public DrawerTitleAdapter(Context context, int adapter_title, String[] drawerItemsArray, TypedArray drawerIconsArray) {
        this.drawerItemsArray = drawerItemsArray;
        this.drawerIconsArray = drawerIconsArray;
        c = context;
        id = adapter_title;
    }

    @Override
    public int getCount() {
        return drawerItemsArray.length;
    }

    @Override
    public Object getItem(int position) {
        return drawerItemsArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
            holder.drawerText = (TextView) convertView.findViewById(R.id.adapter_title);
            holder.drawerIcons = (ImageView) convertView.findViewById(R.id.adapter_icons);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //setting names of the drawer items
        holder.drawerText.setText(drawerItemsArray[position]);
        //setting images to the left of the title
        holder.drawerIcons.setImageResource(drawerIconsArray.getResourceId(position, -1));
        return convertView;
    }

    private class ViewHolder {
        TextView drawerText;
        ImageView drawerIcons;
    }
}
