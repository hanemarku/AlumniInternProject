import { TestBed } from '@angular/core/testing';

import { InterestDataService } from './interest-data.service';

describe('InterestDataService', () => {
  let service: InterestDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterestDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
