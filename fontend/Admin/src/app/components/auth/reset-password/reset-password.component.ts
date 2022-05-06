import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UpdatePasswordRq } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  public generalForm: FormGroup;

  public username: string;
  public password: string;
  public token: string;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private userClient: UserClient,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.createGroupForm();
    this.loadData();
  }

  createGroupForm() {
    this.generalForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      rePassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  loadData() {
    this.route.queryParams.subscribe(params =>{
      this.username = params['username'] == undefined ? null: params['username'];
      this.token = params['token'] == undefined ? null : params['token'];
    }) 


  }

  updatepassword(){
    let password = this.generalForm.controls['password'].value;
    let rePassword = this.generalForm.controls['rePassword'].value;
    if(password != rePassword){
      this.toastr.error("password not equals password repeat", "error");
    }else{
      let rq = new UpdatePasswordRq(this.username,password,this.token);
      this.userClient.updatePassword(rq).subscribe(() =>{
        this.router.navigate(['auth/login']);
        this.toastr.success("Update password success", "success");
      } );
    }
  }
}
