import {CustomerListComponent} from './customer-list';
import {WelcomeComponent} from "./welcome";
import {ConnectionListComponent} from "./connection-list";
import {Routes, RouterModule} from "@angular/router";
import {Component} from "@angular/core";
import {SeasonEntryListComponent} from "./season-entry-list";
import {MeasureStampListComponent} from "./measure-stamp-list";

export const appRoutes: Routes = [
    { path: '', component: WelcomeComponent, terminal: true },
    { path: 'welcome', component: WelcomeComponent},
    { path: 'customers', component: CustomerListComponent },
    { path: 'connections', component: ConnectionListComponent },
    { path: 'season-entries', component: SeasonEntryListComponent},
    { path: 'measure-stamps/:id', component: MeasureStampListComponent}
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
