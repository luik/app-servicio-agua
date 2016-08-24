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

    isDisplayingDialog: boolean = false;
    connections: IConnection[];
    selectedConnection: IConnection;
    connection: IConnection;
    isNewConnection: boolean;

    constructor(private connectionService: ConnectionService) {
    }

    ngOnInit() {
        this.connectionService.getConnections().subscribe(
            connections => {
                this.connections = connections as Array<IConnection>;
            }
        )
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
    }

}
