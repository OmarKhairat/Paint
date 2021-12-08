package com.example.backend.model;

import com.google.gson.Gson;

import java.util.HashMap;

public class konva
{
    private HashMap<Long, IShape> shapes;
    private ShapeFactory factory;
    private static konva sheet;
    private long currentID = 0;
    private Gson gson = new Gson();
    private konva()
    {
        shapes = new HashMap<Long, IShape>();;
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
        setCurrentID();
        shapes.put(getCurrentID(), drawnShape);
        drawnShape = shapes.get(getCurrentID());
        System.out.println(gson.toJson(drawnShape.ShapeHM()));
        return  getCurrentID();
    }
    public void preformEdition(String JString, String StrID)
    {
        long id = Long.parseLong(StrID);
        IShape tempShape = shapes.get(id);
        tempShape.setProperties(JString);
        shapes.remove(id);
        shapes.put(id, tempShape);
    }
    public void preformDeletion(String StrID)
    {
        long id = Long.parseLong(StrID);
        shapes.remove(id);
    }
}
