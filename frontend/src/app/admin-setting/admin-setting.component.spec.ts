import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSettingComponent } from './admin-setting.component';

describe('AdminSettingComponent', () => {
  let component: AdminSettingComponent;
  let fixture: ComponentFixture<AdminSettingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSettingComponent]
    });
    fixture = TestBed.createComponent(AdminSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
