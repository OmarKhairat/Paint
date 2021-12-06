import Konva from 'Konva';
import {HttpClient} from '@angular/common/http';
class Request{
    constructor(public http: HttpClient){}
    request(shape: Konva.Shape){
        var jas = shape.toJSON()
        console.log(jas)
        console.log(typeof jas)

    }

    createRequest(shape: Konva.Shape){
        var jas = shape.toJSON()

        //id <=
    }

    editRequest(shape: Konva.Shape){

    }

    deleteReqest(shape: Konva.Shape){

    }

}
export default Request