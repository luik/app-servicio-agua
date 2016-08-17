import { Injectable, Inject } from '@angular/core';
import {ICustomer} from "./model/ICustomer";
import {Http, Response} from "@angular/http";
import {ConfigApp} from "./configApp";

@Injectable()
export class CustomerService {

  constructor(@Inject(Http) private http: Http) {
  }

  getCustomer(_id){
      if(_id === 0)
      {
          return {id: 0, name : '', documentId: ''} as ICustomer;
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

      if(_customer.id === 0)
      {
          this.http.post(ConfigApp.WS_HOST + "/ws/add-customer", _customer).subscribe(response => {
              console.log(response);
          });
      }
  }

}
