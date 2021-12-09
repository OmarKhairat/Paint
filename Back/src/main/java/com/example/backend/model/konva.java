package com.example.backend.model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Stack;

public class konva
{
    private HashMap<Long, IShape> shapes;
    private ShapeFactory factory;
    private static konva sheet;
    private long currentID = 0;
    private Gson gson = new Gson();
    private Stack<Operation> undo;
    private Stack<Operation> redo;
    private konva()
    {
        shapes = new HashMap<Long, IShape>();
        factory = new ShapeFactory();
        undo = new Stack<Operation>();
        redo = new Stack<Operation>();
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
        //START
        String SID = Long.toString(getCurrentID());
        JSONArray tempArr = new JSONArray();
        HashMap<String, IShape> tempHM = new HashMap<String, IShape>();
        tempArr.put(getCurrentID());
        tempHM.put(SID, drawnShape);
        Operation op = new Operation("Draw", tempArr);
        op.setNextShape(tempHM);
        //END
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
        undo.push(op);
        return  getCurrentID();
    }
    public void preformEdition(String JString, String StrID)
    {
        HashMap<String, IShape> PrevTempHM = new HashMap<String, IShape>();
        HashMap<String, IShape> NextTempHM = new HashMap<String, IShape>();
        JSONArray tempArr = new JSONArray();
        long id = Long.parseLong(StrID);
        tempArr.put(id);
        Operation op = new Operation("Edit", tempArr);
        IShape tempShape = shapes.get(id);
        PrevTempHM.put(StrID, tempShape);
        System.out.println("Before:" + gson.toJson(shapes.get(id).ShapeHM()));
        tempShape.setProperties(JString);
        shapes.remove(id);
        shapes.put(id, tempShape);
        NextTempHM.put(StrID, tempShape);
        op.setNextShape(NextTempHM);
        op.setPrevShapes(PrevTempHM);
        undo.push(op);
        System.out.println("After:" + gson.toJson(shapes.get(id).ShapeHM()));
    }
    public void preformDeletion(String StrID)
    {
        HashMap<String, IShape> PrevTempHM = new HashMap<String, IShape>();
        HashMap<String, IShape> NextTempHM = new HashMap<String, IShape>();
        JSONArray tempArr = new JSONArray();
        long id = Long.parseLong(StrID);
        tempArr.put(id);
        Operation op = new Operation("Delete", tempArr);
        PrevTempHM.put(StrID, shapes.get(StrID));
        op.setPrevShapes(PrevTempHM);
        shapes.remove(id);
        undo.push(op);
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
                System.out.println("hhhhz: "+ drawnShape.ShapeHM().toString());
                absShape absDrawnShape = (absShape)drawnShape;
                absShape temp = (absShape)absDrawnShape.clone();
                IShape ITemp = temp;
                //remove underline
                shapes.remove(tempID);
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
