package com.example.backend.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LineSegment implements IShape
{
    private String className;
    private HashMap<String, Object> attrs;
    public LineSegment()
    {
        attrs = new HashMap<String, Object>();
        className = "Line";
    }
    @Override
    public void setProperties(String JString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JString);
            JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
            ArrayList<Double> pts = new ArrayList<Double>();
            JSONArray Jpts =  jsonObject2.getJSONArray("points");
            for(int i = 0; i < Jpts.length(); i++)
            {
                String temp = Jpts.get(i).toString();
                pts.add(Double.parseDouble(temp));
            }
            attrs.put("points", pts);
            attrs.put("stroke", jsonObject2.getString("stroke"));
            attrs.put("draggable", jsonObject2.getBoolean("draggable"));
            attrs.put("name", jsonObject2.getString("name"));
            attrs.put("scaleY", jsonObject2.getDouble("scaleY"));
            attrs.put("scaleX", jsonObject2.getDouble("scaleX"));
            attrs.put("tension", jsonObject2.getDouble("tension"));
            attrs.put("rotation", jsonObject2.getDouble("rotation"));
            attrs.put("strokeWidth", jsonObject2.getDouble("strokeWidth"));
            attrs.put("skewX", jsonObject2.getDouble("skewX"));
            attrs.put("x", jsonObject2.getDouble("x"));
            attrs.put("y", jsonObject2.getDouble("y"));
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
