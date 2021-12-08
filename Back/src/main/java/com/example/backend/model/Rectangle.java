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
            attrs.put("x", jsonObject2.getDouble("x"));
            attrs.put("y", jsonObject2.getDouble("y"));
            attrs.put("width", jsonObject2.getDouble("width"));
            attrs.put("height", jsonObject2.getDouble("height"));
            attrs.put("stroke", jsonObject2.getString("stroke"));
            attrs.put("draggable", jsonObject2.getBoolean("draggable"));
            attrs.put("name", jsonObject2.getString("name"));
            attrs.put("scaleY", jsonObject2.getDouble("scaleY"));
            attrs.put("scaleX", jsonObject2.getDouble("scaleX"));
            attrs.put("skewX", jsonObject2.getDouble("skewX"));
            attrs.put("rotation", jsonObject2.getDouble("rotation"));
            attrs.put("strokeWidth", jsonObject2.getDouble("strokeWidth"));
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
