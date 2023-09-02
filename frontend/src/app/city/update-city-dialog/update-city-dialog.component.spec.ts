import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCityDialogComponent } from './update-city-dialog.component';

describe('UpdateCityDialogComponent', () => {
  let component: UpdateCityDialogComponent;
  let fixture: ComponentFixture<UpdateCityDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateCityDialogComponent]
    });
    fixture = TestBed.createComponent(UpdateCityDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
