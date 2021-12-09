package com.example.backend.model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class konva
{
    private HashMap<Long, IShape> shapes;
    private ShapeFactory factory;
    private ArrayList<Long> IdArr;
    private static konva sheet;
    private long currentID = 0;
    private Gson gson = new Gson();
    private Stack<Operation> undo;
    private Stack<Operation> redo;
    private konva()
    {
        shapes = new HashMap<Long, IShape>();
        factory = new ShapeFactory();
        IdArr = new ArrayList<Long>();
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
    public long drawShape(String JString, boolean addToId)
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
//        System.out.println(gson.toJson(drawnShape.ShapeHM()));
        if(addToId)
        {
            Operation op = new Operation("Draw", tempArr);
            op.setNextShape(tempHM);
            IdArr.add(getCurrentID());
            undo.push(op);
        }
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
        JSONArray tempArr = new JSONArray();
        long id = Long.parseLong(StrID);
        tempArr.put(id);
        Operation op = new Operation("Delete", tempArr);
        PrevTempHM.put(StrID, shapes.get(StrID));
        op.setPrevShapes(PrevTempHM);
        shapes.remove(id);
        IdArr.remove(id);
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
                long newID = drawShape(drawnShape.ShapeHM().toString(), false);
                newIDs.put(newID);
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
            HashMap<String, IShape> NextTempHM = new HashMap<String, IShape>();
            JSONArray tempArr = new JSONArray();
            IDs = jsonObject.getJSONArray("IDs");
            HMRes.put("IDs", IDs);
            deltaX = jsonObject.getDouble("deltaX");
            deltaY = jsonObject.getDouble("deltaY");
            for(int i = 0; i < IDs.length(); i++)
            {
                long tempID = Long.parseLong(IDs.get(i).toString());
                tempArr.put(tempID);
                IShape tempShape = shapes.get(tempID);
                tempShape.offset(deltaX, deltaY);
                shapes.put(tempID, tempShape);
                IdArr.add(tempID);
                NextTempHM.put(IDs.get(i).toString(), tempShape);
                HMRes.put(Long.toString(tempID), tempShape.ShapeHM());
            }
            Operation op = new Operation("Draw", tempArr);
            op.setNextShape(NextTempHM);
            undo.push(op);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String res = gson.toJson(HMRes);
        return res;
    }
    public void emptyRedo(){ redo.clear();}
    public String preformUndo() throws JSONException
    {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Operation tempOp = undo.pop();
        JSONArray tempArr = tempOp.getIDs();
        redo.push(tempOp);
        String nameOperation = tempOp.getName();
        result.put("Name", nameOperation);
        result.put("IDs", tempArr);
        if(nameOperation == "Draw")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(tempId).ShapeHM()));
                shapes.remove(tempId);
                IdArr.remove(tempId);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Edit")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(tempId).ShapeHM()));
                IShape tempShape = shapes.get(tempId);
                tempShape.setProperties(gson.toJson(tempOp.getPrevShapes().get(tempId)));
                shapes.remove(tempId);
                shapes.put(tempId, tempShape);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Delete")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(tempId).ShapeHM()));
                String JString = gson.toJson(tempOp.getPrevShapes().get(tempId));
                shapes.put(tempId, tempOp.getPrevShapes().get(tempId));
                IdArr.add(tempId);
            }
            return gson.toJson(result);
        }
        return "Error";
    }
    public String preformRedo() throws JSONException
    {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Operation tempOp = redo.pop();
        JSONArray tempArr = tempOp.getIDs();
        undo.push(tempOp);
        String nameOperation = tempOp.getName();
        result.put("Name", nameOperation);
        result.put("IDs", tempArr);
        if(nameOperation == "Draw")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(tempId).ShapeHM()));
                shapes.put(tempId, tempOp.getNextShapes().get(tempId));
                IdArr.add(tempId);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Edit")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(tempId).ShapeHM()));
                IShape tempShape = shapes.get(tempId);
                tempShape.setProperties(gson.toJson(tempOp.getNextShapes().get(tempId)));
                shapes.remove(tempId);
                shapes.put(tempId, tempShape);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Delete")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(tempId).ShapeHM()));
                shapes.remove(tempId);
                IdArr.remove(tempId);
            }
            return gson.toJson(result);
        }
        return "Error";
    }
}
