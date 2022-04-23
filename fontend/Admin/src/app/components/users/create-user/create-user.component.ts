import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserCreate } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {
  public accountForm: FormGroup;
  public permissionForm: FormGroup;

  constructor(
    private _toastr: ToastrService,
    private formBuilder: FormBuilder,
    private userClient: UserClient) {
    this.createAccountForm();
  }

  createAccountForm() {
    this.accountForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['',[Validators.required, Validators.minLength(6)]],
      username: ['', Validators.required],
      status: ['' , Validators.required],
      displayName: ['', Validators.required]
    })
  }

  ngOnInit() {
  }

  createUser(){
    let username = this.accountForm.controls['username'].value;
    let password = this.accountForm.controls['password'].value;
    let email = this.accountForm.controls['email'].value;
    let firstName = this.accountForm.controls['firstName'].value;
    let lastName = this.accountForm.controls['lastName'].value;
    let status = this.accountForm.controls['status'].value;
    let displayName = this.accountForm.controls['displayName'].value;

    if(this.accountForm.valid){
     this.userClient.createUser(new UserCreate(username, password, displayName, email, status, firstName, lastName)).subscribe(rp => {
      this._toastr.success('Success', 'Create User success!');
     })
    }
  }
}
