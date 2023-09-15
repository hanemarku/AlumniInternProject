import { TestBed } from '@angular/core/testing';

import { EventSpecificsService } from './event-specifics.service';

describe('EventSpecificsService', () => {
  let service: EventSpecificsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventSpecificsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
