package com.example.backend.controller;

import com.example.backend.model.konva;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import java.io.FileNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/controller")
public class MainController
{
    konva drawSheet = konva.getInstance();
    @GetMapping("/draw")
    public long drawShape(@RequestParam String first)
    {
        drawSheet.emptyRedo();
        return drawSheet.drawShape(first, true);

    }
    @GetMapping("/edit")
    public String editShape(@RequestParam String shape, @RequestParam String id)
    {
        drawSheet.emptyRedo();
        drawSheet.preformEdition(shape, id);
        return "The Shape is edited.";
    }
    @GetMapping("/delete")
    public String deleteShape(@RequestParam String id)
    {
        drawSheet.emptyRedo();
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
        drawSheet.emptyRedo();
        return drawSheet.preformPaste(JString);
    }
    @GetMapping("/undo")
    public String getUndo() throws JSONException
    {
        return drawSheet.preformUndo();
    }
    @GetMapping("/redo")
    public String getRedo() throws JSONException
    {
        return drawSheet.preformRedo();
    }

    @GetMapping("/save")
    public void save() throws JSONException {
        drawSheet.save();

    }
    @GetMapping("/load")
    public String load() throws FileNotFoundException, JSONException {
        return drawSheet.load();
    }
}
