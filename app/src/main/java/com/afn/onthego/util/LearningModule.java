package com.afn.onthego.util;

import java.util.Comparator;

/**
 * Created by kyle on 4/12/15.
 */
public class LearningModule implements Comparable<LearningModule> {
    public String name;
    public String type;
    public String data;
    public int position;


    public int compareTo(LearningModule another) {
        if(this.position == another.position)
        {
            return 0;
        }
        else if(this.position < another.position)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

}
