import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';
import Shapes from "./shapes";
import Operation from "./operation";
import Draw from "./freeDraw"
import Colors from "./colors"
import Selecting from "./selecting"


@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })

  export class homecomponent implements OnInit {
    b:any

    shapeCreator: any = new Shapes
    operations: any = new Operation
    ColorsOp: any = new Colors
    Selecting: any = new Selecting

    FreeDraw: Draw = new Draw
    drawMode: boolean = false
    drawflag: boolean = false


    stage!: Konva.Stage;
    layer!: Konva.Layer;



    color: string = 'black'


    ngOnInit(): void {    
      this.stage = new Konva.Stage({  //create the stage
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight
      });
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);

      this.Selecting.initiate()

      this.stage.on('mousedown', (e) => {
        if(this.drawMode){
            this.FreeDraw.startDraw(this.layer,this.color)
            this.drawflag = true
        }else{
          if (e.target !==this.stage){
            return
          }
          this.Selecting.mouseDown(e , this.stage)
        }
      });

      this.stage.on('mousemove touchmove', (e) => {
        if(this.drawMode){
          this.FreeDraw.draw(this.layer,this.color)
        }else{
          this.Selecting.mouseMove(e , this.stage)
        }        
      
      });

      this.stage.on('mouseup', (e) => {
        if(this.drawMode){
          this.FreeDraw.endDraw()
          this.b = this.FreeDraw.line
        }else{
          this.Selecting.mouseUp(e , this.stage)
        } 
      });

      this.stage.on('click',  (e)=> {
        this.Selecting.click(e , this.stage)
      }); 
      
    }

    //for doing the event
    colour( y:string)
    {
      this.color=y
      
    }

    create(name:string)
    {
      if(this.drawMode)
        this.changeDrawMode()
      var shift = this.operations.checkForShift(this.layer , name)
      this.b = this.shapeCreator.createShape(name,this.color, 150+shift, 150+shift)
      console.log(this.color)
      console.log(this.b)
      this.layer.add(this.b)
      this.addSelection()
    }

    addSelection(){
      this.Selecting.selectedShapes = [this.b]
      this.layer.add(this.Selecting.tr)
      this.Selecting.tr.nodes([this.b])
      this.layer.add(this.Selecting.selected)
    }
 
    changeDrawMode()
    {
      this.drawMode = !this.drawMode
      if(this.drawMode){
        this.Selecting.emptytr()
        this.drawflag = false
      }else if(this.drawflag)
        this.addSelection()

    }

    remove()
    {
      this.operations.delete(this.Selecting.selectedShapes)
      this.Selecting.emptytr()

    }
    fill()
    {
      this.ColorsOp.full(this.Selecting.selectedShapes,this.color)
    }
    changecolr()
    {
      if(!this.drawMode){
        this.ColorsOp.changeColor(this.Selecting.selectedShapes,this.color)
      }
    }
  }