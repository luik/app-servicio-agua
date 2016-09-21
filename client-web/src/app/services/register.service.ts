import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {IRegister} from "../model/IRegister";

@Injectable()
export class RegisterService {

    constructor(@Inject(Http) private http: Http) {
    }

    getRegisters() {
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-registers", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    getAvailableRegisters() {
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-available-registers", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    addRegister(_register: IRegister) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/add-register", _register);
    }

    updateRegister(_register: IRegister) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/update-register", _register);
    }
}
