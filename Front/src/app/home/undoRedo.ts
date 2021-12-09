import Konva from 'Konva';
import Operation from "./operation";
import Convert from './convert';

class UndoRedo{
    check:boolean=false
    operations: any = new Operation
    convert: any = new Convert

    searchforshape( stage : Konva.Stage, id:string)
    {
        
        var ch = stage.getChildren(function(node){
            return node.getAttr("id")==id ;
         })
         this.operations.delete(ch)
    }

    create(shape:string , layer: Konva.Layer)
    {
        var jas = JSON.parse(shape)
        delete jas.Name ;
        var str = JSON.stringify(jas)
        this.convert.jsonToShapes(str, layer)


    }
    undo(stage : Konva.Stage , strR: string,layer:Konva.Layer)
    {
        var jas = JSON.parse(strR)
        if(jas["name"]=="Draw")
        {
            this.searchforshape(stage,strR)
        }
        else if(jas["name"]=="Edit" )
       {
        this.searchforshape(stage,strR)
        this.create(strR,layer)
       }
      else if(jas["name"]=="Delete")
      {
        this.create(strR,layer)
      }

    }

}
export default UndoRedo