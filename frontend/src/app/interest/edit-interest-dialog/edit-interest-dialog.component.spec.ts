import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditInterestDialogComponent } from './edit-interest-dialog.component';

describe('EditInterestDialogComponent', () => {
  let component: EditInterestDialogComponent;
  let fixture: ComponentFixture<EditInterestDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditInterestDialogComponent]
    });
    fixture = TestBed.createComponent(EditInterestDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
