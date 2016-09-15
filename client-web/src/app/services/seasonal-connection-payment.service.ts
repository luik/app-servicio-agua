import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class SeasonalConnectionPaymentService {

    constructor(@Inject(Http) private http: Http) {
    }

    getSeasonalConnectionPayments(_byCriteria: string,  _entity: any) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/" + _byCriteria + "/get-seasonal-connection-payments", _entity).map(
            function (response: Response) {
                return response.json();
            });
    }
}
