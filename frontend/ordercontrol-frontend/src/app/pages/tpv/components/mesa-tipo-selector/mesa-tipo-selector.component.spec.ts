import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MesaTipoSelectorComponent } from './mesa-tipo-selector.component';

describe('MesaTipoSelectorComponent', () => {
  let component: MesaTipoSelectorComponent;
  let fixture: ComponentFixture<MesaTipoSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MesaTipoSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MesaTipoSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
