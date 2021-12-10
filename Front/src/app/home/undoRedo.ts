import Konva from 'Konva';
import Operation from "./operation";
import Convert from './convert';
class UndoRedo{
    check:boolean=false
    operations: any = new Operation
    convert: any = new Convert
    
    searchforshape( stage : Konva.Stage, ids:string[] , layer:Konva.Layer)
    {
        console.log(ids)
        for(let i=0 ;i<ids.length;i++)
        {
            var id = ids[i]
            console.log(typeof(id.toString() ))
            var ch = layer.getChildren(function(node){
                console.log(node.getAttr('id'))
                return node.getAttr('id')==id.toString() ;
             })
             console.log(ch)
             this.operations.delete(ch)
        }
   
    }

    create(shape:any , layer: Konva.Layer)
    {
        console.log("in create")


        var jas = shape
        console.log(jas)
        delete jas.Name ;
        var str = JSON.stringify(jas)
        this.convert.jsonToShapes(str, layer)


    }
    undo(stage : Konva.Stage , strR: string,layer:Konva.Layer)
    {
        console.log(strR)
        var jas = JSON.parse(strR)
        console.log(jas)

        if(jas["Name"]=="Draw")
        {
            console.log("aaa")
            this.searchforshape(stage,jas["IDs"].values,layer)
        }
        else if(jas["Name"]=="Edit" )
       {
        this.searchforshape(stage,jas["IDs"].values,layer)
        this.create(jas,layer)
       }
      else if(jas["Name"]=="Delete")
      {
        this.create(jas,layer)
      }

    }
    redo( stage : Konva.Stage , strR: string,layer:Konva.Layer)
    {
        console.log(strR)
        
        var jas = JSON.parse(strR)
        console.log(jas["Name"])
        console.log(jas)

        if(jas["Name"]=="Draw")
        {
            console.log("aaa")
            this.create(jas,layer)
        }
        else if(jas["Name"]=="Edit" )
       {
        this.searchforshape(stage,jas["IDs"].values,layer)
        this.create(jas,layer)
       }
      else if(jas["Name"]=="Delete")
      {
        this.searchforshape(stage,jas["IDs"].values,layer)
      }
    } 

}
export default UndoRedo