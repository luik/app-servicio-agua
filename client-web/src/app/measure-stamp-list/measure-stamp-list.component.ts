import {Component, OnInit} from '@angular/core';
import {IMeasureStamp} from "../model/IMeasureStamp";
import {MeasureStampService} from "../services/measure-stamp.service";
import {ActivatedRoute} from "@angular/router";
import {MenuItem, Message} from "primeng/components/common/api";
import {ConfigApp} from "../configApp";

@Component({
    selector: 'app-measure-stamp-list',
    templateUrl: 'measure-stamp-list.component.html',
    styleUrls: ['measure-stamp-list.component.css']
})
export class MeasureStampListComponent implements OnInit {

    measureStamps: IMeasureStamp[];
    selectedMeasureStamp: IMeasureStamp;

    isUploadingMeasurementsExcel: boolean = false;

    items: MenuItem[];

    uploadURL: string;

    isNotifying: boolean = false;
    uploadMessages: Message[];

    constructor(
        private measureStampService: MeasureStampService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.measureStampService.getMeasureStamps(this.route.snapshot.params["by"], {id: parseInt(this.route.snapshot.params["id"])}).subscribe(
            measureStamps =>
            {
                this.measureStamps = measureStamps as Array<IMeasureStamp>;
            }
        )

        this.items = [
            {label: "Plantilla excel de lecturas", icon: "fa-file-excel-o", command: event => {
                window.open(ConfigApp.WS_HOST + "/ws/" + this.route.snapshot.params["by"] + "/get-measure-stamps/excel/" + this.route.snapshot.params["id"]);
            }},
            {label: "Subir lecturas en excel", icon: "fa-upload", command: event => {
                this.isUploadingMeasurementsExcel = true;
            }}
        ];

        this.uploadURL = ConfigApp.WS_HOST + "/ws/" + this.route.snapshot.params["by"] + "/upload-measure-stamps/excel/" + this.route.snapshot.params["id"];
    }

    save(){
        this.measureStampService.saveMeasureStamps(this.measureStamps.filter(measureStamp => measureStamp.value != measureStamp.modifiedValue)).subscribe(
            measureStamps =>
            {
                this.ngOnInit();
            }
        )
    }

    onUpload(event) {

        this.uploadMessages = [];

        if(event.xhr.responseText === "OK"){
            this.uploadMessages.push({ severity: 'info', summary: 'El excel se proceso correctamente', detail: ''});
        }
        else{
            this.uploadMessages.push({ severity: 'error', summary: event.xhr.responseText, detail: ''});
        }

        this.isUploadingMeasurementsExcel = false;
        this.isNotifying = true;

        this.ngOnInit();
    }
}
