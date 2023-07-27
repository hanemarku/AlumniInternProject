import { Component, Output, EventEmitter } from '@angular/core';

export class Skill {
  constructor(public name: string) {}
}

@Component({
  selector: 'app-skill-search',
  templateUrl: './skill-search.component.html',
  styleUrls: ['./skill-search.component.sass']
})
export class SkillSearchComponent {
  allSkills: string[] = [
    'Digital Marketing',
    'Web Development',
    'Graphic Design',
    'Data Analysis',
    'Project Management',
    'Communication Skills',
    'Problem Solving',
    'Teamwork',
    'Time Management',
    'Leadership'
  ];

  filteredSkills: string[] = [];
  selectedSkills: string[] = [];
  searchTerm: string = ''; 
  @Output() selectedSkillsChange: EventEmitter<string[]> = new EventEmitter();

  onSearch(searchTerm: string) {
    this.searchTerm = searchTerm.trim();

    if (!searchTerm || searchTerm === '') {
      this.filteredSkills = [];
    } else {
      this.filteredSkills = this.allSkills.filter(skill =>
        skill.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
  }
  

  onAddSkill(skill: string) {
    if (!this.selectedSkills.includes(skill)) {
      this.selectedSkills.push(skill);
      this.selectedSkillsChange.emit(this.selectedSkills);
    }
  }

  onEnterKeyPress(event: Event) {
    const searchTerm = this.searchTerm.trim();
    console.log("searchTerm: " + searchTerm);
    if (event instanceof KeyboardEvent && event.key === 'Enter' && searchTerm && !this.filteredSkills.length) {
      this.onAddSkill(searchTerm);
    }
  }
}
