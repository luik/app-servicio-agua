import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class ServiceShutOffService {

  constructor(@Inject(Http) private http: Http) {
  }

  getServiceShutOffs(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-service-shut-offs", {}).map(
          function(response : Response) {
              return response.json();
          });
  }
}
