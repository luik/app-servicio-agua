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
import {SeasonalConnectionPaymentList} from "./seasonal-connection-payment-list/seasonal-connection-payment-list.component";
import {SeasonalConnectionPaymentService} from "./services/seasonal-connection-payment.service";
import {RegisterListComponent} from "./register-list/register-list.component";
import {ConnectionTypeListComponent} from "./connection-type-list/connection-type-list.component";
import {ConnectionTypeService} from "./services/connection-type.service";
import {ConfigListComponent} from "./config-list/config-list.component";
import {ConfigService} from "./services/config.service";
import {ServiceShutOffListComponent} from "./service-shut-off-list/service-shut-off-list.component";
import {ServiceShutOffService} from "./services/service-shut-off.service";

@NgModule({
    imports:      [routing, HttpModule, BrowserModule, FormsModule, HttpModule,
        InputTextModule, DataTableModule, ButtonModule, DialogModule, DropdownModule, AutoCompleteModule,
        CheckboxModule, InputSwitchModule, ToggleButtonModule, CalendarModule, MenuModule, MenubarModule
    ],
    declarations: [AppComponent, CustomerListComponent, ConnectionListComponent, WelcomeComponent,
        SeasonEntryListComponent, MeasureStampListComponent, SeasonalConnectionDebtList,
        SeasonalConnectionPaymentList, RegisterListComponent, ConnectionTypeListComponent,
        ConfigListComponent, ServiceShutOffListComponent
    ],
    bootstrap:    [AppComponent],
    providers:    [CustomerService, ConnectionService, RegisterService, ZoneService,
        SeasonEntryService, MeasureStampService, SeasonalConnectionDebtService, SeasonalConnectionPaymentService,
        ConnectionTypeService, ConfigService, ServiceShutOffService
    ]
})
export class AppModule { }
