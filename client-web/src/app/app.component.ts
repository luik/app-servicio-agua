import { Component } from '@angular/core';
import { HTTP_PROVIDERS } from '@angular/http';
import { ROUTER_DIRECTIVES, provideRouter, RouterConfig } from '@angular/router';

import {CustomerListComponent} from './customer-list';
import {CustomerEditComponent} from './customer-edit'
import {WelcomeComponent} from "./welcome";

export const appRoutes: RouterConfig = [
    { path: '', component: WelcomeComponent, terminal: true },
    { path: 'welcome', component: WelcomeComponent},
    { path: 'customers', component: CustomerListComponent },
    { path: 'customer-edit/:id', component: CustomerEditComponent }
];

export const APP_ROUTER_PROVIDER = provideRouter(appRoutes);

@Component({
    moduleId: module.id,
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [
        HTTP_PROVIDERS
    ]
})
export class AppComponent {
  title = 'Aplicación control agua!';
}
