import { TestBed } from '@angular/core/testing';

import { EducationDataService } from './education-data.service';

describe('EducationDataService', () => {
  let service: EducationDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
