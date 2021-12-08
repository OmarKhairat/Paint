import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Draw{
    drawing: boolean = false
    line! : Konva.Line


    startDraw(layer: Konva.Layer , color: string ,stroke:number ){
        
        this.drawing=true
        var stage = layer.getStage()
        var pos = stage.getPointerPosition()! ;


        this.line = new Konva.Line({
            x:1e-17,
            y:1e-17,
            stroke: color,
            strokeWidth: stroke,
            globalCompositeOperation:'source-over' ,
            lineCap: 'round',
            points: [pos.x, pos.y , pos.x, pos.y],
            scaleX: 0.999999,
            scaleY: 0.999999,
            skewX: 1e-17,
            rotation: 1e-17,
            name:'rect',
            draggable: true,
            id: "-1"

          });
          layer.add(this.line);
          
    }

    draw(layer: Konva.Layer ){
        
        if (!this.drawing){
            return;
        }
        
        var stage = layer.getStage()
        var pos = stage.getPointerPosition()!

        var points = this.line.points().concat([pos.x, pos.y]);
        this.line.points(points);
        
    }

    endDraw(){
        this.drawing = false
    }

}
export default Draw;
