import { Component, Input, OnInit } from '@angular/core';
import { SkillDataService } from '../services/skill-service/skill-data.service';
import { NgForm } from '@angular/forms';
import { EditSkillDialogComponent } from './edit-skill-dialog/edit-skill-dialog.component';
import { MatDialog } from '@angular/material/dialog';


export class SkillComponent {

  constructor(
    public id: string,
    public name: string
  ) { }
}

@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.sass']
})

export class SkillListComponent implements OnInit {

  skills: SkillComponent[] = [];
  showValidationErrors: boolean = false;

  constructor(
    private skillDataService: SkillDataService,
    private dialog: MatDialog
  ) { }


  ngOnInit(): void {
    this.refreshSkills();
  }

  onFormSubmit(form: NgForm) {
    console.log("FORM SUBMITTED");
    console.log(form);
    if (form.invalid) {
      this.showValidationErrors = true;
    } else {
      this.saveSkill(form);
    }
  }

  onEditClicked(skill: SkillComponent) {
    console.log(`edit skill ${skill.id}`);
    const dialogRef = this.dialog.open(EditSkillDialogComponent, {
      width: '700px',
      data: skill
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Updated Skill Data:', result);
        this.skillDataService.updateSkill(skill.id, result).subscribe(
          response => {
            console.log('Skill Updated:', response);
            this.refreshSkills();
          },
          error => {
            console.error('Error updating skill:', error);
          }
        );
      }
    });
  }

  refreshSkills() {
    this.skillDataService.listAllSkills().subscribe(
      (response: any[]) => {
        console.log(response);
        this.skills = response;
      },
      (error: any) => {
        console.error('Error fetching skills:', error);
      }
    );
  }

  deleteSkill(skillId: string) {
    console.log(`delete skill ${skillId}`);
    this.skillDataService.deleteSkill(skillId).subscribe(
      response => {
        console.log(response);
        this.refreshSkills();
      }
    )
  }


  updateSkill(skillId: string, updatedSkill: any) {
    console.log(`update skill ${skillId}`);
    this.skillDataService.updateSkill(skillId, updatedSkill).subscribe(
      response => {
        console.log(response);
        this.refreshSkills();
      },
      error => {
        console.error('Error updating skill:', error);
      }
    );
  }

  saveSkill(form: NgForm) {
    console.log('save', form.value.text);
    this.skillDataService.saveSkill({ name: form.value.text }).subscribe(
      response => {
        console.log(response);
        this.refreshSkills();
        this.showValidationErrors = false;
        form.resetForm();
      },
    );
  }


}
