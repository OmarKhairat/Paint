package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ShapeFactory
{
    public IShape createShape(String JString)
    {
//        IShape shape;
        try
        {
            JSONObject jsonObject = new JSONObject(JString);
            String type =  jsonObject.getString("className");
            if(type.equalsIgnoreCase("Rect"))
            {
                IShape shape = new Rectangle();
                shape.setProperties(JString);
                return shape;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
