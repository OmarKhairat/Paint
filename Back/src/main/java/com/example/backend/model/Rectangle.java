package com.example.backend.model;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Rectangle implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public Rectangle()
    {
        attrs = new HashMap<String, Object>();
        className = "Rect";
    }

    @Override
    public void setProperties(String JString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JString);
            JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
//            {"fill":"transparent","x":160,"y":160,"width":150,"height":75,"stroke":"royalblue","draggable":true,"name":"rect"},"className":"Rect"}
            attrs.put("fill", jsonObject2.getString("fill"));
            attrs.put("x", jsonObject2.getInt("x"));
            attrs.put("y", jsonObject2.getInt("y"));
            attrs.put("width", jsonObject2.getInt("width"));
            attrs.put("height", jsonObject2.getInt("height"));
            attrs.put("stroke", jsonObject2.getString("stroke"));
            attrs.put("draggable", jsonObject2.getBoolean("draggable"));

        }
        catch (JSONException e)
        {
            System.out.println("Error "+e.toString());
        }
    }

    public HashMap<String, Object> ShapeHM()
    {
        HashMap<String, Object> ans = new HashMap<String, Object>();
        ans.put("attrs", attrs);
        ans.put("className", className);
        return ans;
    }
}
