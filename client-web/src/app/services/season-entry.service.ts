import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";
import {ISeasonEntry} from "../model/ISeasonEntry";

@Injectable()
export class SeasonEntryService {

    constructor(@Inject(Http) private http: Http) {
    }

    getSeasonEntries() {
        return this.http.post(ConfigApp.WS_HOST + "/ws/get-season-entries", {}).map(
            function (response: Response) {
                return response.json();
            });
    }

    updateSeasonEntry(_seasonEntry: ISeasonEntry) {
        return this.http.post(ConfigApp.WS_HOST + "/ws/update-season-entry", _seasonEntry);
    }
}
