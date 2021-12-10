import Konva from 'Konva';
import Convert from './convert';

class Load{
    convert: any = new Convert

    doload(layer: Konva.Layer,strLoad: string){

        var ch = layer.getChildren(function(node){
            console.log(node.getAttr('name'))
            return node.getAttr('name')=='rect' ;
        })
        for(let i=0 ; i< ch.length ; i++){
            ch[i].destroy()
        }

        this.convert.jsonToShapes(strLoad , layer)
    }


}
export default Load