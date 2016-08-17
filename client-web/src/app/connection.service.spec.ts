/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { ConnectionService } from './connection.service';

describe('Service: Connection', () => {
  beforeEach(() => {
    addProviders([ConnectionService]);
  });

  it('should ...',
    inject([ConnectionService],
      (service: ConnectionService) => {
        expect(service).toBeTruthy();
      }));
});
