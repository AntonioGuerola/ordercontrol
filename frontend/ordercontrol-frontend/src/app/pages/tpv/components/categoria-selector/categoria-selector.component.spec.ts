import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaSelectorComponent } from './categoria-selector.component';

describe('CategoriaSelectorComponent', () => {
  let component: CategoriaSelectorComponent;
  let fixture: ComponentFixture<CategoriaSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriaSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriaSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
