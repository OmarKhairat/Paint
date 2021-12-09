import Konva from 'Konva';

class Convert{

    jsonToShapes(strJson: string, layer: Konva.Layer){
        var jas = JSON.parse(strJson)
        for(var id in jas){
            if(id == "IDs"){
                continue
            }
            var jsonshapeStr = JSON.stringify(jas[id])
            console.log(jsonshapeStr)
            var shape = Konva.Node.create(jsonshapeStr, 'container')
            console.log(id)
            shape.setAttr("draggable", false)

            shape.setAttr("id", id)
            console.log(shape)

            layer.add(shape)
        }


    }

}
export default Convert