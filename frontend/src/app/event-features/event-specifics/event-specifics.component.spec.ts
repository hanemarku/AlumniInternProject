import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSpecificsComponent } from './event-specifics.component';

describe('EventSpecificsComponent', () => {
  let component: EventSpecificsComponent;
  let fixture: ComponentFixture<EventSpecificsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventSpecificsComponent]
    });
    fixture = TestBed.createComponent(EventSpecificsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
