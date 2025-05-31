import { TestBed } from '@angular/core/testing';

import { AccionesCuentaService } from './acciones-cuenta.service';

describe('AccionesCuentaService', () => {
  let service: AccionesCuentaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccionesCuentaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
