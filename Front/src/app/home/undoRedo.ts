import Konva from 'Konva';
import Operation from "./operation";
import Convert from './convert';
class UndoRedo{
    check:boolean=false
    operations: any = new Operation
    convert: any = new Convert
    
    searchforshape( stage : Konva.Stage, ids:string[] , layer:Konva.Layer)
    {
        for(let i=0 ;i<ids.length;i++)
        {
            var id = ids[i]
            var ch = layer.getChildren(function(node){
                console.log(node.getAttr('id'))
                return node.getAttr('id')==id.toString() ;
             })
             this.operations.delete(ch)
        }
   
    }

    create(shape:any , layer: Konva.Layer)
    {
        var jas = shape
        delete jas.Name ;
        var str = JSON.stringify(jas)
        this.convert.jsonToShapes(str, layer)


    }
    undo(stage : Konva.Stage , strR: string,layer:Konva.Layer)
    {
        var jas = JSON.parse(strR)

        if(jas["Name"]=="Draw")
        {
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
        
        var jas = JSON.parse(strR)

        if(jas["Name"]=="Draw")
        {
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