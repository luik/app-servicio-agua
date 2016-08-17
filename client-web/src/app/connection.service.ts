import { Injectable, Inject } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "./configApp";

@Injectable()
export class ConnectionService {

  constructor(@Inject(Http) private http: Http) { }

    getConnections(){
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-connections", {}).map(
            function(response : Response) {
                return response.json();
            });
    }

}
