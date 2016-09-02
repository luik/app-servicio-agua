import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {ISeasonEntry} from "../model/ISeasonEntry";
import {Config} from "protractor/built/configParser";

@Injectable()
export class SeasonalConnectionDebtService {

    constructor(@Inject(Http) private http: Http) {
    }

    getSeasonalConnectionDebts(_byCriteria: string,  _entity: any) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/" + _byCriteria + "/get-seasonal-connection-debts", _entity).map(
            function (response: Response) {
                return response.json();
            });
    }

    generateSeasonalConnectionDebts(_byCriteria, _entity: any){
        console.log("generate", _byCriteria, _entity, ConfigApp.WS_HOST + "/ws/" + _byCriteria + "/generate-seasonal-connection-debts");
        return this.http.post(ConfigApp.WS_HOST + "/ws/" + _byCriteria + "/generate-seasonal-connection-debts", _entity).map(
            function (response: Response) {
                return response.json();
            });
    }

}
