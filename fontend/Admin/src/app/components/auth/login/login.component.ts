import { UserClient } from './../../../api-clients/user.client';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginRequest } from 'src/app/api-clients/model/user.model';
import { UserService } from 'src/app/shared/service/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public registerForm: FormGroup;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder, 
    private route: Router,
    private userClient: UserClient) {
    this.createLoginForm();
    this.createRegisterForm();
    this.userService.logout();
  }

  owlcarousel = [
    {
      title: "Welcome to Multikart",
      desc: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
    },
    {
      title: "Welcome to Multikart",
      desc: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
    },
    {
      title: "Welcome to Multikart",
      desc: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
    }
  ]
  owlcarouselOptions = {
    loop: true,
    items: 1,
    dots: true
  };

  createLoginForm() {
    this.loginForm = this.formBuilder.group({
      userName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    })
  }
  createRegisterForm() {
    this.registerForm = this.formBuilder.group({
      userName: [''],
      password: [''],
      confirmPassword: [''],
    })
  }


  ngOnInit() {
  }

  login(): void {
    let username = this.loginForm.controls['userName'].value;
    let password = this.loginForm.controls['password'].value;
   
    if(this.loginForm.valid){
      this.userClient.login(new LoginRequest(username, password))
        .subscribe(response => {
          this.userService.setJWT(response.content);
          this.route.navigate(['dashboard/default']);
        });
    }
  }

}
