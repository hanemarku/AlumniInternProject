import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSpecificsDetailsComponent } from './event-specifics-details.component';

describe('EventSpecificsDetailsComponent', () => {
  let component: EventSpecificsDetailsComponent;
  let fixture: ComponentFixture<EventSpecificsDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventSpecificsDetailsComponent]
    });
    fixture = TestBed.createComponent(EventSpecificsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
