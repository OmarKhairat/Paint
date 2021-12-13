import Konva from 'Konva';
import Convert from './convert';
import {HttpClient} from '@angular/common/http';
import UndoRedo from './undoRedo';
import Load from './load';
class Request{
    copyIDs: any
    selectedPos: any

    convert : Convert = new Convert
    undo =new UndoRedo
    load = new Load
    constructor(private http: HttpClient){}


    createRequest(shape: Konva.Shape){
        var jas = shape.toJSON()
        this.http.get('http://localhost:8080/controller/draw',{
          responseType:'text',
          params:{
              first:jas
          },
          observe:'response'
        })
        .subscribe(response=>{
        
          var id = response.body!
          shape.setAttr("id", id)
          console.log(shape.getAttr("id"))
        
        })
    }

    editRequest(shapes: Konva.Shape[]){
      for(let i=0 ; i<shapes.length ; i++){
        var shape = shapes[i]
        var jas = shape.toJSON()
        this.http.get('http://localhost:8080/controller/edit',{
          responseType:'text',
          params:{
              shape:jas,
              id : shape.getAttr("id")
          },
          observe:'response'
        })
        .subscribe(response=>{
          console.log(response.body!)
        })
      }
    }

    deleteReqest(shapes: Konva.Shape[]){
      for(let i=0 ; i<shapes.length ; i++){
        var shape = shapes[i]
        this.http.get('http://localhost:8080/controller/delete',{
          responseType:'text',
          params:{
              id : shape.getAttr("id")
          },
          observe:'response'
        })
        .subscribe(response=>{
          console.log(response.body!)
        })
      }

    }

    copyRequest(shapes: Konva.Shape[], selectedpos:[]){
      if(shapes.length == 0)
        return

      this.selectedPos = selectedpos
      let ids :string[] = []
      ids.length = shapes.length
      for(let i=0 ; i<shapes.length ; i++){
        var shape = shapes[i]
        ids[i] = shape.getAttr("id")
      }
      var jasIDs ='{"Id":'.concat(JSON.stringify(ids)).concat('}')

      this.http.get('http://localhost:8080/controller/Copy',{
        responseType:'text',
        params:{
            id : jasIDs
        },
        observe:'response'
      })
      .subscribe(response=>{
        console.log(response.body!)
        this.copyIDs = JSON.parse(response.body!).values
        
      })
    }

    pastRequest(corsurPos: number[], layer: Konva.Layer){
      if(corsurPos[1]<0){
        corsurPos[0] = 100
        corsurPos[1] = 100
      }
      var jassend = {
        IDs : this.copyIDs,
        deltaX : corsurPos[0]- this.selectedPos[0],
        deltaY : corsurPos[1]- this.selectedPos[1]
      }
      var str = JSON.stringify(jassend)

      this.http.get('http://localhost:8080/controller/Paste',{
        responseType:'text',
        params:{
            JString : str
        },
        observe:'response'
      })
      .subscribe(response=>{
        console.log(response.body!)
        this.convert.jsonToShapes(response.body!, layer)
      })
    }

    undorequest(stage: Konva.Stage, layer:Konva.Layer)
    {
      this.http.get('http://localhost:8080/controller/undo',{
        responseType:'text',
        params:{   
        },
        observe:'response'
      })
      .subscribe(response=>{
        console.log(response.body!) 
        if(response.body! != "Error"){
          this.undo.undo(stage ,response.body! ,layer)
        }
      })

    }
    redorequest(stage: Konva.Stage, layer:Konva.Layer)
    {
      this.http.get('http://localhost:8080/controller/redo',{
        responseType:'text',
        params:{   
        },
        observe:'response'
      })
      .subscribe(response=>{
        console.log(response.body!) 
        if(response.body! != "Error"){
          this.undo.redo(stage ,response.body! ,layer)
        }
      })

    }
    saverequest()
    {
      this.http.get('http://localhost:8080/controller/save',{
        params:{   
        },
      }).subscribe(response=>{
         })
    }
    loadrequest( layer: Konva.Layer)
    {
      this.http.get('http://localhost:8080/controller/load',{
        responseType:'text',
        params:{   
        },
        observe:'response'
      }).subscribe(response=>{
        
        this.load.doload(layer , response.body!)
      })
    }

}
export default Request