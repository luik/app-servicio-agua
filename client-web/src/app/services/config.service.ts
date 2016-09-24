import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {IConfig} from "../model/IConfig";

@Injectable()
export class ConfigService {

    constructor(@Inject(Http) private http: Http) {
    }

    getConfigs() {
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-configs", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    updateConfig(_config: IConfig) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/update-config", _config);
    }
}
