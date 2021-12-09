package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Circle extends absShape implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public Circle()
    {
        attrs = new HashMap<String, Object>();
        className = "Circle";
    }
//    {"attrs":{"fill":"transparent","x":190,"y":190,"radius":90,"stroke":"black","draggable":true,"name":"rect"},"className":"Circle"}

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
            attrs.put("radius", jsonObject2.getDouble("radius"));
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
