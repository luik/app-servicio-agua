import {Component, OnInit} from '@angular/core';
import {IConnection} from "../model/IConnection";
import {ConnectionService} from "../services/connection.service";
import {ICustomer} from "../model/ICustomer";
import {IRegister} from "../model/IRegister";
import {IZone} from "../model/IZone";
import {RegisterService} from "../services/register.service";
import {ZoneService} from "../services/zone.service";
import {forEach} from "@angular/router/src/utils/collection";

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
    zones: IZone[];
    selectedConnection: IConnection;
    connection: IConnection;
    isNewConnection: boolean;

    selectedRegister: IRegister;
    selectedZone: IZone;

    constructor(
        private connectionService: ConnectionService,
        private registerService: RegisterService,
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

        this.zoneService.getZones().subscribe(
            zones => {
                this.zones = zones as Array<IZone>;
            }
        );
    }

    search(event) {
        this.filteredRegisters = [];
        for(let register of this.availableRegisters)
        {
            if(register.registerID.indexOf(event.query) >= 0)
            {
                this.filteredRegisters.push(register);
            }
        }
    }

    showDialogToAdd(){
        this.isNewConnection = true;
        this.connection = <IConnection>{id: 0};
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
        this.connection = event.data;
        this.isNewConnection = false;
        this.isDisplayingDialog = true;

        for(let register of this.availableRegisters)
        {
            if(register.id === this.connection.registerID)
            {
                this.selectedRegister = register;
                break;
            }
        }
    }

}
