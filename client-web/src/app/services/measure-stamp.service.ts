import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {IConnection} from "../model/IConnection";
import {IMeasureStamp} from "../model/IMeasureStamp";

@Injectable()
export class MeasureStampService {

  constructor(@Inject(Http) private http: Http) {
  }

  getMeasureStamps(_byCriteria: string,  _entity: any){
      return this.http.post(ConfigApp.WS_HOST + "/ws/" + _byCriteria + "/get-measure-stamps", _entity).map(
          function(response : Response) {
              return response.json();
          });
  }

    saveMeasureStamps(_measureStamps: IMeasureStamp[]){
        return this.http.post(ConfigApp.WS_HOST + "/ws/save-measure-stamps", _measureStamps);
    }
}
