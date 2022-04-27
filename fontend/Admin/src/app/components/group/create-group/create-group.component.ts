import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { GroupClient } from 'src/app/api-clients/group.client';
import { BaseGroup } from 'src/app/api-clients/model/group.model';


@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.scss']
})
export class CreateGroupComponent implements OnInit {
  public generalForm: FormGroup;
  public seoForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private groupClient: GroupClient,
    private toastr: ToastrService) {
    this.createGroupForm();
  }

  createGroupForm() {
    this.generalForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(3)]]
    });
  }
  ngOnInit() {
  }
  
  createGroup(): void{
    let name = this.generalForm.controls['name'].value;
    let description = this.generalForm.controls['description'].value;
    this.groupClient.createGroup(new BaseGroup(name,description)).subscribe
    (response =>{
      this.toastr.success('Success','Create role success')
    })
  }


}
