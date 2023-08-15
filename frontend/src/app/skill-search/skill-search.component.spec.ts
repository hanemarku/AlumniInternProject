import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillSearchComponent } from './skill-search.component';

describe('SkillSearchComponent', () => {
  let component: SkillSearchComponent;
  let fixture: ComponentFixture<SkillSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SkillSearchComponent]
    });
    fixture = TestBed.createComponent(SkillSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
