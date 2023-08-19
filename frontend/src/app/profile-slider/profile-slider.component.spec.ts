import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileSliderComponent } from './profile-slider.component';

describe('ProfileSliderComponent', () => {
  let component: ProfileSliderComponent;
  let fixture: ComponentFixture<ProfileSliderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileSliderComponent]
    });
    fixture = TestBed.createComponent(ProfileSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
