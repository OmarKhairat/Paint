package com.example.backend.model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        shapes = new HashMap<Long, IShape>();
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
        /*
        * test clone
        * */
//        IShape cloned = (IShape) drawnShape;
//        absShape dd = (absShape)drawnShape;
//        absShape aa = (absShape)dd.clone();
//        IShape bb = aa;
//        System.out.println("cloned :" + gson.toJson(bb.ShapeHM()));
//        System.out.println(gson.toJson(drawnShape.ShapeHM()));
//        dd.testop();
//        System.out.println("cloned :" + gson.toJson(dd.ShapeHM()));
        System.out.println(gson.toJson(drawnShape.ShapeHM()));
        return  getCurrentID();
    }
    public void preformEdition(String JString, String StrID)
    {
        long id = Long.parseLong(StrID);
        IShape tempShape = shapes.get(id);
        System.out.println("Before:" + gson.toJson(shapes.get(id).ShapeHM()));
        tempShape.setProperties(JString);
        shapes.remove(id);
        shapes.put(id, tempShape);
        System.out.println("After:" + gson.toJson(shapes.get(id).ShapeHM()));
    }
    public void preformDeletion(String StrID)
    {
        long id = Long.parseLong(StrID);
        shapes.remove(id);
    }
    public String preformCopy(String StrIDS) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(StrIDS);
        JSONArray IDs;
        JSONArray newIDs = new JSONArray();
        try
        {
            IDs = jsonObject.getJSONArray("Id");
            for(int i = 0; i < IDs.length(); i++)
            {
                long tempID =Long.parseLong(IDs.get(i).toString());
                IShape drawnShape = shapes.get(tempID);
                absShape absDrawnShape = (absShape)drawnShape;
                absShape temp = (absShape)absDrawnShape.clone();
                IShape ITemp = temp;
                setCurrentID();
                shapes.put(getCurrentID(), ITemp);
                newIDs.put(getCurrentID());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String res = gson.toJson(newIDs);
        return res;
    }
    public String preformPaste(String StrPaste) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(StrPaste);
        JSONArray IDs;
        HashMap<String, Object> HMRes = new HashMap<String, Object>();
        double deltaX, deltaY;
        try
        {
            IDs = jsonObject.getJSONArray("IDs");
            HMRes.put("IDs", IDs);
            deltaX = jsonObject.getDouble("deltaX");
            deltaY = jsonObject.getDouble("deltaY");
            JSONArray arrShape = new JSONArray();
            for(int i = 0; i < IDs.length(); i++)
            {
                long tempID = Long.parseLong(IDs.get(i).toString());
                IShape tempShape = shapes.get(tempID);
                tempShape.offset(deltaX, deltaY);
                shapes.put(tempID, tempShape);
                HMRes.put(Long.toString(tempID), tempShape.ShapeHM());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String res = gson.toJson(HMRes);
        return res;
    }

}
