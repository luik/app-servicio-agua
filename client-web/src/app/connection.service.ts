import { Injectable, Inject } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "./configApp";
import {IConnection} from "./model/IConnection";

@Injectable()
export class ConnectionService {

  constructor(@Inject(Http) private http: Http) { }

    getConnections(){
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-connections", {}).map(
            function(response : Response) {
                return response.json();
            });
    }

    addConnection(_connection: IConnection){
        return this.http.post(ConfigApp.WS_HOST + "/ws/add-connection", _connection);
    }

    updateConnection(_connection: IConnection){
        return this.http.post(ConfigApp.WS_HOST + "/ws/update-connection", _connection);
    }

    deleteConnection(_connection: IConnection){
        return this.http.post(ConfigApp.WS_HOST + "/ws/delete-connection", _connection);
    }

}
