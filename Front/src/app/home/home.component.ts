import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';
@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })
  export class homecomponent implements OnInit {
    b:any
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
    mohsen()
    {
      this.circle( this.colors)
      console.log(this.colors)
      this.layer.add(this.b)
    }
    circle( color:string)
    {
                                    
        this.b= new Konva.Circle({
        x:150,
        y:150,
        radius:90,
        stroke:color,
        strokeWidth:2,
        draggable:true,
        
        
      });
      
      console.log(this.b)
    }
 
    remove()
    {
      this.layer.remove();
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);
      this.colors='red'
      this.mohsen()
      
    }
  }