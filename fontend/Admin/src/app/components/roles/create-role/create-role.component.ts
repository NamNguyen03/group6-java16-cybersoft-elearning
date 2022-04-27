import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BaseRole } from 'src/app/api-clients/model/role.model';
import { RoleClient } from 'src/app/api-clients/role.client';


@Component({
  selector: 'app-create-role',
  templateUrl: './create-role.component.html',
  styleUrls: ['./create-role.component.scss']
})
export class CreatePageComponent implements OnInit {
  public generalForm: FormGroup;
  public seoForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private roleClient: RoleClient,
    private toastr: ToastrService) {
    this.createRoleForm();
  }

  createRoleForm() {
    this.generalForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(3)]]
    });
  }
  ngOnInit() {
  }
  
  createRole(): void{
  let name = this.generalForm.controls['name'].value;
  let description = this.generalForm.controls['description'].value;
  this.roleClient.createRole(new BaseRole(name,description)).subscribe
  (response =>{
    this.toastr.success('Success','Create role success')
  })
  }


}
