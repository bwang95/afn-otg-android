package com.afn.onthego.util;

import java.util.Comparator;

/**
 * Created by kyle on 4/12/15.
 */
public class LearningModule implements Comparable<LearningModule> {
    public String name;
    public String type;
    public String data;
    public String position;

    public LearningModule()
    {}

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public int compareTo(LearningModule another) {
        return this.position.compareTo(another.position);
    }

}
