import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
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

  public isActions = false;

  public isUpdateAvatar = false;

  public profileForm: FormGroup;

  public fileInputAvatar: any;



  constructor(
    private _toastr: ToastrService,
    private _userClient: UserClient,
    private formBuilder: FormBuilder,
    private _userService: UserService
    ) { }

  ngOnInit() {
    this.getData();
    this.createProfileForm();
    this._userService.$userCurrent.subscribe(user => this.profile = user );
   }

  toggleAction(): void {
    this.isActions = !this.isActions;
    if(!this.isActions){
      this.isUpdateAvatar = false;
    }
   }

   cancelUploadAvatar(): void {
    this.isUpdateAvatar = false;
  }


  goToUpdateAvatar(): void {
    this.isUpdateAvatar = true;
  }

  goToProfile() {
    this.isUpdateProfile = false;
  }
   
  goToUpdateProfile() {
    this.isUpdateProfile = true;
  }

  changeInputAvatar(event: any): void{
    this._userClient.uploadAvatar(event.target.files[0]).subscribe(
      response => {
        this._toastr.success('Success', 'Update avatar success!');
        this._userService.setUserCurrent(response.content);
        this.isUpdateAvatar = false;
      }
    )
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
    let displayName = this.profileForm.controls['displayName'].value;
    let hobbies = this.profileForm.controls['hobbies'].value;
    let facebook = this.profileForm.controls['facebook'].value;
    let gender = this.profileForm.controls['gender'].value;
    let phone = this.profileForm.controls['phone'].value;

    this._userClient.updateMyProfile(new UpdateMyProfileRq( displayName, firstName, lastName, hobbies, facebook, gender, phone)).subscribe(
      response => {
        this.isUpdateProfile = false;
        this._toastr.success('Update Profile success!', 'Success');
        this._userService.setUserCurrent(response.content);
      }
    )
  }

  resetPassword(): void {
    this._userClient.generateTokenUpdatePassword().subscribe(() => this._toastr.success('Reset password success, please check your email', 'Success'));
  }
}
