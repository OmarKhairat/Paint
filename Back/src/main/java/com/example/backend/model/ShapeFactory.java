package com.example.backend.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

 interface IShape
{
    void setProperties(String JString);
    HashMap<String, Object> ShapeHM();
}
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
                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
                if(jsonObject2.getDouble("width") == jsonObject2.getDouble("height"))
                {
                    IShape shape = new Square();
                    shape.setProperties(JString);
                    return shape;
                }
                IShape shape = new Rectangle();
                shape.setProperties(JString);
                return shape;
            }
            else if(type.equalsIgnoreCase("Circle"))
            {
                IShape shape = new Circle();
                shape.setProperties(JString);
                return shape;
            }
            else if(type.equalsIgnoreCase("Ellipse"))
            {
                IShape shape = new Ellipse();
                shape.setProperties(JString);
                return shape;
            }
            else if(type.equalsIgnoreCase("RegularPolygon"))
            {
                IShape shape = new Triangle();
                shape.setProperties(JString);
                return shape;
            }
            else if(type.equalsIgnoreCase("Line"))
            {
                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
                if(jsonObject2.getJSONArray("points").length() > 4)
                {
                    IShape shape = new FreeHand();
                    shape.setProperties(JString);
                    return shape;
                }
                IShape shape = new LineSegment();
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
