import { Injectable, Inject } from '@angular/core';
import {ICustomer} from "../model/ICustomer";
import {Http, Response} from "@angular/http";
import {ConfigApp} from "../configApp";

@Injectable()
export class CustomerService {

  constructor(@Inject(Http) private http: Http) {
  }

  getCustomers(){
      return this.http.post(ConfigApp.WS_HOST + "/ws/get-customers", {}).map(
          function(response : Response) {
              return response.json();
          });
  }

  addCustomer(_customer: ICustomer){
      return this.http.post(ConfigApp.WS_HOST + "/ws/add-customer", _customer);
  }

  updateCustomer(_customer: ICustomer){
      return this.http.post(ConfigApp.WS_HOST + "/ws/update-customer", _customer);
  }

  deleteCustomer(_customer: ICustomer){
      return this.http.post(ConfigApp.WS_HOST + "/ws/delete-customer", _customer);
  }
}
