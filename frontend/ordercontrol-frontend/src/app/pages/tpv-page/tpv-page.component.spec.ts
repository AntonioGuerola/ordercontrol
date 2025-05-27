import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TpvPageComponent } from './tpv-page.component';

describe('TpvPageComponent', () => {
  let component: TpvPageComponent;
  let fixture: ComponentFixture<TpvPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TpvPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TpvPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
