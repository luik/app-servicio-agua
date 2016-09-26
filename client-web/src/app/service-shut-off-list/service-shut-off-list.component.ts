import {Component, OnInit} from '@angular/core';
import {ServiceShutOffService} from "../services/service-shut-off.service";
import {IServiceShutOff} from "../model/IServiceShutOff";

@Component({
    selector: 'app-service-shut-off-list',
    templateUrl: 'service-shut-off-list.component.html',
    styleUrls: ['service-shut-off-list.component.css'],
})
export class ServiceShutOffListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    isNewServiceShutOff: boolean;
    serviceShutOffs: IServiceShutOff[];
    selectedServiceShutOff: IServiceShutOff;
    serviceShutOff: IServiceShutOff;

    constructor(private serviceShutOffService: ServiceShutOffService) {
    }

    ngOnInit() {
        this.serviceShutOffService.getServiceShutOffs().subscribe(
            serviceShutOffs =>
            {
                this.serviceShutOffs = serviceShutOffs as Array<IServiceShutOff>;
            }
        )
    }

    showDialogToAdd(){
        this.isNewServiceShutOff = true;
        this.serviceShutOff = <IServiceShutOff>{id: 0};
        this.isDisplayingDialog = true;
    }

    save(){
        // if(this.isNewRegister)
        // {
        //     this.serviceShutOffService.addRegister(this.register).subscribe(
        //         response =>
        //         {
        //             this.ngOnInit();
        //             this.isDisplayingDialog = false;
        //         }
        //     );
        // }
        // else
        // {
        //     this.serviceShutOffService.updateRegister(this.register).subscribe(
        //         response =>
        //         {
        //             this.ngOnInit();
        //             this.isDisplayingDialog = false;
        //         }
        //     );
        // }
    }

    onRowSelect(event) {
        // this.register = this.cloneRegister(event.data);
        // this.isNewRegister = false;
        // this.isDisplayingDialog = true;
    }

    cloneServiceShutOff(register: IServiceShutOff): IServiceShutOff {
        let newRegister = <IServiceShutOff>{};
        for(let prop in register) {
            newRegister[prop] = register[prop];
        }
        return newRegister;
    }

}
