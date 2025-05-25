import { TestBed } from '@angular/core/testing';

import { TipoMesaService } from './tipo-mesa.service';

describe('TipoMesaService', () => {
  let service: TipoMesaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoMesaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
