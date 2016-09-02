import {NgModule}      from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule}    from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent, routing}  from './app.component';
import {CustomerService} from './services/customer.service';
import {ConnectionService} from './services/connection.service';
import {InputTextModule,DataTableModule,ButtonModule,DialogModule,
    DropdownModule, AutoCompleteModule, CheckboxModule, InputSwitchModule,
    ToggleButtonModule, CalendarModule, MenuModule, MenubarModule
} from 'primeng/primeng';
import {CustomerListComponent} from "./customer-list/customer-list.component";
import {ConnectionListComponent} from "./connection-list/connection-list.component";
import {WelcomeComponent} from "./welcome/welcome.component";
import {RegisterService} from "./services/register.service";
import {ZoneService} from "./services/zone.service";
import {SeasonEntryService} from "./services/season-entry.service";
import {MeasureStampService} from "./services/measure-stamp.service";
import {SeasonEntryListComponent} from "./season-entry-list/season-entry-list.component";
import {MeasureStampListComponent} from "./measure-stamp-list/measure-stamp-list.component";
import {SeasonalConnectionDebtList} from "./seasonal-connection-debt-list/seasonal-connection-debt-list.component";
import {SeasonalConnectionDebtService} from "./services/seasonal-connection-debt.service";

@NgModule({
    imports:      [routing, HttpModule, BrowserModule, FormsModule, HttpModule,
        InputTextModule, DataTableModule, ButtonModule, DialogModule, DropdownModule, AutoCompleteModule,
        CheckboxModule, InputSwitchModule, ToggleButtonModule, CalendarModule, MenuModule, MenubarModule
    ],
    declarations: [AppComponent, CustomerListComponent, ConnectionListComponent, WelcomeComponent,
        SeasonEntryListComponent, MeasureStampListComponent, SeasonalConnectionDebtList
    ],
    bootstrap:    [AppComponent],
    providers:    [CustomerService, ConnectionService, RegisterService, ZoneService,
        SeasonEntryService, MeasureStampService, SeasonalConnectionDebtService
    ]
})
export class AppModule { }
