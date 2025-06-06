import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjustesButtonsComponent } from './ajustes-buttons.component';

describe('AjustesButtonsComponent', () => {
  let component: AjustesButtonsComponent;
  let fixture: ComponentFixture<AjustesButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AjustesButtonsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjustesButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
