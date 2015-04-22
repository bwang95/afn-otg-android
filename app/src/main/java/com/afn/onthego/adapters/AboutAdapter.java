package com.afn.onthego.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afn.onthego.R;

import java.util.ArrayList;
import java.util.Collections;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by brian on 4/21/15.
 */
public class AboutAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public static String LOG_TAG = "AboutAdapter";

    Context context;
    ArrayList<ListItem> items;
    ArrayList<String> headers;

    public AboutAdapter(Context context) {
        this.context = context;
        Resources resources = context.getResources();
        items = new ArrayList<>();
        headers = new ArrayList<>();

        String[] headerStrings = resources.getStringArray(R.array.about_headers);
        String[] entries = resources.getStringArray(R.array.about_contributors);
        String[] actions = resources.getStringArray(R.array.about_actions);
        int[] entryPairs = resources.getIntArray(R.array.about_header_ids);

        if (headerStrings.length != entryPairs.length)
            throw new IllegalStateException("Header array is not the same size as id array!");

        int t = 0;
        for (int h = 0; h < entryPairs.length; h++) {
            for (int k = 0; k < entryPairs[h]; k++)
                items.add(new ListItem(entries[t + k], actions[t + k], h));
            t += entryPairs[h];
        }

        Collections.addAll(headers, headerStrings);
    }

    @Override
    public long getHeaderId(int i) {
        return items.get(i).headerId;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Uri getAction(int position) {
        return items.get(position).action;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_about_entry, null);
        }

        TextView text = (TextView) convertView.findViewById(R.id.tv_aboutentry_text);

        text.setText(getItem(position).toString());

        return convertView;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_about_header, null);
        }

        TextView text = (TextView) view.findViewById(R.id.tv_aboutheader_text);

        text.setText(headers.get(items.get(i).headerId));

        return view;
    }

    private class ListItem {
        public String item;
        public Uri action;
        public int headerId;

        public ListItem(String item, String action, int headerId) {
            this.item = item;
            this.headerId = headerId;
            this.action = Uri.parse(action);
        }

        public String toString() {
            return item;
        }
    }
}
