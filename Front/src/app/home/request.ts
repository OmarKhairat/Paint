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
        var jas = shape.toJSON()
        this.http.get('http://localhost:8080/draw/shape',{
          responseType:'text',
          params:{
              first:jas
          },
          observe:'response'
        })
        .subscribe(response=>{
        
          console.log(response.body!)
        
        })
        //id <=
    }

    editRequest(shape: Konva.Shape){

    }

    deleteReqest(shape: Konva.Shape){

    }

}
export default Request