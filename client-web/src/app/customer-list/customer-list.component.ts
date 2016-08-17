import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../customer.service";
import {ICustomer} from "../customer-model/ICustomer";
import {DataTable} from 'primeng/primeng';
import {Column} from 'primeng/primeng';

@Component({
    moduleId: module.id,
    selector: 'app-customer-list',
    templateUrl: 'customer-list.component.html',
    styleUrls: ['customer-list.component.css'],
    providers: [CustomerService],
    directives: [DataTable, Column]
})
export class CustomerListComponent implements OnInit {

    customers: ICustomer[];
    selectedCustomer: ICustomer;

    constructor(private customerService: CustomerService) {
    }

    ngOnInit() {
        this.customerService.getCustomers().subscribe(
            customers =>
            {
                this.customers = customers as Array<ICustomer>;
                console.log(this.customers);
            }
        )
    }

}
