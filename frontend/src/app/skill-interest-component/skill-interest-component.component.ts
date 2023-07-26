import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-skill-interest-component',
  templateUrl: './skill-interest-component.component.html',
  styleUrls: ['./skill-interest-component.component.sass']
})
export class SkillInterestComponentComponent {
  @Output() selectedSkillsChange: EventEmitter<string[]> = new EventEmitter();
  @Output() selectedInterestsChange: EventEmitter<string[]> = new EventEmitter();
  @Input() selectedSkills: string[] = [];
  @Input() selectedInterests: string[] = [];

  onSelectedSkillsChange(selectedSkills: string[]) {
    this.selectedSkillsChange.emit(selectedSkills);
  }

  onSelectedInterestsChange(selectedInterests: string[]) {
    this.selectedInterestsChange.emit(selectedInterests);
  }
}
