import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SkillDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllSkills(){
    return this.http.get<any>("http://localhost:8080/api/v1/skills");
  }
  
  deleteSkill(skillId: string){
    return this.http.delete(`http://localhost:8080/api/v1/skills/${skillId}`);
  }

  updateSkill(skillId: string, updatedSkill: any){
    return this.http.patch(`http://localhost:8080/api/v1/skills/${skillId}`, updatedSkill);
  } 

  saveSkill(skill: any){
    return this.http.post(`http://localhost:8080/api/v1/skills`, skill);
  }

  sortSkillsByName(){
    return this.http.get<any>("http://localhost:8080/api/v1/skills/sort-name");
  }


}
