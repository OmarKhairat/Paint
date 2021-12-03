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
    create(name:string)
    {
      this.b = this.shapeCreator.createShape('circle',this.colors)
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