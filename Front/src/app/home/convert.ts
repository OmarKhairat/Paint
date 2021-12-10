import Konva from 'Konva';

class Convert{

    jsonToShapes(strJson: string, layer: Konva.Layer){
        var jas = JSON.parse(strJson)
        for(var id in jas){
            if(id == "IDs"){
                continue
            }


            var jsonshapeStr =  jas[id]  
            var shape = Konva.Node.create(jsonshapeStr, 'container')
            shape.setAttr("draggable", false)

            shape.setAttr("id", id)


            layer.add(shape)

        }


    }

}
export default Convert