import {Component, OnInit} from '@angular/core';
import {ISeasonEntry} from "../model/ISeasonEntry";
import {MenuItem} from "primeng/primeng";
import {ISeasonalConnectionDebt} from "../model/ISeasonalConnectionDebt";
import {SeasonalConnectionDebtService} from "../services/seasonal-connection-debt.service";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'seasonal-connection-debt-list',
    templateUrl: 'seasonal-connection-debt-list.component.html',
    styleUrls: ['seasonal-connection-debt-list.component.css']
})
export class SeasonalConnectionDebtList implements OnInit {
    isDisplayingDialog: boolean = false;

    seasonalConnectionDebts: ISeasonalConnectionDebt[];
    selectedSeasonalConnectionDebt: ISeasonalConnectionDebt;
    seasonalConnectionDebt: ISeasonalConnectionDebt;

    items: MenuItem[];

    constructor(
        private seasonalConnectionDebtService: SeasonalConnectionDebtService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.seasonalConnectionDebtService.getSeasonalConnectionDebts(this.route.snapshot.params["by"], {id: parseInt(this.route.snapshot.params["id"])}) .subscribe(
            seasonalConnectionDebts =>
            {
                this.seasonalConnectionDebts = seasonalConnectionDebts as Array<ISeasonalConnectionDebt>;
            }
        )

        this.items = [
            {label: "Medidas", icon: "fa-search"},
            {label: "Cobros", icon: "fa-search"}
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
