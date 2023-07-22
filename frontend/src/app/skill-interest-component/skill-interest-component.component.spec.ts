import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillInterestComponentComponent } from './skill-interest-component.component';

describe('SkillInterestComponentComponent', () => {
  let component: SkillInterestComponentComponent;
  let fixture: ComponentFixture<SkillInterestComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SkillInterestComponentComponent]
    });
    fixture = TestBed.createComponent(SkillInterestComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
