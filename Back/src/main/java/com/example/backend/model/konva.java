package com.example.backend.model;

import com.google.gson.Gson;

import java.util.HashMap;

public class konva
{
    private HashMap<Long, Object> shapes;
    private ShapeFactory factory;
    private static konva sheet;
    private long currentID = 0;
    private Gson gson = new Gson();
    private konva()
    {
        shapes = new HashMap<Long, Object>();;
        factory = new ShapeFactory();
    }
    public static konva getInstance()
    {
        if(sheet == null)
        {
            sheet = new konva();
        }
        return sheet;
    }
    public long getCurrentID()
    {
        return currentID;
    }
    public void setCurrentID()
    {
        this.currentID = currentID + 1;
    }
    public long drawShape(String JString)
    {
        IShape drawnShape = factory.createShape(JString);
        System.out.println(gson.toJson(drawnShape.ShapeHM()));
        setCurrentID();
        return  getCurrentID();
    }
}
