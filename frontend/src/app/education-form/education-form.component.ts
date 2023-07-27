import { Component } from '@angular/core';


export interface Education {
  id: number;
  school: string;
  degree: string;
  graduationYear: number;
}


@Component({
  selector: 'app-education-form',
  templateUrl: './education-form.component.html',
  styleUrls: ['./education-form.component.sass']
})
export class EducationFormComponent {
  newEducation: Education = {
    id: 1,
    school: '',
    degree: '',
    graduationYear: 1990
  };

  educations: Education[] = [];

  addEducation() {
    if (this.newEducation.school && this.newEducation.degree && this.newEducation.graduationYear) {
      // Add the new education to the educations array
      this.educations.push({...this.newEducation});

      // Clear the form after adding the education
      this.newEducation.school = '';
      this.newEducation.degree = '';
      this.newEducation.graduationYear = 1990;
    }
  }

  deleteEducation(index: number) {
    this.educations.splice(index, 1);
  }

  editEducation(index: number) {
    const selectedEducation = this.educations[index];
    this.newEducation = { ...selectedEducation };
    this.deleteEducation(index); // Remove the selected education from the list
  }
}
