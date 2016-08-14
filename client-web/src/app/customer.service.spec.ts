/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { CustomerService } from './customer.service';

describe('Service: Customer', () => {
  beforeEach(() => {
    addProviders([CustomerService]);
  });

  it('should ...',
    inject([CustomerService],
      (service: CustomerService) => {
        expect(service).toBeTruthy();
      }));
});
