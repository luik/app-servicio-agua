import {Component, OnInit} from '@angular/core';
import {IConnection} from "../model/IConnection";
import {ConnectionService} from "../services/connection.service";
import {ICustomer} from "../model/ICustomer";
import {IRegister} from "../model/IRegister";
import {IZone} from "../model/IZone";
import {RegisterService} from "../services/register.service";
import {ZoneService} from "../services/zone.service";
import {forEach} from "@angular/router/src/utils/collection";
import {CustomerService} from "../services/customer.service";

@Component({
    selector: 'app-connection-list',
    templateUrl: 'connection-list.component.html',
    styleUrls: ['connection-list.component.css']
})
export class ConnectionListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    connections: IConnection[];
    availableRegisters: IRegister[];
    filteredRegisters: IRegister[];
    customers: ICustomer[];
    filteredCustomers: ICustomer[];
    zones: IZone[];
    filteredZones: IZone[];
    selectedConnection: IConnection;
    connection: IConnection;
    isNewConnection: boolean;

    selectedRegister: IRegister;
    selectedCustomer: ICustomer;
    selectedZone: IZone;

    constructor(
        private connectionService: ConnectionService,
        private registerService: RegisterService,
        private customerService: CustomerService,
        private zoneService: ZoneService
    ) {
    }

    ngOnInit() {
        this.connectionService.getConnections().subscribe(
            connections => {
                this.connections = connections as Array<IConnection>;
            }
        );

        this.registerService.getAvailableRegisters().subscribe(
            registers => {
                this.availableRegisters = registers as Array<IRegister>;

            }
        );

        this.customerService.getCustomers().subscribe(
            customers => {
                this.customers = customers as Array<ICustomer>;
            }
        );

        this.zoneService.getZones().subscribe(
            zones => {
                this.zones = zones as Array<IZone>;
            }
        );
    }

    searchRegisters(event) {
        this.filteredRegisters =
            this.availableRegisters.filter(register => {
                return register.registerID.indexOf(event.query) >= 0;
            })
    }

    searchCustomers(event) {
        this.filteredCustomers =
            this.customers.filter(customer => {
                return customer.name.indexOf(event.query) >= 0;
            })
    }

    searchZones(event){
        this.filteredZones =
            this.zones.filter(
                zone => {
                    return zone.name.indexOf(event.query) >= 0;
                }
            );
    }

    showDialogToAdd(){
        this.isNewConnection = true;
        this.connection = <IConnection>{id: 0};
        this.selectedRegister = null;
        this.selectedCustomer = null;
        this.selectedZone = null;
        this.isDisplayingDialog = true;
    }

    save(){
        if(this.isNewConnection)
        {
            this.connectionService.addConnection(this.connection).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
        else
        {
            this.connectionService.updateConnection(this.connection).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
    }

    delete(){
        this.connectionService.deleteConnection(this.connection).subscribe(
            response =>
            {
                this.ngOnInit();
                this.isDisplayingDialog = false;
            }
        );
    }

    onRowSelect(event) {
        //selected connection
        this.connection = this.cloneConnection(event.data);
        this.isNewConnection = false;
        this.isDisplayingDialog = true;
        this.selectedRegister = <IRegister>{registerID: this.connection.registerName};
        this.selectedCustomer = <ICustomer>{name: this.connection.customerName};
        this.selectedZone = <IZone>{name: this.connection.zoneName};
    }

    cloneConnection(connection: IConnection): IConnection {
        let newConnection = <IConnection>{};
        for(let prop in connection) {
            newConnection[prop] = connection[prop];
        }
        return newConnection;
    }

}
