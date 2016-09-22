import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {IConnectionType} from "../model/IConnectionType";

@Injectable()
export class ConnectionTypeService {

    constructor(@Inject(Http) private http: Http) {
    }

    getConnectionTypes() {
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-connection-types", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    getConnectionCategories(){
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-connection-categories", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    updateConnectionType(_connectionType: IConnectionType) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/update-connection-type", _connectionType);
    }
}
