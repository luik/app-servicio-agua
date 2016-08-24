import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class RegisterService {

  constructor(@Inject(Http) private http: Http) {
  }

  getRegisters(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-registers", {}).map(
          function(response : Response) {
              return response.json();
          });
  }
}
