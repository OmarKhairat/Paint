import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Draw{
    drawing: boolean = false
    line! : Konva.Line


    startDraw(layer: Konva.Layer , color: string){
        
        this.drawing=true
        var stage = layer.getStage()
        var pos = stage.getPointerPosition()! ;


        this.line = new Konva.Line({
            stroke: color,
            strokeWidth: 3,
            globalCompositeOperation:'source-over' ,
            lineCap: 'round',
            points: [pos.x, pos.y , pos.x, pos.y],
            name:'rect',
            draggable: true
          });
          layer.add(this.line);
          
    }

    draw(layer: Konva.Layer , color: string){
        
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
