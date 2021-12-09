package com.example.backend.model;

import java.util.HashMap;

public abstract class absShape implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public Object clone()
    {
        Object clone = null;
        try
        {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return clone;
    }
    public void setProperties(String JString) {}
    public HashMap<String, Object> ShapeHM()
    {
        return null;
    }
    public void offset(double deltaX,double deltaY) {}
}
