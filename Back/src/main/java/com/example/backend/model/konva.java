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
        op.setPrevShapes(PrevTempHM);
        System.out.println("Before:" + gson.toJson(shapes.get(id).ShapeHM()));
        shapes.remove(id);
        IShape testShape = factory.createShape(JString);
        IShape testShape2 = factory.createShape(JString);
        shapes.put(id, testShape);
        NextTempHM.put(StrID, testShape2);
        op.setNextShape(NextTempHM);
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
        IShape testShape = factory.createShape(gson.toJson(shapes.get(id).ShapeHM()));
        PrevTempHM.put(StrID, testShape);
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
        if(undo.empty())
        {
            return "Error";
        }
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
                System.out.println("help:    "+ tempId);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(Long.toString(tempId)).ShapeHM()));
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
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(Long.toString(tempId)).ShapeHM()));
                IShape tempShape = shapes.get(tempId);
                System.out.println("Marwan Pablo: "+ gson.toJson(tempOp.getPrevShapes().get(Long.toString(tempId)).ShapeHM()));
                tempShape.setProperties(gson.toJson(tempOp.getPrevShapes().get(Long.toString(tempId)).ShapeHM()));
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
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(Long.toString(tempId)).ShapeHM()));
                shapes.put(tempId, tempOp.getPrevShapes().get(Long.toString(tempId)));
                IdArr.add(tempId);
            }
            return gson.toJson(result);
        }
        return "Error";
    }
    public String preformRedo() throws JSONException
    {
        if(redo.empty())
        {
            return "Error";
        }
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
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(Long.toString(tempId)).ShapeHM()));
                shapes.put(tempId, tempOp.getNextShapes().get(Long.toString(tempId)));
                IdArr.add(tempId);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Edit")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getNextShapes().get(Long.toString(tempId)).ShapeHM()));
                IShape testShape = factory.createShape(gson.toJson(tempOp.getNextShapes().get(Long.toString(tempId)).ShapeHM()));
//                IShape tempShape = shapes.get(tempId);
//                System.out.println("Hello:   " + gson.toJson(tempOp.getNextShapes().get(Long.toString(tempId))));
//                tempShape.setProperties();
                shapes.remove(tempId);
                shapes.put(tempId, testShape);
            }
            return gson.toJson(result);
        }
        else if(nameOperation == "Delete")
        {
            for(int i = 0; i < tempArr.length(); i++)
            {
                long tempId = (long)tempArr.get(i);
                result.put(Long.toString(tempId), gson.toJson(tempOp.getPrevShapes().get(Long.toString(tempId)).ShapeHM()));
                shapes.remove(tempId);
                IdArr.remove(tempId);
            }
            return gson.toJson(result);
        }
        return "Error";
    }
}
