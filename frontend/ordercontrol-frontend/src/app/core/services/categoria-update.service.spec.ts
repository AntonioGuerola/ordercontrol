import { TestBed } from '@angular/core/testing';

import { CategoriaUpdateService } from './categoria-update.service';

describe('CategoriaUpdateService', () => {
  let service: CategoriaUpdateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaUpdateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
