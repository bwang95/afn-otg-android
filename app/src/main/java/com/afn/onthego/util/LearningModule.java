package com.afn.onthego.util;

/**
 * Created by Kyle on 4/14/2015.
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

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getData()
    {
        return data;
    }

    public String getPosition()
    {
        return position;
    }

    public int compareTo(LearningModule another) {
        return this.position.compareTo(another.getPosition());
    }
}
