import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSearchByComponent } from './event-search-by.component';

describe('EventSearchByComponent', () => {
  let component: EventSearchByComponent;
  let fixture: ComponentFixture<EventSearchByComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventSearchByComponent]
    });
    fixture = TestBed.createComponent(EventSearchByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
