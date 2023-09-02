import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilesSwiperComponent } from './profiles-swiper.component';

describe('ProfilesSwiperComponent', () => {
  let component: ProfilesSwiperComponent;
  let fixture: ComponentFixture<ProfilesSwiperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilesSwiperComponent]
    });
    fixture = TestBed.createComponent(ProfilesSwiperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
