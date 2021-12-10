import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Colors{

    
    //for HTML
    title = 'colorPicker';
    selectedColor: string = 'color1';


    //for selecting any color from the color map
    arrayColors: any = {
      color1: '#2883e9',
      color2: '#e920e9',
      color3: 'rgb(255,245,0)',
      color4: 'rgb(236,64,64)',
      color5: 'rgba(45,208,45,1)'
    };

    changeColor(shapes: Konva.Shape[], color: string){
      for(let i=0 ; i< shapes.length ; i++)
        shapes[i].setAttr('stroke', color)
    }
    full(shapes: Konva.Shape[],clr:string)
    {
      for(let i=0 ; i< shapes.length ; i++)
         shapes[i].setAttr('fill', clr)
    }
    strokewidth(shapes:Konva.Shape[],stroke:number){
      for(let i=0 ; i< shapes.length ; i++)
        shapes[i].setAttr('strokeWidth', stroke)
    }
}
export default Colors;
