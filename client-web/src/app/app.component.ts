import {CustomerListComponent} from './customer-list';
import {WelcomeComponent} from "./welcome";
import {ConnectionListComponent} from "./connection-list";
import {Routes, RouterModule} from "@angular/router";
import {Component} from "@angular/core";
import {SeasonEntryListComponent} from "./season-entry-list";
import {MeasureStampListComponent} from "./measure-stamp-list";
import {SeasonalConnectionDebtList} from "./seasonal-connection-debt-list/seasonal-connection-debt-list.component";
import {SeasonalConnectionPaymentList} from "./seasonal-connection-payment-list/seasonal-connection-payment-list.component";
import {RegisterListComponent} from "./register-list/register-list.component";
import {ConnectionTypeListComponent} from "./connection-type-list/connection-type-list.component";
import {ConfigListComponent} from "./config-list/config-list.component";

export const appRoutes: Routes = [
    { path: '', component: WelcomeComponent},
    { path: 'welcome', component: WelcomeComponent},
    { path: 'customers', component: CustomerListComponent },
    { path: 'connections', component: ConnectionListComponent },
    { path: 'season-entries', component: SeasonEntryListComponent},
    { path: 'measure-stamps/:by/:id', component: MeasureStampListComponent},
    { path: 'seasonal-connection-debts/:by/:id', component: SeasonalConnectionDebtList},
    { path: 'seasonal-connection-payments/:by/:id', component: SeasonalConnectionPaymentList},
    { path: 'registers', component: RegisterListComponent},
    { path: 'connection-types', component: ConnectionTypeListComponent},
    { path: 'configs', component: ConfigListComponent},
];

export const routing = RouterModule.forRoot(appRoutes);

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css']
})
export class AppComponent {
  title = 'Aplicación control agua!';
}
