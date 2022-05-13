import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginRequest, UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import { UserService } from 'src/app/share/user/user.service';

@Component({
  selector: 'app-headertwo',
  templateUrl: './headertwo.component.html',
  styleUrls: ['./headertwo.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HeadertwoComponent implements OnInit {
public loginForm!: FormGroup;
public profile: UserRp = new UserRp();
public isLogin: boolean = false;
 
sidebarCartActive:boolean=false;
cartclick(){
  if(!this.sidebarCartActive){
    this.sidebarCartActive=true;
    this.signInActive=false;
    this.signUpActive=false;
    this.sidebarInfoActive=false;
  }
  else {
    this.sidebarCartActive=false;
  }
}
//cart sidebar activation end

//sign in popup activation start
signInActive:boolean=false;
signinclick(){
  if(!this.signInActive){
    this.sidebarCartActive=false;
    this.signInActive=true;
    this.signUpActive=false;
    this.sidebarInfoActive=false;
  }
  else {
    this.signInActive=false;
  }
}
//sign up popup activation end

  //sign in popup activation start
  signUpActive:boolean=false;
  signupclick(){
    if(!this.signUpActive){
      this.sidebarCartActive=false;
      this.signUpActive=true;
      this.signInActive=false;
      this.sidebarInfoActive=false;
    }
    else {
      this.signUpActive=false;
    }
  }
  //sign up popup activation end

  //sidebar info click activation start
  sidebarInfoActive:boolean=false;
  infoclick(){
    if(!this.sidebarInfoActive){
      this.sidebarCartActive=false;
      this.signUpActive=false;
      this.signInActive=false;
      this.sidebarInfoActive=true;
    }
    else {
      this.sidebarInfoActive=false;
    }
  }
  //sidebar info click activation end

    //counter
    count=0
    count1=0
    count2=0
  
    counter(type:string) {
      type==="add" ?this.count++:this.count--;
    }
    counter1(type:string) {
      type==="add" ?this.count1++:this.count1--;
    }
    counter2(type:string) {
      type==="add" ?this.count2++:this.count2--;
    }

    //sticky header activation
    isSticky: boolean = false;

    @HostListener('window:scroll', ['$event'])
    checkScroll() {
      this.isSticky = window.pageYOffset >= 40;
    }
    //sticky header activation
    constructor(
      private userService: UserService,
      private formBuilder: FormBuilder,
      private userClient: UserClient
    ) {
      userService.$userCurrent.subscribe(user => {
        this.profile = user
        this.isLogin = user.username != undefined && user.username != null && user.username != "" ;
        console.log(this.isLogin)
      });  
      userClient.getMyProfile().subscribe(rp => userService.setUserCurrent(rp.content));
     }

    createLoginForm() {
      this.loginForm = this.formBuilder.group({
        userName: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(6)]]
      })
    }

    ngOnInit(): void {
      this.createLoginForm();    
    }

    login(): void {
      let username = this.loginForm.controls['userName'].value;
      let password = this.loginForm.controls['password'].value;
     
      if(this.loginForm.valid){
        this.userClient.login(new LoginRequest(username, password))
          .subscribe(response => {
            this.userService.setJWT(response.content);
            this.userClient.getMyProfile().subscribe(rp => this.userService.setUserCurrent(rp.content));
            this.signInActive=false;
          });
      }
    }
  
    logoutClick(): void {
      this.userService.logout();
      this.sidebarCartActive=false;
      this.signUpActive=false;
      this.signInActive=false;
      this.sidebarInfoActive=false;
    }
}
