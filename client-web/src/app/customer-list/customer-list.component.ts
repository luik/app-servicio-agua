import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../customer.service";
import {ICustomer} from "../model/ICustomer";
import {DataTable} from 'primeng/primeng';
import {Column} from 'primeng/primeng';
import {Dialog} from "primeng/primeng";
import {Button} from "primeng/primeng";
import {InputText} from "primeng/primeng";

@Component({
    moduleId: module.id,
    selector: 'app-customer-list',
    templateUrl: 'customer-list.component.html',
    styleUrls: ['customer-list.component.css'],
    providers: [CustomerService],
    directives: [DataTable, Column, Dialog, Button, InputText]
})
export class CustomerListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    isNewCustomer: boolean;
    customers: ICustomer[];
    selectedCustomer: ICustomer;
    customer: ICustomer;

    constructor(private customerService: CustomerService) {
    }

    ngOnInit() {
        this.customerService.getCustomers().subscribe(
            customers =>
            {
                this.customers = customers as Array<ICustomer>;
            }
        )
    }

    showDialogToAdd(){
        this.isNewCustomer = true;
        this.customer = <ICustomer>{id: 0};
        this.isDisplayingDialog = true;
    }

    save(){
        if(this.isNewCustomer)
        {
            this.customerService.addCustomer(this.customer).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
        else
        {
            this.customerService.updateCustomer(this.customer).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
    }

    delete(){
        this.customerService.deleteCustomer(this.customer).subscribe(
            response =>
            {
                this.ngOnInit();
                this.isDisplayingDialog = false;
            }
        );
    }

    onRowSelect(event) {
        this.customer = event.data;
        this.isNewCustomer = false;
        this.isDisplayingDialog = true;
    }

}
