import {Component, OnInit} from '@angular/core';
import {ISeasonEntry} from "../model/ISeasonEntry";
import {MenuItem} from "primeng/primeng";
import {ISeasonalConnectionPayment} from "../model/ISeasonalConnectionPayment";
import {SeasonalConnectionPaymentService} from "../services/seasonal-connection-payment.service";
import {ActivatedRoute} from "@angular/router";
import {ConfigApp} from "../configApp";

@Component({
    selector: 'seasonal-connection-payment-list',
    templateUrl: 'seasonal-connection-payment-list.component.html',
    styleUrls: ['seasonal-connection-payment-list.component.css']
})
export class SeasonalConnectionPaymentList implements OnInit {
    isDisplayingDialog: boolean = false;

    seasonalConnectionPayments: ISeasonalConnectionPayment[];
    selectedSeasonalConnectionPayment: ISeasonalConnectionPayment;
    seasonalConnectionPayment: ISeasonalConnectionPayment;

    items: MenuItem[];

    constructor(
        private seasonalConnectionPaymentService: SeasonalConnectionPaymentService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.seasonalConnectionPaymentService.getSeasonalConnectionPayments(this.route.snapshot.params["by"], {id: parseInt(this.route.snapshot.params["id"])}) .subscribe(
            seasonalConnectionPayments =>
            {
                this.seasonalConnectionPayments = seasonalConnectionPayments as Array<ISeasonalConnectionPayment>;
            }
        )

        this.items = [
            {label: "Recibos PDF", icon: "fa-file-excel-o", command: event => {
                    window.open(ConfigApp.WS_HOST + "/ws/" + this.route.snapshot.params["by"] + "/get-seasonal-connection-payments/pdf/" + this.route.snapshot.params["id"]);
                }},
            //{label: "Reporte Excel", icon: "fa-file-word-o"}
        ];
    }

    onRowSelect(event) {
        //this.seasonEntry = this.cloneSeasonEntry(event.data);
        //this.isDisplayingDialog = true;
        //this.items[0].routerLink = ["/measure-stamps/season/" + this.seasonEntry.id];
        //this.items[1].routerLink = ["/seasonal-connection-debts/season/" + this.seasonEntry.id];
    }

    save(){
        //this.seasonEntryService.updateSeasonEntry(this.seasonEntry).subscribe(
        //    response =>
        //    {
        //        this.ngOnInit();
        //        this.isDisplayingDialog = false;
        //    }
        //);
    }
}
