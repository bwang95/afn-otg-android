package com.afn.onthego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afn.onthego.R;

import java.util.ArrayList;

/**
 * Created by brian on 4/21/15.
 */
public class ConnectAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<String> locations;

    public ConnectAdapter(Context context, ArrayList<String> locations) {
        this.context = context;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_map_location, null);
        }

        TextView text = (TextView) convertView.findViewById(R.id.tv_maploc_text);

        text.setText(locations.get(position));

        return convertView;
    }
}
