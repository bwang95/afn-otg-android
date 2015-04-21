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

/**
 * Created by Kyle on 4/20/2015.
 */
public class LearnAdapter extends BaseAdapter {
    Context context;
    LearningModule learningModule;
    ArrayList<LearningModule> learningModules;
    public LearnAdapter(Context context, LearningModule learningModule)
    {
        this.context = context;
        this.learningModule = learningModule;
        learningModules = Storage.getInstance(context).getLearningModules().getLearningModulesArray();
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
        if(convertView != null)
        {
            return convertView;
        }

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_learn_selector, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_learn_image);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_learn_title);

        switch(learningModule.getType())
        {
            case KeyList.LearningModulesKeys.TYPE_YOUTUBE:
                // set image here
                break;
            case KeyList.LearningModulesKeys.TYPE_WEBSITE:
                // set image here
                break;
            case KeyList.LearningModulesKeys.TYPE_PDF:
                // set image here
                break;
            default:
                // set default image
        }
        textView.setText(learningModule.getName());

        return convertView;
    }
}
