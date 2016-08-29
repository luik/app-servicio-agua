import {Component, OnInit} from '@angular/core';
import {IConnection} from "../model/IConnection";
import {ConnectionService} from "../services/connection.service";
import {ICustomer} from "../model/ICustomer";
import {IRegister} from "../model/IRegister";
import {IZone} from "../model/IZone";
import {RegisterService} from "../services/register.service";
import {ZoneService} from "../services/zone.service";
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
        this.connection = <IConnection>{id: 0, active: true};
        this.selectedRegister = null;
        this.selectedCustomer = null;
        this.selectedZone = null;
        this.isDisplayingDialog = true;
    }

    save(){
        this.connection.registerID = this.selectedRegister.id;
        this.connection.customerID = this.selectedCustomer.id;
        this.connection.zoneID = this.selectedZone.id;

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
        this.connection = this.cloneConnection(event.data);
        this.isNewConnection = false;
        this.isDisplayingDialog = true;
        this.selectedRegister = <IRegister>{id: this.connection.registerID, registerID: this.connection.registerName};
        this.selectedCustomer = <ICustomer>{id: this.connection.customerID, name: this.connection.customerName};
        this.selectedZone = <IZone>{id: this.connection.zoneID, name: this.connection.zoneName};
    }

    onConnectionDeactivated(event){
        let now = Date.now();
        this.connection.endDate = new Date(now - new Date().getTimezoneOffset()*60000).toISOString().slice(0, 10);
    }

    cloneConnection(connection: IConnection): IConnection {
        let newConnection = <IConnection>{};
        for(let prop in connection) {
            newConnection[prop] = connection[prop];
        }
        return newConnection;
    }

}
