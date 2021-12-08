import Konva from 'Konva';
import {HttpClient} from '@angular/common/http';
class Request{
    constructor(private http: HttpClient){}
    request(shape: Konva.Shape){
        var jas = shape.toJSON()
        console.log(jas)
        console.log(typeof jas)

    }

    createRequest(shape: Konva.Shape){
        var jas = shape.toJSON()
        this.http.get('http://localhost:8888/controller/draw',{
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
        this.http.get('http://localhost:8888/controller/edit',{
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
        this.http.get('http://localhost:8888/controller/delete',{
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

}
export default Request