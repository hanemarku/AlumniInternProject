import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestSearchComponent } from './interest-search.component';

describe('InterestSearchComponent', () => {
  let component: InterestSearchComponent;
  let fixture: ComponentFixture<InterestSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InterestSearchComponent]
    });
    fixture = TestBed.createComponent(InterestSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
