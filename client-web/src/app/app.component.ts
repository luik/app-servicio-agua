import {CustomerListComponent} from './customer-list';
import {WelcomeComponent} from "./welcome";
import {ConnectionListComponent} from "./connection-list/connection-list.component";
import {Routes, RouterModule} from "@angular/router";
import {Component} from "@angular/core";

export const appRoutes: Routes = [
    { path: '', component: WelcomeComponent, terminal: true },
    { path: 'welcome', component: WelcomeComponent},
    { path: 'customers', component: CustomerListComponent },
    { path: 'connections', component: ConnectionListComponent }
];

export const routing = RouterModule.forRoot(appRoutes);

@Component({
    moduleId: module.id,
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css']
})
export class AppComponent {
  title = 'Aplicación control agua!';
}
