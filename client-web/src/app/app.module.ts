import {NgModule}      from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule}    from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent, routing}  from './app.component';
import {CustomerService} from './customer.service';
import {ConnectionService} from './connection.service';
import {InputTextModule,DataTableModule,ButtonModule,DialogModule} from 'primeng/primeng';
import {CustomerListComponent} from "./customer-list/customer-list.component";
import {ConnectionListComponent} from "./connection-list/connection-list.component";
import {WelcomeComponent} from "./welcome/welcome.component";

@NgModule({
    imports:      [routing, HttpModule, BrowserModule, FormsModule, HttpModule,
        InputTextModule, DataTableModule, ButtonModule, DialogModule],
    declarations: [AppComponent, CustomerListComponent, ConnectionListComponent, WelcomeComponent],
    bootstrap:    [AppComponent],
    providers:    [CustomerService, ConnectionService]
})
export class AppModule { }
