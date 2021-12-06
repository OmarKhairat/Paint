import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Shapes{
    shape: any
    clor:string="white"
    createShape(name: string, color: string, x:number, y:number)
    {
        console.log('in')

        switch (name){
            case 'circle':{
                this.shape = this.circle(color, x, y)
                return this.shape
            }
            case 'ellipse':{
                this.shape = this.ellipse(color, x, y)
                return this.shape
            }

            case 'rectangle':{
                this.shape = this.rectangle(color, x, y)
                return this.shape
            }

            case 'square':{
                this.shape = this.square(color, x, y)
                return this.shape
            }

            case 'triangle':{
                this.shape = this.triangle(color, x, y)
                return this.shape
            }

            case 'line':{
                this.shape = this.line(color, x, y)
                return this.shape
            }

        }

    }

    circle( color:string, x:number, y:number)
    {                            
      var cir = new Konva.Circle({
          fill:this.clor,
        x: x,
        y: y,
        radius:90,
        stroke:color,
        strokeWidth:2,
        draggable:true,
        name:'rect'
      });
      return cir
    }

    rectangle(color:string, x:number, y:number)
    {
        var rect = new Konva.Rect({
            fill:this.clor,
            x:x,
            y:y,
            width: 150,
            height: 75,
            stroke: color,
            strokeWidth: 2,
            draggable:true,
            name:'rect'

          });
          return rect
    }

    square(color:string, x:number, y:number)
    {
        var sq = new Konva.Rect({
            fill:this.clor,
            x:x,
            y:y,
            width: 100,
            height: 100,
            stroke: color,
            strokeWidth: 2,
            draggable:true,
            name:'rect'

          });
          return sq
    }

    triangle(color:string, x:number, y:number)
    {
        var tri = new Konva.RegularPolygon({
            fill:this.clor,
            x:x,
            y:y,
            sides: 3,
            radius: 70,
            stroke: color,
            strokeWidth: 2,
            draggable:true,
            name:'rect'
        });
        return tri
    }

    ellipse(color:string, x:number, y:number)
    {
        var elps = new Konva.Ellipse({
            fill:this.clor,
            x:x,
            y:y,
            radiusX: 100,
            radiusY: 50,
            stroke: color,
            strokeWidth: 2,
            draggable:true,
            name:'rect'
        });
        return elps
    }

    line(color: string, x:number, y:number){
        var line = new Konva.Line({
            points: [x,y,x+150,y],
            stroke: color,
            tension: 1,
            draggable: true,
            name:'rect'  
        });

        return line

    }


    
}
export default Shapes;
