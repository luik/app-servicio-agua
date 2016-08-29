import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {IConnection} from "../model/IConnection";

@Injectable()
export class MeasureStampService {

  constructor(@Inject(Http) private http: Http) {
  }

  getMeasureStamps(_connection: IConnection){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-measure-stamps", _connection).map(
          function(response : Response) {
              return response.json();
          });
  }
}
