import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ICustomer} from "../customer-model/ICustomer";
import {CustomerService} from "../customer.service";

@Component({
    moduleId: module.id,
    selector: 'app-customer-edit',
    templateUrl: 'customer-edit.component.html',
    styleUrls: ['customer-edit.component.css'],
    providers: [CustomerService]
})
export class CustomerEditComponent implements OnInit, OnDestroy {
    pageTitle: string = 'Editar beneficiario';
    customer: ICustomer;
    sub: any;

    constructor(private route: ActivatedRoute, private customerService: CustomerService) {
    }

    private onCustomerUpdated(){
        if(this.customer.id === 0)
        {
            this.pageTitle = 'Agregar beneficiario';
        }
    }

    saveCustomer(){
        this.customerService.saveCustomer(this.customer);
    }

    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            let id = Number.parseInt(params["id"]);
            this.customer = this.customerService.getCustomer(id);
            this.onCustomerUpdated();
        });
    }

    ngOnDestroy()
    {
        this.sub.unsubscribe();
    }

}
