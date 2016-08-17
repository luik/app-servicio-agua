import { Injectable, Inject } from '@angular/core';
import {ICustomer} from "./customer-model/ICustomer";
import {Http, Response} from "@angular/http";
import {ConfigApp} from "./configApp";

@Injectable()
export class CustomerService {

  constructor(@Inject(Http) private http: Http) {
      console.log("Customer service initialized");
  }

  getCustomer(_id){
      if(_id === 0)
      {
          return {customerID: 0, firstName : '', lastName : '', documentID: ''} as ICustomer;
      }

      //TODO connect to web service
  }

  getCustomers(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-customers", {}).map(
          function(response : Response) {
              return response.json();
          });
  }

  saveCustomer(_customer: ICustomer){
      if(_customer.customerID === 0)
      {
          this.http.post(ConfigApp.WS_HOST + "/ws/add-customer", _customer).subscribe(response => {
              console.log(response);
          });
      }
  }

}
