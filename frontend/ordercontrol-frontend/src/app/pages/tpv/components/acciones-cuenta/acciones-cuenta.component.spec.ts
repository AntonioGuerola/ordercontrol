import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccionesCuentaComponent } from './acciones-cuenta.component';

describe('AccionesCuentaComponent', () => {
  let component: AccionesCuentaComponent;
  let fixture: ComponentFixture<AccionesCuentaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccionesCuentaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccionesCuentaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
