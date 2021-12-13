import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Selecting{

    tr:any   // transformar the square aroud the selected shape
    selected:any  //the big rectange on multible shape selection

    selectedShapes: Konva.Node[] = []
    move:boolean = false
    // for vertices of the selected rectangle
    x1:any
    x2:any
    y1:any
    y2:any


    initiate(){
        this.tr= new Konva.Transformer();
        this.selected = new Konva.Rect({
          fill: 'rgba(0,0,55,0.5)',
          visible: false,
        });
    }

    mouseDown(e:any , stage: Konva.Stage){
        this.editDragable(false)

        e.evt.preventDefault();
        this.x1 = stage.getPointerPosition()?.x;
        this.y1 = stage.getPointerPosition()?.y;
        this.x2 = stage.getPointerPosition()?.x;
        this.y2 = stage.getPointerPosition()?.y;
        this.selected.visible(true);
        this.selected.width(0);
        this.selected.height(0);
    }
     
    mouseMove(e:any , stage: Konva.Stage){
        if (!this.selected.visible()) {
            this.move = true
            return;
          }
          e.evt.preventDefault();
          this.x2 = stage.getPointerPosition()?.x;
          this.y2 = stage.getPointerPosition()?.y;
          this.selected.setAttrs({
            x: Math.min(this.x1, this.x2),
            y: Math.min(this.y1, this.y2),
            width: Math.abs(this.x2 - this.x1),
            height: Math.abs(this.y2 - this.y1),
        });
        this.move = false
    }

    mouseUp(e:any , stage: Konva.Stage){
        if (!this.selected.visible()) {
            return;
          }
          e.evt.preventDefault();
          setTimeout(() => {this.selected.visible(false);});
          var shapes = stage.find('.rect');
          var box = this.selected.getClientRect();
          var select = shapes.filter((shape) =>
            Konva.Util.haveIntersection(box, shape.getClientRect())
          );
          this.tr.nodes(select);
          this.selectedShapes = select
          this.editDragable(true)
          console.log(this.selectedShapes.length)
    }

    click(e:any , stage: Konva.Stage){
          this.editDragable(false)
          if (this.selected.visible()) {
            return;
          }
          if (e.target === stage) {
            this.emptytr()
            return
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
          this.selectedShapes= [e.target]
          this.editDragable(true)
    }

    emptytr(){
        this.tr.nodes([]);
    }
    
    editDragable(check: boolean){
      for(let i=0; i< this.selectedShapes.length ; i++){
        this.selectedShapes[i].setAttr("draggable", check)
      }
    }


}
export default Selecting;
