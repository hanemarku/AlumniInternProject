import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SkillComponent } from '../skill.component';



@Component({
  selector: 'app-edit-skill-dialog',
  templateUrl: './edit-skill-dialog.component.html',
  styleUrls: ['./edit-skill-dialog.component.sass']
})
export class EditSkillDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<EditSkillDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public skill: SkillComponent) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close()
  }

  onFormSubmit(form: NgForm) {
    if (form.invalid) return
    
    const updatedSkill = {
      ...this.skill,
      ...form.value
    }
    
    this.dialogRef.close(updatedSkill)
  }


}