package com.afn.onthego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LearningModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 4/20/2015.
 */
public class LearnAdapter extends BaseAdapter {
    Context context;
    List<LearningModule> learningModules;
    public LearnAdapter(Context context, List<LearningModule> learningModules)
    {
        this.context = context;
        this.learningModules = learningModules;
    }


    @Override
    public int getCount() {
        return learningModules.size();
    }

    @Override
    public Object getItem(int position) {
        return learningModules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LearningModule learningModule = learningModules.get(position);
        View view;
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_learn_selector, parent, false);
        }
        else
        {
            view = convertView;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_learn_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_learn_title);

        switch(learningModule.getType())
        {
            case KeyList.LearningModulesKeys.TYPE_YOUTUBE:
                imageView.setImageResource(R.drawable.ic_plusone_small_off_client);
                // set image here
                break;
            case KeyList.LearningModulesKeys.TYPE_WEBSITE:
                imageView.setImageResource(R.drawable.ic_plusone_medium_off_client);
                // set image here
                break;
            case KeyList.LearningModulesKeys.TYPE_PDF:
                imageView.setImageResource(R.drawable.ic_plusone_tall_off_client);
                // set image here
                break;
            default:
                // set default image
        }
        textView.setText(learningModule.getName());

        return view;
    }
}
