import { Component, Input, OnInit } from '@angular/core';
import { SkillDataService } from '../services/skill-service/skill-data.service';
import { NgForm } from '@angular/forms';
import { EditSkillDialogComponent } from './edit-skill-dialog/edit-skill-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';


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
  isSortByNameActive: boolean = false;
  isSortByDateActive: boolean = true;
  isDropdownOpen: boolean = false;

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
        if (this.isSortByNameActive) {
          this.sortSkillsByName();
        }
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

  sortSkillsByName(event?: Event) {
    console.log('sort skills by name');
    if (event) {
      event.preventDefault();
    }
    this.skillDataService.sortSkillsByName().subscribe(
      (response: any[]) => {
        console.log(response);
        this.skills = response;
        this.isSortByDateActive = false;
        this.isSortByNameActive = true;
      },
      (error: any) => {
        console.error('Error fetching skills:', error);
      }
    );
  }

  sortSkillsByDate() {
    console.log('sort skills by date');
    this.refreshSkills();
    this.isSortByDateActive = true;
    this.isSortByNameActive = false;
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  openConfirmationDialog(skillId: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        const skillToDelete = this.skills.find(skill => skill.id === skillId);
        if (skillToDelete) {
          this.deleteSkill(skillToDelete.id);
        }
        Swal.fire(
          'Deleted!',
          'Skill has been deleted.',
          'success'
        )
      }
    })
  }

}

