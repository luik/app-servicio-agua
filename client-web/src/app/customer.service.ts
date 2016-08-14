import { Injectable, Inject } from '@angular/core';
import {ICustomer} from "./customer-model/ICustomer";
import {Http} from "@angular/http";

@Injectable()
export class CustomerService {

  constructor(@Inject(Http) private http: Http) {
      console.log("Customer service initialized");
  }

  getCustomer(_id){
      if(_id === 0)
      {
          return {id: 0, firstName : '', lastName : '', documentID: ''} as ICustomer;
      }

      //TODO connect to web service
  }

  saveCustomer(_customer: ICustomer){
      if(_customer.id === 0)
      {
          this.http.post("//localhost:8080/ws/add-customer", _customer).subscribe(response => {
              console.log(response);
          });
      }
  }

}
