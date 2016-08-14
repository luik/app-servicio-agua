/* tslint:disable:no-unused-variable */

import { By }           from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { addProviders, async, inject } from '@angular/core/testing';
import { CustomerListComponent } from './customer-list.component';

describe('Component: CustomerList', () => {
  it('should create an instance', () => {
    let component = new CustomerListComponent();
    expect(component).toBeTruthy();
  });
});
