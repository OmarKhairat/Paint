import Konva from 'Konva';

class Convert{

    jsonToShapes(strJson: string, layer: Konva.Layer){
        console.log("in convert")
        var jas = JSON.parse(strJson)
        console.log(jas)
        for(var id in jas){
            if(id == "IDs"){
                continue
            }


            var jsonshapeStr =  jas[id]  
            console.log(jsonshapeStr)
            var shape = Konva.Node.create(jsonshapeStr, 'container')
            shape.setAttr("draggable", false)

            shape.setAttr("id", id)
            console.log(shape)

            layer.add(shape)
            console.log("out convert")

        }


    }

}
export default Convert