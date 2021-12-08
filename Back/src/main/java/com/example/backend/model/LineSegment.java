package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

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
            attrs.put("points", jsonObject2.getJSONArray("points"));
            attrs.put("stroke", jsonObject2.getString("stroke"));
            attrs.put("draggable", jsonObject2.getBoolean("draggable"));
            attrs.put("name", jsonObject2.getString("name"));
            attrs.put("scaleY", jsonObject2.getDouble("scaleY"));
            attrs.put("scaleX", jsonObject2.getDouble("scaleX"));
            attrs.put("tension", jsonObject2.getDouble("tension"));
            attrs.put("rotation", jsonObject2.getDouble("rotation"));
            attrs.put("strokeWidth", jsonObject2.getDouble("strokeWidth"));
            attrs.put("skewX", jsonObject2.getDouble("skewX"));

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
