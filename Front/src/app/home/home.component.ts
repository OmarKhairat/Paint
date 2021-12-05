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
    stage!: Konva.Stage;
    layer!: Konva.Layer;
    tr:any
    selected:any
    x1:any
    x2:any
    y1:any
    y2:any
    title = 'colorPicker';
    color: string = 'black'
    arrayColors: any = {
      color1: '#2883e9',
      color2: '#e920e9',
      color3: 'rgb(255,245,0)',
      color4: 'rgb(236,64,64)',
      color5: 'rgba(45,208,45,1)'
    };
    selectedColor: string = 'color1';
    ngOnInit(): void {    
      this.stage = new Konva.Stage({
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight
      });
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);
      this.tr= new Konva.Transformer();
      this.selected = new Konva.Rect({
        fill: 'rgba(0,0,55,0.5)',
        visible: false,
      });
      this.stage.on('mousedown touchstart', (e) => {
        if (e.target !== this.stage) {
          if(e.target.getAttr('stroke') != this.color)
          {
            e.target.setAttr('stroke',this.color).draw()
          }
          return;
        }
        e.evt.preventDefault();
        this.x1 = this.stage.getPointerPosition()?.x;
        this.y1 = this.stage.getPointerPosition()?.y;
        this.x2 = this.stage.getPointerPosition()?.x;
        this.y2 = this.stage.getPointerPosition()?.y;
        this.selected.visible(true);
        this.selected.width(0);
        this.selected.height(0);
      });
      this.stage.on('mousemove touchmove', (e) => {
        if (!this.selected.visible()) {
          return;
        }
        e.evt.preventDefault();
        this.x2 =  this.stage.getPointerPosition()?.x;
        this.y2 = this.stage.getPointerPosition()?.y;
        this.selected.setAttrs({
          x: Math.min(this.x1, this.x2),
          y: Math.min(this.y1, this.y2),
          width: Math.abs(this.x2 - this.x1),
          height: Math.abs(this.y2 - this.y1),
        });
      });
      this.stage.on('mouseup touchend', (e) => {
        if (!this.selected.visible()) {
          return;
        }
        e.evt.preventDefault();
        setTimeout(() => {
          this.selected.visible(false);          
        });
        var shapes = this.stage.find('.rect');
        var box = this.selected.getClientRect();
        var select = shapes.filter((shape) =>
          Konva.Util.haveIntersection(box, shape.getClientRect())
        );
        this.tr.nodes(select);
      });
      this.stage.on('click tap',  (e)=> {
        if (this.selected.visible()) {
          return;
        }
        if (e.target === this.stage) {
          this.tr.nodes([]);
          return;
        }
        if (!e.target.hasName('rect')) {
          return;
        }
        const metaPressed = e.evt.shiftKey || e.evt.ctrlKey || e.evt.metaKey;
        const isSelected = this.tr.nodes().indexOf(e.target) >= 0;
        if (!metaPressed && !isSelected) {
          this.tr.nodes([e.target]);
        } else if (metaPressed && isSelected) {
          const nodes = this.tr.nodes().slice(); 
          nodes.splice(nodes.indexOf(e.target), 1);
          this.tr.nodes(nodes);
        } else if (metaPressed && !isSelected) {
          const nodes = this.tr.nodes().concat([e.target]);
          this.tr.nodes(nodes);
        }
      }); 
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
    colour( y:string)
    {
      this.color=y
    }
    create(name:string)
    {
      this.checkForShift()
      this.b = this.shapeCreator.createShape(name,this.color, 150+this.shift, 150+this.shift)
      console.log(this.colors)
      console.log(this.b)
      this.layer.add(this.b)
      this.layer.add(this.tr)
      this.tr.nodes([this.b])
      this.layer.add(this.selected)
    }
    remove()
    {
      this.layer.remove();
      this.layer = new Konva.Layer;
      this.stage.add(this.layer);
    }
  }