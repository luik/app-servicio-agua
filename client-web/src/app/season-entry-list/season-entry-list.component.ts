import {Component, OnInit} from '@angular/core';
import {SeasonEntryService} from "../services/season-entry.service";
import {ISeasonEntry} from "../model/ISeasonEntry";
import {MenuItem} from "primeng/primeng";
import {SeasonalConnectionDebtService} from "../services/seasonal-connection-debt.service";

@Component({
    selector: 'app-season-entry-list',
    templateUrl: 'season-entry-list.component.html',
    styleUrls: ['season-entry-list.component.css']
})
export class SeasonEntryListComponent implements OnInit {
    isDisplayingDialog: boolean = false;

    seasonEntries: ISeasonEntry[];
    selectedSeasonEntry: ISeasonEntry;
    seasonEntry: ISeasonEntry;

    items: MenuItem[];

    isGeneratingSeasonalConnectionDebts: boolean = false;

    constructor(
        private seasonEntryService: SeasonEntryService,
        private seasonalConnectionDebtService: SeasonalConnectionDebtService
        ) {
    }

    ngOnInit(): void {
        this.seasonEntryService.getSeasonEntries() .subscribe(
            seasonEntries =>
            {
                this.seasonEntries = seasonEntries as Array<ISeasonEntry>;
            }
        )


    }

    onRowSelect(event) {
        this.seasonEntry = this.cloneSeasonEntry(event.data);
        this.isDisplayingDialog = true;

        if(this.seasonEntry.id <= 1){
            this.items = [
                {label: "Medidas", icon: "fa-search"}];
        }
        else{
            this.items = [
                {label: "Medidas", icon: "fa-search"},
                {label: "Cobros", icon: "fa-search"},
                {label: "Generar cobros", icon: "fa-gear"}
            ];

            this.items[1].routerLink = ["/seasonal-connection-debts/season/" + this.seasonEntry.id];
            this.items[2].command = (event) => this.onGenerateSeasonalConnectionDebts(event);
        }

        this.items[0].routerLink = ["/measure-stamps/season/" + this.seasonEntry.id];
    }

    onGenerateSeasonalConnectionDebts(event){
        this.isGeneratingSeasonalConnectionDebts = true;

        this.seasonalConnectionDebtService.generateSeasonalConnectionDebts("season", this.seasonEntry).subscribe(
            result => {
                this.isGeneratingSeasonalConnectionDebts = false;
                this.isDisplayingDialog = false;
            }
        );
    }

    cloneSeasonEntry(seasonEntry: ISeasonEntry): ISeasonEntry {
        let newSeasonEntry = <ISeasonEntry>{};
        for(let prop in seasonEntry) {
            newSeasonEntry[prop] = seasonEntry[prop];
        }
        return newSeasonEntry;
    }

    save(){
        this.seasonEntryService.updateSeasonEntry(this.seasonEntry).subscribe(
            response =>
            {
                this.ngOnInit();
                this.isDisplayingDialog = false;
            }
        );
    }
}
