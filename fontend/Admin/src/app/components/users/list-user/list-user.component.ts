import { Component, OnInit } from '@angular/core';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { UpdateUserRq, UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageService } from 'src/app/shared/service/page/page.service';
import { id } from '@swimlane/ngx-charts';


@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit {
  public user_list: UserRp[] = [];
  public profile: UserRp = new UserRp();
  public profileForm: FormGroup;

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(0, 10, null, true, null, null);
  public pageCurrent = 1;
  public isSearch =false;

  constructor(private _userClient: UserClient, 
    private _router: Router,
    private form: FormBuilder,
    private _pageService: PageService,
    private _toastr: ToastrService, 
    private route: ActivatedRoute) {
    
  }

  public settings = {
    pager: {
        display: true,
        perPage: 10,
    },
    delete: {
        confirmDelete: true,
    },
    edit: {
      confirmSave: true,
    },
    actions: {
        custom: false,
        delete: true,
        add: false,
    },
    columns: {
        username: {
            title: 'User name',
            editable: false,
        },
        email: {
            title: 'Email',
            type: 'email',
            editable: true,
        },
        department: {
          title: 'Department',
          editable: true,
        },
        major: {
            title: 'Major',
            editable: true,
        },
        status: {
            title: 'Status',
            editor: {
                type: 'list',
                config: {
                    selectText: 'Select',
                    list: [
                        { value: 'ACTIVE', title: 'ACTIVE' },
                        { value: 'TEMPORARY_BLOCKED', title: 'TEMPORARY_BLOCKED' },
                        { value: 'PERMANENT_BLOCKED', title: 'PERMANENT_BLOCKED' }
                    ],
                },
            },
        }
    },
};

  ngOnInit() {
    this.getPageDetails();
    this.createFormSearch();
    
  }

  toggleSearch(){
    this.isSearch=!this.isSearch;
  }
  private createFormSearch() {
    this.searchForm = this.form.group({
      fieldNameSort:[''],
      isIncrementSort:[''],
      fieldNameSearch:[''],
      valueFieldNameSearch: ['']
    })
  }

  getPageDetails(): void{
   
    this.route.queryParams.subscribe(params => {
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];
      this.pageCurrent = params['page'] == undefined ? 1 : params['page'];
      this._pageRequest = new PageRequest(this.pageCurrent, 10, fieldNameSort, isIncrementSort, fieldNameSearch, valueFieldNameSearch);
      this.loadData();

  });
  }

  loadData(): void{
    this._userClient.search(this._pageRequest).subscribe(
      response =>{
        this.user_list = response.content.items;
        this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);
      }
    );
  }


  onDeleteConfirm(event: any): void {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
        if (result.isConfirmed) {
          let isLoadData = false;
          this._userClient.delete(event.data.id).subscribe(() => {
            this.loadData();
            isLoadData = true;
            this._toastr.success('Success', 'Delete User success!');
          })
          
          if(!isLoadData) {
            this.loadData();
          }
        }
    });

  }

  onSaveConfirm(event: any): void {
    
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, update it!',
    }).then((result) => {
      if (result.isConfirmed) {
        let id = event.newData.id;
        let email = event.newData.email;
        let department = event.newData.department;
        let major = event.newData.major;
        let status = event.newData.status;
        let isLoadData = false;

        this._userClient.updateUser(id, new UpdateUserRq(email, status, major, department)).subscribe(
          () => {
            this.loadData();
            isLoadData = true;
            this._toastr.success('Success', 'Update User Successfully')
          }
        )
        if(!isLoadData) {
          this.loadData();
        }
      }});
  }

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    
    this._router.navigate(['/users/list-user'],{
      queryParams: {
        'page':this.pageCurrent,
        'fieldNameSort':fieldNameSort,
        'isIncrementSort':isIncrementSort,
        'fieldNameSearch':fieldNameSearch,
        'valueFieldNameSearch':valueFieldNameSearch}

    })
  }

  clickPage(index: string): void {
    if(index == 'next'){
      this.pageCurrent++;
    }
    if(index == 'prev'){
      this.pageCurrent--;
    }
    if(index != 'prev' && index != 'next'){
      this.pageCurrent = Number(index);
    }
    this.search();
  }
  onUserRowSelected(event) {
     let userId = event.data.id;
     console.log(userId)
    
    this._router.navigate(['/users/user-details'],{
      queryParams: {'userId':userId}})

  }

  getData(id: string): void{
    this._userClient.getProfile(id).subscribe(
      response =>{
        this.profile = response.content
        this.createProfileForm();
        this.setDefaultValueForm();
      } 
      
    )
  }

  setDefaultValueForm(){
    console.log(this.profileForm)
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
}


