import {Component, OnInit} from '@angular/core';
import {IMeasureStamp} from "../model/IMeasureStamp";
import {MeasureStampService} from "../services/measure-stamp.service";
import {IConnection} from "../model/IConnection";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-measure-stamp-list',
    templateUrl: 'measure-stamp-list.component.html',
    styleUrls: ['measure-stamp-list.component.css']
})
export class MeasureStampListComponent implements OnInit {

    measureStamps: IMeasureStamp[];
    selectedMeasureStamp: IMeasureStamp;

    constructor(
        private measureStampService: MeasureStampService,
        private route: ActivatedRoute,) {
    }

    ngOnInit(): void {
        this.measureStampService.getMeasureStamps(<IConnection>{id: parseInt(this.route.snapshot.params["id"])}).subscribe(
            measureStamps =>
            {
                this.measureStamps = measureStamps as Array<IMeasureStamp>;
            }
        )
    }

    onRowSelect(event) {
    }
}
