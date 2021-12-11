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
import {HotkeysService , Hotkey} from 'angular2-hotkeys';
import UndoRedo from './undoRedo';

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
    copyflag: boolean = false
    Undo:any = new UndoRedo
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
           
            console.log(this.Selecting.tr.nodes())
            if(!inn && this.Selecting.move && this.Selecting.tr.nodes().length != 0){
              console.log(this.Selecting.selectedShapes)
              this.requests.editRequest(this.Selecting.selectedShapes)
              
            }
          }
        } 
        this.deleteAndCopyColor()
      });

      this.stage.on('click',  (e)=> {
        if(e.evt.which == 1){
          this.Selecting.click(e , this.stage)
          this.deleteAndCopyColor()
        }
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
      this.Selecting.editDragable(false)
      if(this.drawMode)
        this.changeDrawMode()
      var shift = this.operations.checkForShift(this.layer , name)
      this.b = this.shapeCreator.createShape(name,this.color, 150+shift, 150+shift,this.stroke)
      console.log(this.color)
      console.log(this.b)
      this.layer.add(this.b)
      this.addSelection()
      this.requests.createRequest(this.b)
      this.deleteAndCopyColor()

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
      this.Selecting.selectedShapes = []
      this.deleteAndCopyColor()

    }

    backGround(){
      if(this.drawMode){
        document.getElementById('draw')!.style.backgroundColor ="#777777";
        console.log("inn")
      }else{
        document.getElementById('draw')!.style.backgroundColor = "rgb(255, 255, 255)";
      }
    }

    deleteAndCopyColor(){
      console.log(this.Selecting.selectedShapes)
      if(this.Selecting.selectedShapes.length !=0){
        document.getElementById('copy')!.style.color ="orange";
        document.getElementById('delete')!.style.color ="orange";
        document.getElementById('copy2')!.style.color ="orange";
        document.getElementById('delete2')!.style.color ="orange";
        document.getElementById('fill')!.style.color ="orange";

      }else{
        document.getElementById('copy')!.style.color ="#111";
        document.getElementById('delete')!.style.color ="#111";
        document.getElementById('copy2')!.style.color ="#111";
        document.getElementById('delete2')!.style.color ="#111";
        document.getElementById('fill')!.style.color ="#111";

      }
    }

    remove()
    {
      this.requests.deleteReqest(this.Selecting.selectedShapes)
      this.operations.delete(this.Selecting.selectedShapes)
      this.Selecting.emptytr()
      this.Selecting.selectedShapes = []
      this.deleteAndCopyColor()

    }
    fill()
    {
      this.ColorsOp.full(this.Selecting.selectedShapes,this.color)
      this.requests.editRequest(this.Selecting.selectedShapes)

    }
    changecolr()
    {
      if(!this.drawMode){
        this.ColorsOp.changeColor(this.Selecting.selectedShapes,this.color)
        this.requests.editRequest(this.Selecting.selectedShapes)
      }

    }

    changeline()
    {
      
      console.log(this.Selecting.selectedShapes)
      this.ColorsOp.strokewidth(this.Selecting.selectedShapes,this.stroke)
      this.requests.editRequest(this.Selecting.selectedShapes)
    }

    copy(){
      console.log(this.Selecting.selectedShapes.length)
      if(this.Selecting.selectedShapes.length !=0){
        this.requests.copyRequest(this.Selecting.selectedShapes, [this.Selecting.tr.getAttr("x"), this.Selecting.tr.getAttr("y")] )

        document.getElementById('copyButton')!.style.backgroundColor ="#777777";
        document.getElementById('paste')!.style.color ="orange";
        document.getElementById('paste2')!.style.color ="orange";
        
        this.copyflag = true
      }
    }
    paste(){
      if(this.copyflag){
        var pos = this.stage.getPointerPosition()!
        var shapes =this.requests.pastRequest([pos.x, pos.y], this.layer)

        document.getElementById('copyButton')!.style.backgroundColor ="rgb(255, 255, 255)";
        document.getElementById('paste')!.style.color ="#111";
        document.getElementById('paste2')!.style.color ="#111";

        this.copyflag = false
      }
    }
    undo()
    {
      this.requests.undorequest(this.stage,this.layer)
      
      this.Selecting.selectedShapes = []
      this.Selecting.emptytr()
      this.deleteAndCopyColor()

    }
    redo()
    {
      this.requests.redorequest(this.stage,this.layer)

      this.Selecting.selectedShapes = []
      this.Selecting.emptytr()
      this.deleteAndCopyColor()

    }
    save()
    {
      this.requests.saverequest()
    }

    load()
    {
      this.requests.loadrequest(this.layer  )
    }


    constructor(public http: HttpClient,private _hotkeysService: HotkeysService){ 
      this._hotkeysService.add(new Hotkey('del', (event: KeyboardEvent): boolean => {
        this.remove();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+c', (event: KeyboardEvent): boolean => {
        this.copy();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+v', (event: KeyboardEvent): boolean => {
        this.paste();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+z', (event: KeyboardEvent): boolean => {
        this.undo();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+y', (event: KeyboardEvent): boolean => {
        this.redo();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+s', (event: KeyboardEvent): boolean => {
        this.save();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('ctrl+l', (event: KeyboardEvent): boolean => {
        this.load();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('f', (event: KeyboardEvent): boolean => {
        this.fill();
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('d', (event: KeyboardEvent): boolean => {
        this.changeDrawMode();
        return false; // Prevent bubbling
      })); 
      this._hotkeysService.add(new Hotkey('s', (event: KeyboardEvent): boolean => {
        this.create("square");
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('r', (event: KeyboardEvent): boolean => {
        this.create("rectangle");
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('l', (event: KeyboardEvent): boolean => {
        this.create("line");
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('c', (event: KeyboardEvent): boolean => {
        this.create("circle");
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('t', (event: KeyboardEvent): boolean => {
        this.create("triangle");
        return false; // Prevent bubbling
      }));
      this._hotkeysService.add(new Hotkey('e', (event: KeyboardEvent): boolean => {
        this.create("ellipse");
        return false; // Prevent bubbling
      }));
    }
  }