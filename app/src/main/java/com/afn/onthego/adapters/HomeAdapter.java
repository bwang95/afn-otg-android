package com.afn.onthego.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afn.onthego.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by brian on 4/17/15.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titles;
    private int[] imageIds = {
            R.drawable.abc_btn_rating_star_off_mtrl_alpha,
            R.drawable.abc_btn_rating_star_off_mtrl_alpha,
            R.drawable.abc_btn_rating_star_off_mtrl_alpha,
            R.drawable.abc_btn_rating_star_off_mtrl_alpha
    };

    public HomeAdapter(Context context) {
        this.context = context;
        titles = new ArrayList<>();
        Collections.addAll(titles, context.getResources().getStringArray(R.array.fragment_titles));
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null)
            return convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_home_selector, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.iv_hs_image);
        TextView text = (TextView) convertView.findViewById(R.id.tv_hs_title);

        image.setImageResource(imageIds[position]);
        text.setText(titles.get(position));

        return convertView;
    }
}
