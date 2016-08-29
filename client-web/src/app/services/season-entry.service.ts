import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class SeasonEntryService {

  constructor(@Inject(Http) private http: Http) {
  }

  getSeasonEntries(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-season-entries", {}).map(
          function(response : Response) {
              return response.json();
          });
  }
}
