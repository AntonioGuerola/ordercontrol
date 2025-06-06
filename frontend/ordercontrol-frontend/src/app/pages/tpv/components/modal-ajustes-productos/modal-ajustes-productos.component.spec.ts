import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalAjustesProductosComponent } from './modal-ajustes-productos.component';

describe('ModalAjustesProductosComponent', () => {
  let component: ModalAjustesProductosComponent;
  let fixture: ComponentFixture<ModalAjustesProductosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalAjustesProductosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalAjustesProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
