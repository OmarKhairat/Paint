package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Triangle implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public Triangle()
    {
        attrs = new HashMap<String, Object>();
        className = "RegularPolygon";
    }
    @Override
    public void setProperties(String JString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JString);
            JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
            attrs.put("fill", jsonObject2.getString("fill"));
            attrs.put("x", jsonObject2.getDouble("x"));
            attrs.put("y", jsonObject2.getDouble("y"));
            attrs.put("radius", jsonObject2.getDouble("radius"));
            attrs.put("stroke", jsonObject2.getString("stroke"));
            attrs.put("draggable", jsonObject2.getBoolean("draggable"));
            attrs.put("name", jsonObject2.getString("name"));
            attrs.put("scaleY", jsonObject2.getDouble("scaleY"));
            attrs.put("scaleX", jsonObject2.getDouble("scaleX"));
            attrs.put("skewX", jsonObject2.getDouble("scaleY"));
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
