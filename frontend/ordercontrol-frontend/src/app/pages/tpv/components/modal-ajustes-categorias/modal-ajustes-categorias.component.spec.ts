import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalAjustesCategoriasComponent } from './modal-ajustes-categorias.component';

describe('ModalAjustesCategoriasComponent', () => {
  let component: ModalAjustesCategoriasComponent;
  let fixture: ComponentFixture<ModalAjustesCategoriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalAjustesCategoriasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalAjustesCategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
