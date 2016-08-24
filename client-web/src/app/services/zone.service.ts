import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class ZoneService {

  constructor(@Inject(Http) private http: Http) {
  }

  getZones(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-zones", {}).map(
          function(response : Response) {
              return response.json();
          });
  }
}
