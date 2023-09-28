import { TestBed } from '@angular/core/testing';

import { RecommendationDataService } from './recommendation-data.service';

describe('RecommendationDataService', () => {
  let service: RecommendationDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecommendationDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
