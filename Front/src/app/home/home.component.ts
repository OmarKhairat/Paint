import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';
import Shapes from "./shapes";

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })
  export class homecomponent implements OnInit {
    b:any
    shift: number = 0
    shapeCreator: any = new Shapes
    shapes: any = [];
    stage!: Konva.Stage;
    layer!: Konva.Layer;
    ngOnInit(): void {
    
      this.stage = new Konva.Stage({
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight
      });
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);
   
      
    }
    colors:string='black'

    checkForShift(){
      var children = this.layer.getChildren();
      let n:number = 0 
      this.shift = 0
      for(let i=0 ; i<children.length ; i++){
        if(children[i].getClassName() != 'Line'){
            if(children[i].x() == 150 + this.shift && children[i].y() == 150 + this.shift){
              this.shift += 10
            }
        }
        
      }

    }

    create(name:string)
    {
      this.checkForShift()
      this.b = this.shapeCreator.createShape(name,this.colors, 150+this.shift, 150+this.shift)
      console.log(this.colors)
      console.log(this.b)
      this.layer.add(this.b)
    }

 
    remove()
    {
      this.layer.remove();
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);
    }
  }