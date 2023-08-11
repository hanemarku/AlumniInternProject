import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserVerificationTemplateComponent } from './user-verification-template.component';

describe('UserVerificationTemplateComponent', () => {
  let component: UserVerificationTemplateComponent;
  let fixture: ComponentFixture<UserVerificationTemplateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserVerificationTemplateComponent]
    });
    fixture = TestBed.createComponent(UserVerificationTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
