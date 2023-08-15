import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { SkillDataService } from '../services/skill-service/skill-data.service';

export class Skill {
  id: string | null;
  name: string;

  constructor(id: string | null = null, name: string) {
    this.id = id;
    this.name = name;
  }
  
}


@Component({
  selector: 'app-skill-search',
  templateUrl: './skill-search.component.html',
  styleUrls: ['./skill-search.component.sass']
})
export class SkillSearchComponent implements OnInit{
  allSkills: Skill[] = [];
  filteredSkills: Skill[] = [];
  selectedSkills: Skill[] = [];
  searchTerm: string = ''; 
  @Output() selectedSkillsChange: EventEmitter<Skill[]> = new EventEmitter();

  constructor(
    private skillDataService: SkillDataService
  ) {}

  ngOnInit(): void {
    this.fetchAllSkills();
  }

  fetchAllSkills() {
    this.skillDataService.listAllSkills().subscribe(
      (response: Skill[]) => {
        this.allSkills = response;
      },
      (error: any) => {
        console.error('Error fetching skills:', error);
      }
    );
  }

  onSearch(searchTerm: string) {
    this.searchTerm = searchTerm.trim();

    if (!searchTerm || searchTerm === '') {
      this.filteredSkills = [];
    } else {
      this.filteredSkills = this.allSkills.filter(skill =>
        skill.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
  }
  

  onAddSkill(skill: Skill) {
    const skillExistsInDatabase = this.allSkills.some((existingSkill) => existingSkill.name === skill.name);
  
    if (!this.selectedSkills.includes(skill)) {
      this.selectedSkills.push(skill);
    }
  
    if (!skillExistsInDatabase) {
      this.skillDataService.saveSkill({ name: skill.name }).subscribe(
        (response: any) => {
          skill.id = response.id; 
          console.log('Skill added to the database:', skill);
          this.selectedSkillsChange.emit(this.selectedSkills); 
        },
        (error: any) => {
          console.error('Error adding skill to the database:', error);
        }
      );
    } else {
     
      this.selectedSkillsChange.emit(this.selectedSkills);
    }
  }
  

  onEnterKeyPress(event: Event) {
    const searchTerm = this.searchTerm.trim();
    console.log("searchTerm: " + searchTerm);
    console.log("filteredSkills: " + this.filteredSkills.length);
    if (event instanceof KeyboardEvent && event.key === 'Enter' && searchTerm && this.filteredSkills.length === 0) {
      const newSkill = new Skill(null, searchTerm);
      this.onAddSkill(newSkill);
    }
  }

  getSkillName(skill: Skill) {
    return skill ? skill.name : '';
  }
}
