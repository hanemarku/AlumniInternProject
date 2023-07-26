import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSkillDialogComponent } from './edit-skill-dialog.component';

describe('EditSkillDialogComponent', () => {
  let component: EditSkillDialogComponent;
  let fixture: ComponentFixture<EditSkillDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditSkillDialogComponent]
    });
    fixture = TestBed.createComponent(EditSkillDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
