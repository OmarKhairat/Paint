package com.example.backend.controller;

import com.example.backend.BackendApplication;
import com.example.backend.model.konva;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/controller")
public class MainController
{
    konva drawSheet = konva.getInstance();
    @GetMapping("/draw")
    public long drawShape(@RequestParam String first)
    {
        return drawSheet.drawShape(first);
//        System.out.println(first);
//
//
//        test t = new test();
//        Gson gs = new Gson();
////        JSONObject dummy = new JSONObject();
////        try
////        {
////            JSONObject jsonObject = new JSONObject(first);
////            JSONObject jsonObject2 = new JSONObject(jsonObject.getString("attrs"));
////            System.out.println("JSON Object: "+jsonObject2.getInt("x"));
//////            return jsonObject;
////        }
////        catch (JSONException e)
////        {
////            System.out.println("Error "+e.toString());
////        }
////        t.add();
//        HashMap<Integer, Object> hm = new HashMap<Integer, Object>();
//        HashMap<Integer, Object> hm2 = new HashMap<Integer, Object>();
//        ArrayList<Double> dd = new ArrayList<Double>();
//        dd.add(555.0);
//        dd.add(5897.55);
//        hm2.put(999, "99");
//        hm2.put(2, "efowe");
//        hm.put(999, "99");
//        hm.put(2, "efowe");
//        hm.put(2, hm2);
//        hm.put(22,55);
//        hm.put(5, dd);
//        String res = gs.toJson(hm);
//        System.out.println(res);
////        return res;
//        return res;
    }
    @GetMapping("/edit")
    public String editShape(@RequestParam String shape, @RequestParam String id)
    {
        drawSheet.preformEdition(shape, id);
        return "The Shape is edited.";
    }
    @GetMapping("/delete")
    public String deleteShape(@RequestParam String id)
    {
        drawSheet.preformDeletion(id);
        return "The Shape is deleted.";
    }
    @GetMapping("/Copy")
    public String CopyShape(@RequestParam String id) throws JSONException
    {
        return drawSheet.preformCopy(id);
    }
    @GetMapping("/Paste")
    public String PasteShape(@RequestParam String JString) throws JSONException
    {
        return drawSheet.preformPaste(JString);
    }
}
