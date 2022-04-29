import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UpdateMyProfileRq, UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import { UserService } from 'src/app/shared/service/user/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public isUpdateProfile = false;

  public profile: UserRp = new UserRp();

  public profileForm: FormGroup;

  constructor(
    private _toastr: ToastrService,
    private _userClient: UserClient,
    private formBuilder: FormBuilder,
    private _userService: UserService
    ) { }

  ngOnInit() {
    this.getData();
    this.createProfileForm();
 
   }

  goToProfile() {
    this.isUpdateProfile = false;
  }
   
  goToUpdateProfile() {
    this.isUpdateProfile = true;
  }


  getData(): void{
    this._userClient.getMyProfile().subscribe(
      response =>{
        this.profile = response.content
        this.setDefaultValueForm();
      } 
      
    )
  }

  createProfileForm() {
    this.profileForm = this.formBuilder.group({
      firstName: [''],
      lastName: [''],
      email: [''],
      displayName: [''],
      hobbies: [''],
      facebook: [''],
      gender: [''],
      phone: [''],
    })
  }

  setDefaultValueForm(){
    this.profileForm.patchValue({
      firstName: this.profile.firstName,
      lastName: this.profile.lastName,
      email: this.profile.email,
      displayName: this.profile.displayName,
      hobbies: this.profile.hobbies,
      facebook: this.profile.facebook,
      gender: this.profile.gender,
      phone: this.profile.phone
    })
  }

  updateProfile(){
    let firstName = this.profileForm.controls['firstName'].value;
    let lastName = this.profileForm.controls['lastName'].value
    let email = this.profileForm.controls['email'].value;
    let displayName = this.profileForm.controls['displayName'].value;
    let hobbies = this.profileForm.controls['hobbies'].value;
    let facebook = this.profileForm.controls['facebook'].value;
    let gender = this.profileForm.controls['gender'].value;
    let phone = this.profileForm.controls['phone'].value;

    this._userClient.updateMyProfile(new UpdateMyProfileRq( displayName, email, firstName, lastName, hobbies, facebook, gender, phone)).subscribe(
      response => {
        this.profile = response.content;
        this.isUpdateProfile = false;
        this._toastr.success('Success', 'Update Profile success!');
        this._userService.setDisplayName(response.content.displayName);
      }
    )
  }
}
