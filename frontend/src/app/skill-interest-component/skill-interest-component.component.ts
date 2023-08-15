import { Component, Output, EventEmitter, Input } from '@angular/core';
import { Skill } from '../skill-search/skill-search.component';

@Component({
  selector: 'app-skill-interest-component',
  templateUrl: './skill-interest-component.component.html',
  styleUrls: ['./skill-interest-component.component.sass']
})
export class SkillInterestComponentComponent {
  @Output() selectedSkillsChange: EventEmitter<Skill[]> = new EventEmitter();
  @Output() selectedInterestsChange: EventEmitter<Skill[]> = new EventEmitter();
  @Input() selectedSkills: Skill[] = [];
  @Input() selectedInterests: Skill[] = [];

  onSelectedSkillsChange(selectedSkills: Skill[]) {
    this.selectedSkillsChange.emit(selectedSkills);
  }

  onSelectedInterestsChange(selectedInterests: Skill[]) {
    this.selectedInterestsChange.emit(selectedInterests);
  }
}
