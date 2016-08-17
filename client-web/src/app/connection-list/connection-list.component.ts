import {Component, OnInit} from '@angular/core';
import {ConnectionService} from "../connection.service";
import {Column} from "primeng/components/column/column";
import {DataTable} from "primeng/components/datatable/datatable";
import {IConnection} from "../model/IConnection";

@Component({
    moduleId: module.id,
    selector: 'app-connection-list',
    templateUrl: 'connection-list.component.html',
    styleUrls: ['connection-list.component.css'],
    providers: [ConnectionService],
    directives: [DataTable, Column]
})
export class ConnectionListComponent implements OnInit {

    connections: IConnection[];
    selectedConnection: IConnection;

    constructor(private connectionService: ConnectionService) {
    }

    ngOnInit() {
        this.connectionService.getConnections().subscribe(
            connections => {
                console.log(connections);
                this.connections = connections as Array<IConnection>;
            }
        )
    }

}
