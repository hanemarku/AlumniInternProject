import { TestBed } from '@angular/core/testing';

import { RegistUsersService } from './regist-users.service';

describe('RegistUsersService', () => {
  let service: RegistUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegistUsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
