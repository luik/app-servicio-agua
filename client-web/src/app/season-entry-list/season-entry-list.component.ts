import {Component, OnInit} from '@angular/core';
import {SeasonEntryService} from "../services/season-entry.service";
import {ISeasonEntry} from "../model/ISeasonEntry";

@Component({
    selector: 'app-season-entry-list',
    templateUrl: 'season-entry-list.component.html',
    styleUrls: ['season-entry-list.component.css']
})
export class SeasonEntryListComponent implements OnInit {

    seasonEntries: ISeasonEntry[];
    selectedSeasonEntry: ISeasonEntry;

    constructor(private seasonEntryService: SeasonEntryService) {
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
    }
}
