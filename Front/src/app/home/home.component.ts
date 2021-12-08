import { Component, OnInit ,ViewChild,ElementRef } from '@angular/core';
import Konva from 'Konva';
import Shapes from "./shapes";
import Operation from "./operation";
import Draw from "./freeDraw"
import Colors from "./colors"
import Selecting from "./selecting"
import Request from './request';
import { HttpClient } from '@angular/common/http';
import { observable } from 'rxjs';


@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })

  export class homecomponent implements OnInit {
    
    b:any
    requests:any = new Request(this.http)
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
   stroke:number=3
   @ViewChild('menu ') menu!:ElementRef
   contextMenu(e:any)
   {
     console.log(e.pageX)
     console.log(e.pageY)
     e.preventDefault()
     this.menu.nativeElement.style.display="block"
     this.menu.nativeElement.style.top=e.pageY+"px"
     this.menu.nativeElement.style.left=e.pageX+"px"
     
   }
   disappear()
   {
     this.menu.nativeElement.style.display="none"
   }
   stop(e:any)
   {
     e.stopPropagtion();
   }

    ngOnInit(): void {  
        
      this.stage = new Konva.Stage({  //create the stage
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight
      });
      
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);

      this.Selecting.initiate()
        var inn=false

        this.stage.on('mousedown', (e) => {
        if(this.drawMode){
            this.FreeDraw.startDraw(this.layer,this.color,this.stroke)
            this.drawflag = true
        }else{
          if (e.target !==this.stage){
            console.log("qw")
             inn = true
            return
          }
          this.Selecting.mouseDown(e , this.stage)
        
        }
      });

      this.stage.on('mousemove', (e) => {
        if(this.drawMode){
          this.FreeDraw.draw(this.layer)
        }else{
          
          inn= false
          
          this.Selecting.mouseMove(e , this.stage)
         
        }        
      
      });

      this.stage.on('mouseup', (e) => {
        if(this.drawMode){
          this.FreeDraw.endDraw()
          this.b = this.FreeDraw.line
          this.requests.createRequest(this.b)

        }else{
          
          this.Selecting.mouseUp(e , this.stage)
          if(this.Selecting.selectedShapes.length !=0){
           
           
            if(!inn && this.Selecting.move){
              console.log(inn)
              console.log(this.Selecting.selectedShapes)
              this.requests.editRequest()
              
            }
          }
        } 

      });

      this.stage.on('click',  (e)=> {
        console.log("show")
        this.Selecting.click(e , this.stage)
      }); 
      
    }

    //for doing the event
    colour( y:string)
    {
      this.color=y
      
    }
    strkewidth(y:number )
    {
      this.stroke=y
    }
    create(name:string)
    {
      if(this.drawMode)
        this.changeDrawMode()
      var shift = this.operations.checkForShift(this.layer , name)
      this.b = this.shapeCreator.createShape(name,this.color, 150+shift, 150+shift,this.stroke)
      console.log(this.color)
      console.log(this.b)
      this.layer.add(this.b)
      this.addSelection()
      this.requests.createRequest(this.b)

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

      this.backGround()
    }

    backGround(){
      if(this.drawMode){
        document.getElementById('draw')!.style.backgroundColor ="#777777";
        console.log("inn")
      }else{
        document.getElementById('draw')!.style.backgroundColor = "rgb(255, 255, 255)";
      }
    }

    remove()
    {
      this.operations.delete(this.Selecting.selectedShapes)
      this.Selecting.emptytr()
      this.requests.deleteReqest()

    }
    fill()
    {
      this.ColorsOp.full(this.Selecting.selectedShapes,this.color)
      this.requests.editRequest()

    }
    changecolr()
    {
      if(!this.drawMode){
        this.ColorsOp.changeColor(this.Selecting.selectedShapes,this.color)
        this.requests.editRequest()
      }

    }

    changeline()
    {
      
      console.log(this.Selecting.selectedShapes)
      this.ColorsOp.strokewidth(this.Selecting.selectedShapes,this.stroke)
      this.requests.editRequest()
    }
    changeline()
    {
      
      console.log(this.Selecting.selectedShapes)
      this.ColorsOp.strokewidth(this.Selecting.selectedShapes,this.stroke)
      this.save()
      
    }





    constructor(public http: HttpClient){}


    

  }