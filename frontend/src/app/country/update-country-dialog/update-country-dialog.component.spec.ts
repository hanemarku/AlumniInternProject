import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UpdateCountryDialogComponent } from './update-country-dialog.component';

describe('UpdateCountryDialogComponent', () => {
  let component: UpdateCountryDialogComponent;
  let fixture: ComponentFixture<UpdateCountryDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateCountryDialogComponent]
    });
    fixture = TestBed.createComponent(UpdateCountryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
