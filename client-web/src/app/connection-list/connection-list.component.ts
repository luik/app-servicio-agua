import {Component, OnInit} from '@angular/core';
import {IConnection} from "../model/IConnection";
import {ConnectionService} from "../connection.service";

@Component({
    moduleId: module.id,
    selector: 'app-connection-list',
    templateUrl: 'connection-list.component.html',
    styleUrls: ['connection-list.component.css']
})
export class ConnectionListComponent implements OnInit {

    connections: IConnection[];
    selectedConnection: IConnection;

    constructor(private connectionService: ConnectionService) {
    }

    ngOnInit() {
        this.connectionService.getConnections().subscribe(
            connections => {
                this.connections = connections as Array<IConnection>;
            }
        )
    }

}
