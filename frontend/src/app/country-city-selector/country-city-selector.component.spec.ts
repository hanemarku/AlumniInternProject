import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryCitySelectorComponent } from './country-city-selector.component';

describe('CountryCitySelectorComponent', () => {
  let component: CountryCitySelectorComponent;
  let fixture: ComponentFixture<CountryCitySelectorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CountryCitySelectorComponent]
    });
    fixture = TestBed.createComponent(CountryCitySelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
