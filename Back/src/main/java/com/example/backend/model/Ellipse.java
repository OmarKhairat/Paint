package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Ellipse implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public Ellipse()
    {
        attrs = new HashMap<String, Object>();
        className = "Ellipse";
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
            attrs.put("radiusX", jsonObject2.getDouble("radiusX"));
            attrs.put("radiusY", jsonObject2.getDouble("radiusY"));
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
    @Override
    public HashMap<String, Object> ShapeHM()
    {
        HashMap<String, Object> ans = new HashMap<String, Object>();
        ans.put("attrs", attrs);
        ans.put("className", className);
        return ans;
    }
    @Override
    public void offset(double deltaX,double deltaY)
    {
        double xx = (double)attrs.get("x");
        double yy = (double)attrs.get("y");
        attrs.put("x", xx + deltaX);
        attrs.put("y", yy + deltaY);
    }
}
