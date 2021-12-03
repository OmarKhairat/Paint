import { Component, OnInit } from '@angular/core';
import Konva from 'Konva';

class Shapes{
    shape: any

    createShape(name: string, color: string)
    {
        console.log('in')

        switch (name){
            case 'circle':{
                this.circle(color)
                console.log(this.shape)
                return this.shape
            }

        }

    }

    circle( color:string)
    {
                                    
        this.shape= new Konva.Circle({
        x:150,
        y:150,
        radius:90,
        stroke:color,
        strokeWidth:2,
        draggable:true,
        
      });
      
    }


    
}
export default Shapes;
