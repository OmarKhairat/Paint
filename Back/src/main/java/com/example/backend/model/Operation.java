package com.example.backend.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class Operation
{
    private String name;
    private JSONArray ShapeID;
    private HashMap<String, IShape> prevShapes;
    private HashMap<String, IShape> NextShapes;
    private JSONArray IDs = new JSONArray();
    public Operation(String name, JSONArray IDs)
    {
        this.name = name;
        this.IDs = IDs;
        this.prevShapes = null;
        this.NextShapes = null;
    }
    public void setNextShape(HashMap<String, IShape> NS)
    {
        this.NextShapes = NS;
    }
    public void setPrevShapes(HashMap<String, IShape> PS)
    {
        this.prevShapes = PS;
    }
    public HashMap<String, IShape> getNextShapes()
    {
        return  NextShapes;
    }
    public HashMap<String, IShape> getPrevShapes()
    {
        return  prevShapes;
    }
}
