import {Component, OnInit} from '@angular/core';
import {IRegister} from "../model/IRegister";
import {RegisterService} from "../services/register.service";

@Component({
    selector: 'app-register-list',
    templateUrl: 'register-list.component.html',
    styleUrls: ['register-list.component.css'],
})
export class RegisterListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    isNewRegister: boolean;
    registers: IRegister[];
    selectedRegister: IRegister;
    register: IRegister;

    constructor(private registerService: RegisterService) {
    }

    ngOnInit() {
        this.registerService.getRegisters().subscribe(
            customers =>
            {
                this.registers = customers as Array<IRegister>;
            }
        )
    }

    showDialogToAdd(){
        this.isNewRegister = true;
        this.register = <IRegister>{id: 0};
        this.isDisplayingDialog = true;
    }

    save(){
        if(this.isNewRegister)
        {
            this.registerService.addRegister(this.register).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
        else
        {
            this.registerService.updateRegister(this.register).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
    }

    onRowSelect(event) {
        this.register = this.cloneRegister(event.data);
        this.isNewRegister = false;
        this.isDisplayingDialog = true;
    }

    cloneRegister(register: IRegister): IRegister {
        let newRegister = <IRegister>{};
        for(let prop in register) {
            newRegister[prop] = register[prop];
        }
        return newRegister;
    }

}
