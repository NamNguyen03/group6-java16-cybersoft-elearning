import { Component, OnInit } from '@angular/core';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GroupClient } from 'src/app/api-clients/group.client';
import { GroupResponse } from 'src/app/api-clients/model/group.model';

@Component({
    selector: 'app-add-group',
    templateUrl: './add-group.component.html',
    styleUrls: ['./add-group.component.scss']
  })
  export class AddGroupComponent implements OnInit {
    public user_list: UserRp[] = [];
    public list_group: GroupResponse[];
    public profile: UserRp = new UserRp();
    public profileForm: FormGroup;
    public isAddGroup = false;
    public inputSearch = '';
    public groupCurrent : GroupResponse;
    public isDisableSubmit = false;
  

    public searchForm: FormGroup;
    public pages = [];
    private _pageRequest = new PageRequest(1, 5, null, true, null, null);
    public pageCurrent = 1;
    public isSearch =false;
  
    constructor(private _userClient: UserClient, 
      private _router: Router,
      private form: FormBuilder,
      private _toastr: ToastrService, 
      private route: ActivatedRoute,
      private _GroupClient: GroupClient,
     ) {
      
    }
    
    ngOnInit() {
       this.getData();
   
    }
    
    getData(): void{
        this.route.params.subscribe(params =>{
        let id  =  params['userId'] ; 
        
        this._userClient.getProfile(id).subscribe(
        response =>{
           
          this.profile = response.content
          this.createProfileForm();
          this.setDefaultValueForm();
        } 
      )
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
        phone: this.profile.phone,
        groups: this.profile.groups,
      })
    }
    createProfileForm() {
      this.profileForm = this.form.group({
        firstName: [''],
        lastName: [''],
        email: [''],
        displayName: [''],
        hobbies: [''],
        facebook: [''],
        gender: [''],
        phone: [''],
        groups:[],
      })
    }

    changeInputSearch(event:any){
      this._pageRequest = new PageRequest(1, 5, null, true, "name", this.inputSearch);
      this._GroupClient.search(this._pageRequest).subscribe(
        response =>{
           this.list_group = response.content.items;
          
        }
      );

    }
    selectGroup(group){
      this.inputSearch = group.name;
      this.groupCurrent =group;
      this.list_group = [];
      this.isDisableSubmit =true;

    }
    addGroup(){
      if(this.isDisableSubmit){
        this._userClient.addGroupIntoUser(this.profile.id,this.groupCurrent.id).subscribe(
          () =>{
            this._toastr.success("Add Group Into User successfully","Success")
          }
        )
      }

    }
  }