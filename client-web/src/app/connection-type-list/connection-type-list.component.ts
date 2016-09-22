import {Component, OnInit} from '@angular/core';
import {IConnectionType} from "../model/IConnectionType";
import {ConnectionTypeService} from "../services/connection-type.service";

@Component({
    selector: 'app-connection-type-list',
    templateUrl: 'connection-type-list.component.html',
    styleUrls: ['connection-type-list.component.css'],
})
export class ConnectionTypeListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    isNewConnectionType: boolean;
    connectionTypes: IConnectionType[];
    selectedConnectionType: IConnectionType;
    connectionType: IConnectionType;

    constructor(private connectionTypeService: ConnectionTypeService) {
    }

    ngOnInit() {
        this.connectionTypeService.getConnectionTypes().subscribe(
            connectionTypes =>
            {
                this.connectionTypes = connectionTypes as Array<IConnectionType>;
            }
        )
    }

    showDialogToAdd(){
        this.isNewConnectionType = true;
        this.connectionType = <IConnectionType>{id: 0};
        this.isDisplayingDialog = true;
    }

    save(){
        if(this.isNewConnectionType)
        {
            // this.registerService.addRegister(this.register).subscribe(
            //     response =>
            //     {
            //         this.ngOnInit();
            //         this.isDisplayingDialog = false;
            //     }
            // );
        }
        else
        {
            this.connectionTypeService.updateConnectionType(this.connectionType).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
    }

    onRowSelect(event) {
        this.connectionType = this.cloneConnectionType(event.data);
        this.isNewConnectionType = false;
        this.isDisplayingDialog = true;
    }

    cloneConnectionType(connectionType: IConnectionType): IConnectionType {
        let newConnectionType = <IConnectionType>{};
        for(let prop in connectionType) {
            newConnectionType[prop] = connectionType[prop];
        }
        return newConnectionType;
    }

}
